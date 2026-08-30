package org.project.stratergy;

import org.project.enums.TransactionStatus;
import org.project.enums.TransactionType;
import org.project.model.PaymentIntent;
import org.project.model.Transaction;

public class UPIStratergy implements IPaymentStratergy {

    @Override
    public Transaction pay(PaymentIntent paymentIntent) {
        Transaction transaction = new Transaction();
        transaction.setPaymentIntentId(paymentIntent.getId());
        transaction.setAmount(paymentIntent.getAmount());
        transaction.setTransactionType(TransactionType.UPI);
        transaction.setStatus(TransactionStatus.SUCCESS);
        return transaction;
    }
}
