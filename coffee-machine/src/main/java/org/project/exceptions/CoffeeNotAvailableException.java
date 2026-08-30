package org.project.exceptions;

public class CoffeeNotAvailableException extends RuntimeException {
    public CoffeeNotAvailableException(String coffeeId) {
        super("Coffee is not available: " + coffeeId);
    }
}
