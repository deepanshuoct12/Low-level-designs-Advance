package org.dynamik.stratergy;

import org.dynamik.enums.PaymentStratergy;
import org.dynamik.model.Payment;

public interface IPaymentStratergy {
    public boolean isApplicable(PaymentStratergy paymentStratergy);
    public Payment pay(Double price, String bookingId) throws InterruptedException;
}
