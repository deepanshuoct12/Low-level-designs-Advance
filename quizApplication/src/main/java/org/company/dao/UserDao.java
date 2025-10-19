package org.company.dao;

import org.company.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDao implements IBaseDao<User, String> {

    private static Map<String, User> users = new ConcurrentHashMap<>(); //<>

    @Override
    public User get(String s) {
        return users.get(s);
    }

    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void deleteById(String s) {
      users.remove(s);
    }

    @Override
    public void update(User user) {
     users.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
     users.remove(user.getId());
    }
}
