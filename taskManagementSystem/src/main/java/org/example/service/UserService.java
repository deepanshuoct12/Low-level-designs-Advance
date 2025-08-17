package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;

import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();

    public User saveUser(User user) {
        return userDao.save(user);
    }

    public User findUserById(String id) {
        return userDao.findById(id);
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    public void deleteUser(String id) {
        userDao.delete(id);
    }

    public void updateUser(String id, User user) {
        userDao.update(id, user);
    }
}
