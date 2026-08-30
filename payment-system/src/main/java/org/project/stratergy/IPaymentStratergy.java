package org.project.stratergy;

import org.project.model.PaymentIntent;
import org.project.model.Transaction;

public interface IPaymentStratergy {
    Transaction pay(PaymentIntent paymentIntent);
}
