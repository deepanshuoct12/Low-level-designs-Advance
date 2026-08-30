package org.project.service;

import org.project.constants.ErrorMessages;
import org.project.constants.TransactionTypeConstants;
import org.project.enums.Denomination;
import org.project.enums.TransactionStatus;
import org.project.exception.*;
import org.project.model.*;
import org.project.observer.Subject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ATMApplication extends Subject implements IATMApplication {
    private static ATMApplication instance;
    
    private final AccountService accountService = new AccountService();
    private final InventoryService inventoryService = new InventoryService();
    private final CashService cashService = new CashService();
    private final TransactionService transactionService = new TransactionService();
    private final CardService cardService = new CardService();
    private final ReceiptService receiptService = new ReceiptService();

    private ATMApplication() {}

    public static ATMApplication getInstance() {
        if (instance == null) {
            instance = new ATMApplication();
        }
        return instance;
    }

    @Override
    public List<Cash> withdrawMoney(Long accountId, BigDecimal amount, Long atmId) {
        Account account = accountService.getById(accountId);
        if (account == null) {
            throw new AccountNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND);
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(ErrorMessages.INSUFFICIENT_FUNDS);
        }

        Inventory inventory = inventoryService.getByATMId(atmId);
        if (inventory == null) {
            throw new AccountNotFoundException(ErrorMessages.INVENTORY_NOT_FOUND);
        }

        List<Cash> availableCash = cashService.getCashByInventory(inventory.getId());

        long totalInventoryValue = availableCash.stream()
                .mapToLong(cash -> cash.getDenomination().getValue() * cash.getQuantity())
                .sum();

        if (totalInventoryValue < amount.longValue()) {
            throw new InsufficientInventoryException(ErrorMessages.INSUFFICIENT_INVENTORY);
        }

        List<Cash> dispensedCash = calculateDenominations(amount, availableCash);
        Transaction transaction = new Transaction();
        transaction.setType(TransactionTypeConstants.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setTimestamp(java.time.LocalDateTime.now());
        transaction.setCa(LocalDateTime.now());
        transaction.setUa(LocalDateTime.now());
        transactionService.create(transaction);

        account.setBalance(account.getBalance().subtract(amount));
        accountService.update(accountId, account);


        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setTimestamp(java.time.LocalDateTime.now());
        transactionService.update(transaction);
        transaction.setUa(LocalDateTime.now());

        notifyObservers(TransactionTypeConstants.WITHDRAWAL, amount.longValue(), "SUCCESS");

        return dispensedCash;
    }

    private List<Cash> calculateDenominations(BigDecimal amount, List<Cash> availableCash) {
        List<Cash> dispensed = new ArrayList<>();
        long remaining = amount.longValue();

        for (Cash cash : availableCash) {
            if (remaining <= 0) break;

            int needed = (int) (remaining / cash.getDenomination().getValue());
            if (needed > 0 && needed <= cash.getQuantity()) {
                Cash dispensedCash = new Cash();
                dispensedCash.setDenomination(cash.getDenomination());
                dispensedCash.setQuantity(needed);
                dispensedCash.setTotalValue(needed * cash.getDenomination().getValue());
                dispensedCash.setInventoryId(cash.getInventoryId());
                dispensed.add(dispensedCash);

                cash.setQuantity(cash.getQuantity() - needed);
                cashService.update(cash.getId(), cash);

                remaining -= needed * cash.getDenomination().getValue();
            }
        }

        if (remaining > 0) {
            throw new InsufficientInventoryException(ErrorMessages.CANNOT_DISPENSE_AMOUNT);
        }

        return dispensed;
    }

    @Override
    public boolean validateUser(Long cardId, String pin) {
        Card card = cardService.getById(cardId);
        if (card == null) {
            throw new AccountNotFoundException(ErrorMessages.CARD_NOT_FOUND);
        }

        if (card.getExpiry().isBefore(LocalDate.now())) {
            throw new CardExpiredException(ErrorMessages.CARD_EXPIRED);
        }

        if (!card.getPin().equals(pin)) {
            throw new InvalidPinException(ErrorMessages.INVALID_PIN);
        }

        return true;
    }

    @Override
    public boolean depositMoney(Long inventoryId, Map<Denomination, Integer> denominations) {
        Inventory inventory = inventoryService.getById(inventoryId);
        if (inventory == null) {
            throw new AccountNotFoundException(ErrorMessages.INVENTORY_NOT_FOUND);
        }

        for (Map.Entry<Denomination, Integer> entry : denominations.entrySet()) {
            Denomination denom = entry.getKey();
            Integer quantity = entry.getValue();

            if (quantity <= 0) {
                throw new InvalidDenominationException(ErrorMessages.INVALID_DENOMINATION + denom);
            }

            Cash cash = new Cash();
            cash.setDenomination(denom);
            cash.setQuantity(quantity);
            cash.setTotalValue(denom.getValue() * quantity);
            cash.setInventoryId(inventoryId);
            cashService.create(cash);
        }

        Transaction transaction = new Transaction();
        transaction.setType(TransactionTypeConstants.DEPOSIT);
        transaction.setAmount(BigDecimal.valueOf(denominations.entrySet().stream()
                .mapToLong(e -> e.getKey().getValue() * e.getValue())
                .sum()));
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setTimestamp(java.time.LocalDateTime.now());
        transactionService.create(transaction);

        notifyObservers(TransactionTypeConstants.DEPOSIT, transaction.getAmount().longValue(), "SUCCESS");

        return true;
    }

    @Override
    public Long checkBalance(Long accountId) {
        Account account = accountService.getById(accountId);
        if (account == null) {
            throw new AccountNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND);
        }
        return account.getBalance().longValue();
    }

    @Override
    public boolean changePin(Long cardId, String oldPin, String newPin) {
        Card card = cardService.getById(cardId);
        if (card == null) {
            throw new AccountNotFoundException(ErrorMessages.CARD_NOT_FOUND);
        }

        if (!card.getPin().equals(oldPin)) {
            throw new InvalidPinException(ErrorMessages.OLD_PIN_MISMATCH);
        }
        Transaction transaction = new Transaction();
        transaction.setType(TransactionTypeConstants.PIN_CHANGE);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setCa(LocalDateTime.now());
        transaction.setUa(LocalDateTime.now());
        transactionService.create(transaction);


        card.setPin(newPin);
        cardService.update(cardId, card);



        transaction.setType(TransactionTypeConstants.PIN_CHANGE);
        transaction.setUa(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.PIN_CHANGE_SUCCESS);
        transactionService.update(transaction);

        notifyObservers(TransactionTypeConstants.PIN_CHANGE, 0L, "SUCCESS");

        return true;
    }

    @Override
    public Receipt getReceipt(Long transactionId) {
        Transaction transaction = transactionService.getById(transactionId);
        if (transaction == null) {
            throw new AccountNotFoundException(ErrorMessages.TRANSACTION_NOT_FOUND);
        }

        Receipt receipt = new Receipt();
        receipt.setTransactionId(transactionId);
        
        String amountDisplay = transaction.getType().equals(TransactionTypeConstants.PIN_CHANGE) 
                ? "N/A" 
                : transaction.getAmount().toString();
        
        receipt.setContent("Transaction: " + transaction.getType() + 
                          ", Amount: " + amountDisplay + 
                          ", Status: " + transaction.getStatus() +
                          ", Time: " + transaction.getTimestamp());
        receipt.setPrintedAt(java.time.LocalDateTime.now());
        receipt.setStatus("PRINTED");

        return receiptService.create(receipt);
    }
}
