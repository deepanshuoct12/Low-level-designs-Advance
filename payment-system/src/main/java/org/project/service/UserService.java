package org.project.service;

import org.project.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private static final Map<String, User> STORE = new ConcurrentHashMap<>();

    public User create(User entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        entity.setCa(now);
        entity.setUa(now);
        STORE.put(entity.getId(), entity);
        return entity;
    }

    public User getById(String id) {
        return STORE.get(id);
    }

    public List<User> getAll() {
        return new ArrayList<>(STORE.values());
    }

    public User update(String id, User entity) {
        if (!STORE.containsKey(id)) {
            return null;
        }
        entity.setId(id);
        entity.setUa(System.currentTimeMillis());
        STORE.put(id, entity);
        return entity;
    }

    public boolean delete(String id) {
        return STORE.remove(id) != null;
    }
}
