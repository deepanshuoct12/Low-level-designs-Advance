package org.project.service;

import org.project.model.User;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class UserService {
    private static final ConcurrentHashMap<Long, User> userStorage = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public User create(User user) {
        if (user.getId() == null) {
            user.setId(idCounter++);
        }
        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());
        userStorage.put(user.getId(), user);
        return user;
    }

    public User getById(Long id) {
        return userStorage.get(id);
    }

    public List<User> getAll() {
        return new ArrayList<>(userStorage.values());
    }

    public User update(Long id, User user) {
        User existing = userStorage.get(id);
        if (existing != null) {
            user.setId(id);
            user.setCreatedAt(existing.getCreatedAt());
            user.setUpdatedAt(System.currentTimeMillis());
            userStorage.put(id, user);
            return user;
        }
        return null;
    }

    public boolean delete(Long id) {
        return userStorage.remove(id) != null;
    }
}
