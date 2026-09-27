package org.project.service;

import org.project.model.User;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private static final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public User get(String userId) {
        return users.get(userId);
    }

    public void add(User user){
        users.put(user.getId(), user);
    }

    public void addAll(List<User> users) {
        for (User user: users) {
            add(user);
        }
    }
}
