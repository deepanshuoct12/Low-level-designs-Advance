package org.project.exceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String fieldName, String reason) {
        super("Invalid input for " + fieldName + ": " + reason);
    }
}
