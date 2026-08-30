package org.project.service.impl;

import org.project.exception.ConfigVersionNotFoundException;
import org.project.model.Config;
import org.project.model.ConfigVersion;
import org.project.service.ConfigApplication;

import java.util.Comparator;
import java.util.List;

public class ConfigApplicationImpl implements ConfigApplication {
    private static ConfigApplicationImpl instance;
    
    private final ConfigServiceImpl configService;
    private final ConfigVersionServiceImpl configVersionService;
    
    private ConfigApplicationImpl() {
        this.configService = new ConfigServiceImpl();
        this.configVersionService = new ConfigVersionServiceImpl();
    }
    
    public static ConfigApplicationImpl getInstance() {
        if (instance == null) {
            synchronized (ConfigApplicationImpl.class) {
                if (instance == null) {
                    instance = new ConfigApplicationImpl();
                }
            }
        }
        return instance;
    }
    
    @Override
    public Config addConfig(Config config) {
        return configService.put(config);
    }
    
    @Override
    public boolean removeConfig(String configId) {
        return configService.delete(configId);
    }
    
    @Override
    public Config updateConfig(String configId, Config config) {
        return configService.update(configId, config);
    }
    
    @Override
    public Config getConfig(String configId) {
        return configService.get(configId);
    }
    
    @Override
    public Config rollbackToPreviousVersion(String configId, String environmentId) {
        ConfigVersion previousVersion = getPreviousVersion(configId, environmentId);
        if (previousVersion == null) {
            throw new ConfigVersionNotFoundException(configId, environmentId);
        }
        
        ConfigVersion newVersion = ConfigVersion.builder()
                .configId(configId)
                .value(previousVersion.getValue())
                .version(getNextVersionNumber(configId, environmentId))
                .changedBy("rollback")
                .build();
        
        configVersionService.put(newVersion);
        
        return configService.get(configId);
    }
    
    @Override
    public ConfigVersion getPreviousVersion(String configId, String environmentId) {
        List<ConfigVersion> versions = configVersionService.getByConfigId(configId);
        if (versions.isEmpty()) {
            return null;
        }
        
        return versions.stream()
                .sorted(Comparator.comparing(ConfigVersion::getVersion).reversed())
                .skip(1)
                .findFirst()
                .orElse(null);
    }
    
    private Integer getNextVersionNumber(String configId, String environmentId) {
        List<ConfigVersion> versions = configVersionService.getByConfigId(configId);
        return versions.stream()
                .map(ConfigVersion::getVersion)
                .max(Comparator.naturalOrder())
                .orElse(0) + 1;
    }
}
