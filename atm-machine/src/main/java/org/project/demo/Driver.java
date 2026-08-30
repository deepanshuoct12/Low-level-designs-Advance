package org.project.demo;

import org.project.constants.TransactionTypeConstants;
import org.project.enums.Denomination;
import org.project.exception.*;
import org.project.model.*;
import org.project.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Driver {
    private final BankService bankService = new BankService();
    private final ATMService atmService = new ATMService();
    private final InventoryService inventoryService = new InventoryService();
    private final CashService cashService = new CashService();
    private final UserService userService = new UserService();
    private final AccountService accountService = new AccountService();
    private final CardService cardService = new CardService();
    private final ATMApplication atmApplication = ATMApplication.getInstance();

    private Bank bank;
    private ATM atm;
    private Inventory inventory;
    private User user;
    private Account account;
    private Card card;

    public void runDemo() {
        logInfo("=== ATM System Demo Started ===");
        
        setupSampleData();
        adminDepositMoney();
        userWithdrawMoneyFirst();
        userWithdrawMoneySecond();
        userWithdrawMoneyThird();
        userChangePin();
        
        logInfo("=== ATM System Demo Completed ===");
    }

    private void setupSampleData() {
        logInfo("Setting up sample data...");
        
        createBank();
        createATM();
        createInventory();
        createUser();
        createAccount();
        createCard();
        
        logInfo("Sample data setup completed.");
    }

    private void createBank() {
        bank = new Bank();
        bank.setName("Demo Bank");
        bank.setCode("DB001");
        bank.setAddress("123 Main Street");
        bank = bankService.create(bank);
        logInfo("Bank created: " + bank.getName());
    }

    private void createATM() {
        atm = new ATM();
        atm.setLocation("Central Branch");
        atm.setBankId(bank.getId().toString());
        atm = atmService.create(atm);
        logInfo("ATM created at: " + atm.getLocation());
    }

    private void createInventory() {
        inventory = new Inventory();
        inventory.setAtmId(atm.getId());
        inventory = inventoryService.create(inventory);
        logInfo("Inventory created for ATM");
    }

    private void createUser() {
        user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@email.com");
        user.setPhone("1234567890");
        user.setPin("1234");
        user = userService.create(user);
        logInfo("User created: " + user.getName());
    }

    private void createAccount() {
        account = new Account();
        account.setAccountNumber("ACC001");
        account.setUserId(user.getId());
        account.setBankId(bank.getId());
        account.setBalance(BigDecimal.valueOf(5000));
        account = accountService.create(account);
        logInfo("Account created with balance: " + account.getBalance());
    }

    private void createCard() {
        card = new Card();
        card.setCardNumber("1234567890123456");
        card.setCvv("123");
        card.setExpiry(LocalDate.now().plusYears(2));
        card.setUserId(user.getId());
        card.setPin("1234");
        card = cardService.create(card);
        logInfo("Card created for user");
    }

    private void adminDepositMoney() {
        logInfo("=== Admin Deposit Money ===");
        
        Map<Denomination, Integer> denominations = new HashMap<>();
        denominations.put(Denomination.FIVE_HUNDRED, 20);
        denominations.put(Denomination.ONE_HUNDRED, 50);
        denominations.put(Denomination.FIFTY, 100);
        
        try {
            boolean success = atmApplication.depositMoney(inventory.getId(), denominations);
            if (success) {
                logInfo("Admin deposited money successfully");
                logCashInventory();
            }
        } catch (Exception e) {
            logError("Admin deposit failed: " + e.getMessage());
        }
    }

    private void userWithdrawMoneyFirst() {
        logInfo("=== User Withdraw Money (First Attempt) ===");
        
        try {
            atmApplication.validateUser(card.getId(), "1234");
            logInfo("User validated successfully");
            
            Long balance = atmApplication.checkBalance(account.getId());
            logInfo("Current balance: " + balance);

            logInfo("User Fetching 1000 bucks");
            List<Cash> dispensedCash = atmApplication.withdrawMoney(
                account.getId(), BigDecimal.valueOf(1000), atm.getId());
            
            logInfo("Withdrawal successful. Dispensed cash:");
            for (Cash cash : dispensedCash) {
                logInfo("  - " + cash.getDenomination() + " x " + cash.getQuantity());
            }
            
            balance = atmApplication.checkBalance(account.getId());
            logInfo("Remaining balance: " + balance);
            
        } catch (Exception e) {
            logError("Withdrawal failed: " + e.getMessage());
        }
    }

    private void userWithdrawMoneySecond() {
        logInfo("=== User Withdraw Money (Second Attempt) ===");
        
        try {
            Long balance = atmApplication.checkBalance(account.getId());
            logInfo("Current balance: " + balance);

            logInfo("User Fetching 2000 bucks");
            java.util.List<Cash> dispensedCash = atmApplication.withdrawMoney(
                account.getId(), BigDecimal.valueOf(2000), atm.getId());
            
            logInfo("Withdrawal successful. Dispensed cash:");
            for (Cash cash : dispensedCash) {
                logInfo("  - " + cash.getDenomination() + " x " + cash.getQuantity());
            }
            
            balance = atmApplication.checkBalance(account.getId());
            logInfo("Remaining balance: " + balance);
            
        } catch (Exception e) {
            logError("Withdrawal failed: " + e.getMessage());
        }
    }

    private void userWithdrawMoneyThird() {
        logInfo("=== User Withdraw Money (Third Attempt - Should Fail) ===");
        
        try {
            Long balance = atmApplication.checkBalance(account.getId());
            logInfo("Current balance: " + balance);

            logInfo("User Fetching 3000 bucks");
            java.util.List<Cash> dispensedCash = atmApplication.withdrawMoney(
                account.getId(), BigDecimal.valueOf(3000), atm.getId());
            
            logInfo("Withdrawal successful (unexpected)");
            
        } catch (InsufficientFundsException e) {
            logError("Withdrawal failed as expected: " + e.getMessage());
            logInfo("Exception logged successfully");
        } catch (Exception e) {
            logError("Unexpected error: " + e.getMessage());
        }
    }

    private void userChangePin() {
        logInfo("=== User Change PIN ===");
        
        try {
            atmApplication.validateUser(card.getId(), "1234");
            logInfo("User validated successfully");
            
            boolean success = atmApplication.changePin(card.getId(), "1234", "5678");
            if (success) {
                logInfo("PIN changed successfully from 1234 to 5678");
                
                // Validate with new PIN
                atmApplication.validateUser(card.getId(), "5678");
                logInfo("User validated with new PIN successfully");
            }
            
        } catch (Exception e) {
            logError("PIN change failed: " + e.getMessage());
        }
    }

    private void logCashInventory() {
        java.util.List<Cash> cashList = cashService.getCashByInventory(inventory.getId());
        logInfo("Current Cash Inventory:");
        for (Cash cash : cashList) {
            logInfo("  - " + cash.getDenomination() + " x " + cash.getQuantity());
        }
    }

    private void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    private void logError(String message) {
        System.out.println("[ERROR] " + message);
    }
}
