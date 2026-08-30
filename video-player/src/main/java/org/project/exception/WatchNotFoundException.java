package org.project.exception;

public class WatchNotFoundException extends RuntimeException {
    public WatchNotFoundException(String message) {
        super(message);
    }
}
