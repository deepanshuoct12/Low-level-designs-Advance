package org.example.dao;

import org.example.exception.UserNotFoundException;
import org.example.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {
    private static List<User> users = new ArrayList<>();
    private Map<String, User> idIndex = new HashMap<>();

    public User save(User user) {
        users.add(user);
        idIndex.put(user.getId(), user);
        return user;
    }

    public User findById(String id) {
        if (!idIndex.containsKey(id)) {
            throw new UserNotFoundException("User not found");
        }

        return idIndex.get(id);
    }

    public List<User> findAll() {
        return users;
    }

    public void delete(String id) {
        User user = findById(id);
        users.remove(user);
        idIndex.remove(id);
    }

    public void update(String id, User user) {
        User existingUser = findById(id);
        existingUser.setName(user.getName());
        existingUser.setAge(user.getAge());
        existingUser.setEmailId(user.getEmailId());
    }
}
