package org.project.exception;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(String message) {
        super(message);
    }
}
