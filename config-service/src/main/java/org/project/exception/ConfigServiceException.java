package org.project.exception;

public class ConfigServiceException extends RuntimeException {
    public ConfigServiceException(String message) {
        super(message);
    }

    public ConfigServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
