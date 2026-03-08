package org.dynamik.dao;

import org.dynamik.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDao implements IBaseDao<User, String> {
    private Map<String, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public User findById(String id) {
        return users.get(id);
    }

    @Override
    public void deleteById(String id) {
       users.remove(id);
    }

    @Override
    public void update(User user) {
       if (users.get(user.getId()) != null) {
           users.put(user.getId(), user);
       }
    }

    @Override
    public List<User> getAll() {
        return users.values().stream().collect(Collectors.toList());
    }

    public User findByEmailId(String email) {
        return users.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }

    public List<User> findByName(String name) {
        return users.values().stream()
                .filter(user -> name.equals(user.getName()))
                .collect(Collectors.toList());
    }
}
