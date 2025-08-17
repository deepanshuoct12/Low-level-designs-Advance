package org.dynamik.stratergy;

import org.dynamik.enums.PaymentStatus;
import org.dynamik.enums.PaymentStratergy;
import org.dynamik.model.Payment;
import org.dynamik.service.PaymentService;

public class UPIStratergy implements IPaymentStratergy {
    private PaymentService paymentService = new PaymentService();

    @Override
    public boolean isApplicable(PaymentStratergy paymentStratergy) {
        return paymentStratergy.equals(PaymentStratergy.UPI);
    }

    @Override
    public Payment pay(Double price, String bookingId) throws InterruptedException {
        System.out.println("UPI payment");
        Payment payment = Payment.builder().amount(price).bookingId(bookingId).status(PaymentStatus.SUCCESS).build();
        paymentService.save(payment);
        Thread.sleep(5000);  // simulate payment processing
        return payment;
    }
}
