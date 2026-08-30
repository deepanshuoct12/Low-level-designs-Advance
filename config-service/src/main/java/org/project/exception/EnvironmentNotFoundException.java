package org.project.exception;

public class EnvironmentNotFoundException extends ConfigServiceException {
    public EnvironmentNotFoundException(String environmentId) {
        super("Environment not found with id: " + environmentId);
    }

    public EnvironmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
