package org.project.service;

import org.project.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public User getUser(String userId) {
        return users.get(userId);
    }

    public User createUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public void update(User user) {
        users.put(user.getId(), user);
    }
}
