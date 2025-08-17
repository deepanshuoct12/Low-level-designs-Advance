package org.dynamik.stratergy;

import org.dynamik.enums.PaymentStatus;
import org.dynamik.enums.PaymentStratergy;
import org.dynamik.model.Payment;
import org.dynamik.service.BookingService;
import org.dynamik.service.PaymentService;

public class CreditCardStratergy implements IPaymentStratergy {

    private PaymentService paymentService = new PaymentService();
    private BookingService bookingService = new BookingService();

    @Override
    public boolean isApplicable(PaymentStratergy paymentStratergy) {
        return paymentStratergy.equals(PaymentStratergy.CREDIT_CARD);
    }

    @Override
    public Payment pay(Double price, String bookingId) throws InterruptedException {
        System.out.println("paying via credit card");
        Payment payment = Payment.builder().amount(price).bookingId(bookingId).status(PaymentStatus.SUCCESS).build();
        paymentService.save(payment);
        Thread.sleep(5000);  // simulate payment processing
        return payment;
    }
}
