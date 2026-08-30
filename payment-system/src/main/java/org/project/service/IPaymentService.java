package org.project.service;

import org.project.enums.PaymentIntentStatus;
import org.project.enums.TransactionStatus;
import org.project.model.PaymentIntent;
import org.project.model.Transaction;

public interface IPaymentService {
    PaymentIntent createPaymentIntent(PaymentIntent paymentIntent);

    Transaction doTransaction(String paymentIntentId);

    PaymentIntent cancelPaymentIntent(String paymentIntentId);

    Transaction refundTransaction(String transactionId);

    PaymentIntentStatus getPaymentIntentStatus(String paymentIntentId);

    TransactionStatus getTransactionStatus(String transactionId);
}
