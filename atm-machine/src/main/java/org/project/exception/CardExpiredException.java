package org.project.exception;

public class CardExpiredException extends RuntimeException {
    public CardExpiredException(String message) {
        super(message);
    }
}
