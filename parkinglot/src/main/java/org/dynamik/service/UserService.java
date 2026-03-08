package org.dynamik.service;

import org.dynamik.dao.UserDao;
import org.dynamik.model.User;

import java.util.List;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public User save(User user) {
        return userDao.save(user);
    }

    public User findById(String id) {
        return userDao.findById(id);
    }

    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public User findByEmailId(String email) {
        return userDao.findByEmailId(email);
    }

    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }
}
