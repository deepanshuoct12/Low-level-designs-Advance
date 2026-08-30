package org.project.exceptions;

public class UnsupportedPaymentTypeException extends RuntimeException {
    public UnsupportedPaymentTypeException(String paymentType) {
        super("Unsupported payment type: " + paymentType);
    }
}
