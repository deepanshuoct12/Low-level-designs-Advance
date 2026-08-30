package org.project.service.impl;

import org.project.exception.EnvironmentNotFoundException;
import org.project.model.Environment;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EnvironmentServiceImpl {
    private static final Map<String, Environment> environmentStore = new ConcurrentHashMap<>();

    public Environment get(String id) {
        Environment environment = environmentStore.get(id);
        if (environment == null) {
            throw new EnvironmentNotFoundException(id);
        }
        return environment;
    }

    public Environment put(Environment environment) {
        String id = UUID.randomUUID().toString();
        environment.setId(id);
        environment.setCreatedAt(LocalDateTime.now());
        environment.setUpdatedAt(LocalDateTime.now());
        environmentStore.put(id, environment);
        return environment;
    }

    public Environment update(String id, Environment environment) {
        Environment existing = environmentStore.get(id);
        if (existing == null) {
            throw new EnvironmentNotFoundException(id);
        }
        environment.setId(id);
        environment.setCreatedAt(existing.getCreatedAt());
        environment.setUpdatedAt(LocalDateTime.now());
        environmentStore.put(id, environment);
        return environment;
    }

    public boolean delete(String id) {
        Environment removed = environmentStore.remove(id);
        if (removed == null) {
            throw new EnvironmentNotFoundException(id);
        }
        return true;
    }

    public List<Environment> getAll() {
        return new ArrayList<>(environmentStore.values());
    }

    public List<Environment> getActiveEnvironments() {
        return environmentStore.values().stream()
                .filter(Environment::isActive)
                .collect(Collectors.toList());
    }

    public Environment getByName(String name) {
        return environmentStore.values().stream()
                .filter(environment -> name.equals(environment.getName()))
                .findFirst()
                .orElse(null);
    }

    public boolean deactivate(String id) {
        Environment environment = environmentStore.get(id);
        if (environment == null) {
            throw new EnvironmentNotFoundException(id);
        }
        environment.setActive(false);
        environment.setUpdatedAt(LocalDateTime.now());
        return true;
    }
}
