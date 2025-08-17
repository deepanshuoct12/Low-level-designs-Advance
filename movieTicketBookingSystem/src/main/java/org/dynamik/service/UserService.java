package org.dynamik.service;

import org.dynamik.dao.UserDao;
import org.dynamik.model.User;
import org.dynamik.enums.UserRole;

import java.util.List;
import java.util.stream.Collectors;

public class UserService implements BaseService<User, String> {
    private final UserDao userDao = new UserDao();

    @Override
    public User save(User user) {
        userDao.save(user);
        return user;
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

    public List<User> findByRole(UserRole role) {
        return userDao.findAll().stream()
                .filter(user -> user.getRole() == role)
                .collect(Collectors.toList());
    }
}
