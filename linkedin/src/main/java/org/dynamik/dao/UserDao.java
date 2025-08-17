package org.dynamik.dao;

import org.dynamik.model.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of User data access object using an in-memory HashMap.
 * This class provides CRUD operations for User entities.
 */
public class UserDao implements IBaseDao<User, String> {
    private static final Map<String, User> users = new HashMap<>();

    /**
     * Saves a user to the data store. If the user already exists, it will be updated.
     *
     * @param user the user to save
     * @return the saved user
     * @throws IllegalArgumentException if user is null or has no ID
     */
    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        users.put(user.getId(), user);
        return user;
    }

    /**
     * Finds all users that match the given user's properties.
     * If the input user is null, returns all users.
     *
     * @param user the user to use as a filter (can be null)
     * @return a list of matching users, or empty list if none found
     */
    @Override
    public List<User> findAll() {
        if (users == null) {
            return new ArrayList<>();
        }
        
        return users.values().stream().collect(Collectors.toList());
    }

    /**
     * Deletes a user with the specified ID.
     *
     * @param id the ID of the user to delete
     * @throws IllegalArgumentException if id is null or empty
     */
    @Override
    public void delete(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        users.remove(id);
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user to find
     * @return the user with the specified ID, or null if not found
     * @throws IllegalArgumentException if id is null or empty
     */
    @Override
    public User findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        return users.get(id);
    }


}
