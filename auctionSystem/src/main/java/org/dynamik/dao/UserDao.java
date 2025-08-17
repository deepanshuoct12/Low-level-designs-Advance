package org.dynamik.dao;

import org.dynamik.model.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDao implements IBaseDao<User,String> {
    private static final Map<String,User> users = new ConcurrentHashMap<>();


    @Override
    public User findById(String s) {
       return users.get(s);
    }

    @Override
    public User save(User user) {
        return users.put(user.getId(),user);
    }

    @Override
    public void delete(User user) {
     users.remove(user.getId());
    }

    @Override
    public void deleteById(String s) {
        users.remove(s);
    }

    @Override
    public User update(User user) {
        return users.put(user.getId(),user);
    }

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }
}
