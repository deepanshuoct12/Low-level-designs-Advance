package org.dynamik.service;

import org.dynamik.dao.UserDao;
import org.dynamik.model.User;

import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public void registerUser(User user) {
        userDao.save(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }


    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    public User findById(String id) {
        return userDao.findById(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }
}
