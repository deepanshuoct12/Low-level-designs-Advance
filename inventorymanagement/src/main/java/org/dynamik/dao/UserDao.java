package org.dynamik.dao;

import org.dynamik.model.User;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserDao implements IBaseDao<User, String>{
    private static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    @Override
    public void update(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(String id) {
       users.remove(id);
    }

    @Override
    public void save(User user) {
      users.put(user.getId(), user);
    }

    @Override
    public User get(String id) {
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        return users.values().stream().toList();
    }
}
