package org.dynamik.service;

import org.dynamik.dao.UserDao;
import org.dynamik.model.User;

import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();

    public void saveUser(User user) {
        userDao.save(user);
    }

    public User getUser(String id) {
        return userDao.get(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(String id) {
        userDao.delete(id);
    }
}
