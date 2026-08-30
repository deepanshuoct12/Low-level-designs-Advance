package org.project.service.impl;

import org.project.exception.ConfigServiceException;
import org.project.model.Config;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ConfigServiceImpl {
    private static final Map<String, Config> configStore = new ConcurrentHashMap<>();

    public Config get(String id) {
        Config config = configStore.get(id);
        if (config == null) {
            throw new ConfigServiceException("Config not found with id: " + id);
        }
        return config;
    }

    public Config put(Config config) {
        String id = UUID.randomUUID().toString();
        config.setId(id);
        config.setCreatedAt(LocalDateTime.now());
        config.setUpdatedAt(LocalDateTime.now());
        configStore.put(id, config);
        return config;
    }

    public Config update(String id, Config config) {
        Config existing = configStore.get(id);
        if (existing == null) {
            throw new ConfigServiceException("Config not found with id: " + id);
        }
        config.setId(id);
        config.setCreatedAt(existing.getCreatedAt());
        config.setUpdatedAt(LocalDateTime.now());
        configStore.put(id, config);
        return config;
    }

    public boolean delete(String id) {
        Config removed = configStore.remove(id);
        if (removed == null) {
            throw new ConfigServiceException("Config not found with id: " + id);
        }
        return true;
    }

    public List<Config> getAll() {
        return new ArrayList<>(configStore.values());
    }

    public List<Config> getByServiceId(String serviceId) {
        return configStore.values().stream()
                .filter(config -> serviceId.equals(config.getServiceId()))
                .collect(Collectors.toList());
    }

    public List<Config> getByEnvironmentId(String environmentId) {
        return configStore.values().stream()
                .filter(config -> environmentId.equals(config.getEnvironMentId()))
                .collect(Collectors.toList());
    }

    public boolean deactivate(String id) {
        Config config = configStore.get(id);
        if (config == null) {
            throw new ConfigServiceException("Config not found with id: " + id);
        }
        config.setActive(false);
        config.setUpdatedAt(LocalDateTime.now());
        return true;
    }
}
