package org.project.exception;

public class ServiceNotFoundException extends ConfigServiceException {
    public ServiceNotFoundException(String serviceId) {
        super("Service not found with id: " + serviceId);
    }

    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
