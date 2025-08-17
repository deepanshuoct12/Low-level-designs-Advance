package org.dynamik.service;

import org.dynamik.dao.UserDao;
import org.dynamik.model.User;

import java.util.List;
import java.util.Objects;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public User createUser(User user) {
        return userDao.save(user);
    }

    public User getUserById(String userId) {
        return userDao.findById(userId);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void deleteUser(String userId) {
        userDao.delete(userId);
    }

    public User updateUser(User user) {
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty for update");
        }
        return userDao.save(user);
    }
}
