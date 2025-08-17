package org.dynamik.service;


import org.dynamik.dao.UserDao;
import org.dynamik.model.User;

import java.util.List;
import java.util.UUID;

public class UserService {
    
    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    /**
     * Creates a new user with the provided user details
     * @param user The user to create
     * @return The created user with generated ID
     */
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        
        // Check if user with this email already exists
        User existingUser = userDao.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalStateException("User with email " + user.getEmail() + " already exists");
        }
        
        // Set default balance if not provided
//        if (user.getBalance() == null) {
//            user.setBalance(0L);
//        }

        if (user.getId() == null || user.getId().trim().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }
        
        return userDao.save(user);
    }
    
    /**
     * Updates an existing user
     * @param user The user to update
     * @return The updated user
     */
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
            if (user.getId() == null || user.getId().trim().isEmpty()) {
                user.setId(UUID.randomUUID().toString());
            }
        return userDao.save(user);
    }


    public User findById(String id) {
        return userDao.getById(id);
    }


    public List<User> findAll() {
        return userDao.getAll();
    }


    public void deleteById(String id) {
        userDao.delete(id);
    }


    public User update(User user) {
        return userDao.save(user);
    }


    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }


//    public boolean authenticate(String email, String password) {
//        User user = userDao.findByEmail(email);
//        return user != null && user.getPassword().equals(password);
//    }


    public void updateBalance(String userId, Long amount) {
        User user = userDao.getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

       // user.setBalance(user.getBalance() + amount);
        userDao.save(user);
    }
}
