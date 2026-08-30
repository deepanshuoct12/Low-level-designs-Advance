package org.project.service;

import org.project.model.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class UserService {
    private final Map<Long, User> userStorage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public User create(User user) {
        Long id = idGenerator.getAndIncrement();
        user.setId(id);
        userStorage.put(id, user);
        return user;
    }

    public User getById(Long id) {
        return userStorage.get(id);
    }

    public List<User> getAll() {
        return new java.util.ArrayList<>(userStorage.values());
    }

    public User update(Long id, User user) {
        if (userStorage.containsKey(id)) {
            user.setId(id);
            user.updateTimestamp();
            userStorage.put(id, user);
            return user;
        }
        return null;
    }

    public boolean delete(Long id) {
        return userStorage.remove(id) != null;
    }
}
