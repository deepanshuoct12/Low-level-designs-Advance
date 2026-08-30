package org.project.exception;

import org.project.enums.TransactionType;

public class UnsupportedPaymentStrategyException extends RuntimeException {
    public UnsupportedPaymentStrategyException(TransactionType transactionType) {
        super("No payment strategy found for type: " + transactionType);
    }
}
