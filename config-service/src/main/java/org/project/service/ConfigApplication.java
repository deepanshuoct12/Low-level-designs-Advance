package org.project.service;

import org.project.model.Config;
import org.project.model.ConfigVersion;

public interface ConfigApplication {
    // Config management operations
    Config addConfig(Config config);
    boolean removeConfig(String configId);
    Config updateConfig(String configId, Config config);
    Config getConfig(String configId);
    
    // Version and rollback operations
    Config rollbackToPreviousVersion(String configId, String environmentId);
    ConfigVersion getPreviousVersion(String configId, String environmentId);
}
