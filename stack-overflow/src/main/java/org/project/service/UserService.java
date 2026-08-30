package org.project.service;

import org.project.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private static final Map<String, User> storage = new ConcurrentHashMap<>();

    public User create(User user) {
        user.setId(UUID.randomUUID().toString());
        long now = System.currentTimeMillis();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        storage.put(user.getId(), user);
        return user;
    }

    public User getById(String id) {
        return storage.get(id);
    }

    public List<User> getAll() {
        return new ArrayList<>(storage.values());
    }

    public User update(String id, User user) {
        User existing = storage.get(id);
        if (existing == null) {
            return null;
        }
        user.setId(id);
        user.setCreatedAt(existing.getCreatedAt());
        user.setUpdatedAt(System.currentTimeMillis());
        storage.put(id, user);
        return user;
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }
}
