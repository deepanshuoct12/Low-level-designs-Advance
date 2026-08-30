package org.project.exception;

public class PaymentIntentNotFoundException extends RuntimeException {
    public PaymentIntentNotFoundException(String paymentIntentId) {
        super("Payment intent not found: " + paymentIntentId);
    }
}
