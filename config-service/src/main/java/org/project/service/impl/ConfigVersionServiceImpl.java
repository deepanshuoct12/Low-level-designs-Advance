package org.project.service.impl;

import org.project.exception.ConfigVersionNotFoundException;
import org.project.model.ConfigVersion;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ConfigVersionServiceImpl {
    private static final Map<String, ConfigVersion> configVersionStore = new ConcurrentHashMap<>();

    public ConfigVersion get(String id) {
        ConfigVersion configVersion = configVersionStore.get(id);
        if (configVersion == null) {
            throw new ConfigVersionNotFoundException(id);
        }
        return configVersion;
    }

    public ConfigVersion put(ConfigVersion configVersion) {
        String id = UUID.randomUUID().toString();
        configVersion.setId(id);
        configVersion.setCreatedAt(LocalDateTime.now());
        configVersion.setUpdatedAt(LocalDateTime.now());
        configVersionStore.put(id, configVersion);
        return configVersion;
    }

    public ConfigVersion update(String id, ConfigVersion configVersion) {
        ConfigVersion existing = configVersionStore.get(id);
        if (existing == null) {
            throw new ConfigVersionNotFoundException(id);
        }
        configVersion.setId(id);
        configVersion.setCreatedAt(existing.getCreatedAt());
        configVersion.setUpdatedAt(LocalDateTime.now());
        configVersionStore.put(id, configVersion);
        return configVersion;
    }

    public boolean delete(String id) {
        ConfigVersion removed = configVersionStore.remove(id);
        if (removed == null) {
            throw new ConfigVersionNotFoundException(id);
        }
        return true;
    }

    public List<ConfigVersion> getByConfigId(String configId) {
        return configVersionStore.values().stream()
                .filter(version -> configId.equals(version.getConfigId()))
                .collect(Collectors.toList());
    }

    public List<ConfigVersion> getByChangedBy(String changedBy) {
        return configVersionStore.values().stream()
                .filter(version -> changedBy.equals(version.getChangedBy()))
                .collect(Collectors.toList());
    }

    public ConfigVersion getLatestVersion(String configId, String environmentId) {
        return configVersionStore.values().stream()
                .filter(version -> configId.equals(version.getConfigId()))
                .max(Comparator.comparing(ConfigVersion::getVersion))
                .orElse(null);
    }
}
