package org.dynamik.dao;

import org.dynamik.model.User;
import org.dynamik.enums.UserRole;

import java.util.*;

public class UserDao {
    private static final Map<String, User> users = new HashMap<>();

    public void save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
        }
        users.put(user.getId(), user);
    }

    public User findById(String id) {
        return users.get(id);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void delete(String id) {
        users.remove(id);
    }

    public List<User> findByRole(UserRole role) {
        return users.values().stream()
                .filter(user -> user.getRole() == role)
                .toList();
    }

    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}
