package org.project.service;

import org.project.enums.PaymentIntentStatus;
import org.project.enums.TransactionStatus;
import org.project.enums.TransactionType;
import org.project.exception.AccountNotFoundException;
import org.project.exception.PaymentIntentNotFoundException;
import org.project.exception.TransactionNotFoundException;
import org.project.exception.UnsupportedPaymentStrategyException;
import org.project.model.Account;
import org.project.model.PaymentIntent;
import org.project.model.Transaction;
import org.project.stratergy.CreditCardStratregy;
import org.project.stratergy.IPaymentStratergy;
import org.project.stratergy.UPIStratergy;
import org.project.validator.TransactionValidator;

import java.util.EnumMap;
import java.util.Map;

public class PaymentServiceImpl implements IPaymentService {

    private static  PaymentServiceImpl instance;

    private final PaymentIntentService paymentIntentService = new PaymentIntentService();
    private final TransactionService transactionService = new TransactionService();
    private final AccountService accountService = new AccountService();
    private final Map<TransactionType, IPaymentStratergy> strategies = new EnumMap<>(TransactionType.class);

    private PaymentServiceImpl() {
        strategies.put(TransactionType.CREDIT_CARD, new CreditCardStratregy());
        strategies.put(TransactionType.UPI, new UPIStratergy());
    }

    public static PaymentServiceImpl getInstance() {
        if (instance == null) {
            synchronized (PaymentServiceImpl.class) {
                if (instance == null) {
                    instance = new PaymentServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentIntent paymentIntent) {
        paymentIntent.setStatus(PaymentIntentStatus.REQUIRES_PAYMENT_METHOD);
        return paymentIntentService.create(paymentIntent);
    }

    @Override
    public Transaction doTransaction(String paymentIntentId) {
        PaymentIntent paymentIntent = paymentIntentService.getById(paymentIntentId);
        if (paymentIntent == null) {
            throw new PaymentIntentNotFoundException(paymentIntentId);
        }

        Account senderAccount = accountService.getByUserId(paymentIntent.getUserId());
        if (senderAccount == null) {
            throw new AccountNotFoundException(paymentIntent.getUserId());
        }

        Account merchantAccount = accountService.getByMerchantId(paymentIntent.getMerchantId());
        if (merchantAccount == null) {
            throw new AccountNotFoundException(paymentIntent.getMerchantId());
        }

        paymentIntent.setStatus(PaymentIntentStatus.PROCESSING);
        paymentIntentService.update(paymentIntentId, paymentIntent);

        if (!TransactionValidator.isBalanceSufficient(senderAccount, paymentIntent)) {
            Transaction failedTransaction = new Transaction();
            failedTransaction.setPaymentIntentId(paymentIntentId);
            failedTransaction.setSenderAccountId(senderAccount.getId());
            failedTransaction.setReceiverAccountId(merchantAccount.getId());
            failedTransaction.setAmount(paymentIntent.getAmount());
            failedTransaction.setTransactionType(paymentIntent.getPaymentMethodType());
            failedTransaction.setStatus(TransactionStatus.FAILED);
            transactionService.create(failedTransaction);

            paymentIntent.setStatus(PaymentIntentStatus.FAILED);
            paymentIntentService.update(paymentIntentId, paymentIntent);

            return failedTransaction;
        }

        IPaymentStratergy strategy = strategies.get(paymentIntent.getPaymentMethodType());
        if (strategy == null) {
            throw new UnsupportedPaymentStrategyException(paymentIntent.getPaymentMethodType());
        }

        Transaction transaction = strategy.pay(paymentIntent);
        transaction.setSenderAccountId(senderAccount.getId());
        transaction.setReceiverAccountId(merchantAccount.getId());
        transactionService.create(transaction);

        if (transaction.getStatus() == TransactionStatus.SUCCESS) {
            senderAccount.setBalance(senderAccount.getBalance().subtract(paymentIntent.getAmount()));
            accountService.update(senderAccount.getId(), senderAccount);

            merchantAccount.setBalance(merchantAccount.getBalance().add(paymentIntent.getAmount()));
            accountService.update(merchantAccount.getId(), merchantAccount);

            paymentIntent.setStatus(PaymentIntentStatus.SUCCEEDED);
        } else {
            paymentIntent.setStatus(PaymentIntentStatus.FAILED);
        }
        paymentIntentService.update(paymentIntentId, paymentIntent);

        return transaction;
    }

    @Override
    public PaymentIntent cancelPaymentIntent(String paymentIntentId) {
        PaymentIntent paymentIntent = paymentIntentService.getById(paymentIntentId);
        if (paymentIntent == null) {
            throw new PaymentIntentNotFoundException(paymentIntentId);
        }
        paymentIntent.setStatus(PaymentIntentStatus.CANCELED);
        return paymentIntentService.update(paymentIntentId, paymentIntent);
    }

    @Override
    public Transaction refundTransaction(String transactionId) {
        Transaction transaction = transactionService.getById(transactionId);
        if (transaction == null) {
            throw new TransactionNotFoundException(transactionId);
        }

        Account senderAccount = accountService.getById(transaction.getSenderAccountId());
        if (senderAccount == null) {
            throw new AccountNotFoundException(transaction.getSenderAccountId());
        }
        senderAccount.setBalance(senderAccount.getBalance().add(transaction.getAmount()));
        accountService.update(senderAccount.getId(), senderAccount);

        Account merchantAccount = accountService.getById(transaction.getReceiverAccountId());
        if (merchantAccount == null) {
            throw new AccountNotFoundException(transaction.getReceiverAccountId());
        }
        merchantAccount.setBalance(merchantAccount.getBalance().subtract(transaction.getAmount()));
        accountService.update(merchantAccount.getId(), merchantAccount);

        transaction.setStatus(TransactionStatus.REFUNDED);
        return transactionService.update(transactionId, transaction);
    }

    @Override
    public PaymentIntentStatus getPaymentIntentStatus(String paymentIntentId) {
        PaymentIntent paymentIntent = paymentIntentService.getById(paymentIntentId);
        if (paymentIntent == null) {
            throw new PaymentIntentNotFoundException(paymentIntentId);
        }
        return paymentIntent.getStatus();
    }

    @Override
    public TransactionStatus getTransactionStatus(String transactionId) {
        Transaction transaction = transactionService.getById(transactionId);
        if (transaction == null) {
            throw new TransactionNotFoundException(transactionId);
        }
        return transaction.getStatus();
    }
}
