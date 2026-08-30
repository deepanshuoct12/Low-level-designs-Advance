package org.project.service;

import org.project.model.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class UserService {
    private static final Map<Long, User> userStore = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    public User create(User user) {
        user.setId(idGenerator.getAndIncrement());
        user.onCreate();
        userStore.put(user.getId(), user);
        return user;
    }

    public User getById(Long id) {
        return userStore.get(id);
    }

    public List<User> getAll() {
        return userStore.values().stream().collect(Collectors.toList());
    }

    public User update(Long id, User user) {
        if (userStore.containsKey(id)) {
            user.setId(id);
            user.onUpdate();
            userStore.put(id, user);
            return user;
        }
        return null;
    }

    public boolean delete(Long id) {
        return userStore.remove(id) != null;
    }
}
