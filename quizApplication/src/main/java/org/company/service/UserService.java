package org.company.service;

import org.company.dao.UserDao;
import org.company.model.User;

import java.util.UUID;

public class UserService {
    private final UserDao userDao;

    public UserService() {
       userDao = new UserDao();
    }

    public User createUser(String name, String phoneNumber) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());  // Generate and set a unique ID
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        userDao.save(user);
        return user;
    }

    public User getUser(String userId) {
        return userDao.get(userId);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(String userId) {
        userDao.deleteById(userId);
    }

    public void updatePhoneNumber(String userId, String newPhoneNumber) {
        User user = userDao.get(userId);
        if (user != null) {
            user.setPhoneNumber(newPhoneNumber);
            userDao.update(user);
        }
    }
}
