package org.project.service;

import org.project.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private static final ConcurrentHashMap<String, User> USERS = new ConcurrentHashMap<>();

    public User create(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        USERS.put(user.getId(), user);
        return user;
    }

    public User getById(String id) {
        return USERS.get(id);
    }

    public List<User> getAll() {
        return new ArrayList<>(USERS.values());
    }

    public User update(User user) {
        if (!USERS.containsKey(user.getId())) {
            return null;
        }
        user.setUpdatedAt(System.currentTimeMillis());
        USERS.put(user.getId(), user);
        return user;
    }

    public void delete(String id) {
        USERS.remove(id);
    }
}
