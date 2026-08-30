package org.project.exceptions;

public class InsufficientAmountException extends RuntimeException {
    public InsufficientAmountException(double required, double provided) {
        super("Insufficient amount. Required: " + required + ", Provided: " + provided);
    }
}
