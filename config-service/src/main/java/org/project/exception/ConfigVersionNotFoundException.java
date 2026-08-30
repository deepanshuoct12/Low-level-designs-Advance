package org.project.exception;

public class ConfigVersionNotFoundException extends ConfigServiceException {
    public ConfigVersionNotFoundException(String versionId) {
        super("Config version not found with id: " + versionId);
    }

    public ConfigVersionNotFoundException(String configId, String environmentId) {
        super("Config version not found for config: " + configId + " in environment: " + environmentId);
    }

    public ConfigVersionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
