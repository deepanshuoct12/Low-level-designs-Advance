package org.dynamik.dao;

import org.dynamik.model.Like;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Like data access object using an in-memory HashMap.
 * This class provides CRUD operations for Like entities.
 */
public class LikeDao implements IBaseDao<Like, String> {
    private static final Map<String, Like> likes = new HashMap<>();

    @Override
    public Like save(Like like) {
        if (like == null) {
            throw new IllegalArgumentException("Like cannot be null");
        }
        if (like.getId() == null || like.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Like ID cannot be null or empty");
        }
        likes.put(like.getId(), like);
        return like;
    }

    @Override
    public List<Like> findAll() {
        return new ArrayList<>(likes.values());
    }

    @Override
    public void delete(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Like ID cannot be null or empty");
        }
        likes.remove(id);
    }

    @Override
    public Like findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Like ID cannot be null or empty");
        }
        return likes.get(id);
    }

    /**
     * Find all likes for a specific post
     * @param postId the ID of the post
     * @return list of likes for the specified post
     */
    public List<Like> findByPostId(String postId) {
        if (postId == null || postId.trim().isEmpty()) {
            throw new IllegalArgumentException("Post ID cannot be null or empty");
        }
        return likes.values().stream()
                .filter(like -> postId.equals(like.getPostId()))
                .collect(Collectors.toList());
    }

    /**
     * Find all likes by a specific user
     * @param userId the ID of the user
     * @return list of likes by the specified user
     */
    public List<Like> findByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        return likes.values().stream()
                .filter(like -> userId.equals(like.getUserId()))
                .collect(Collectors.toList());
    }

    /**
     * Check if a user has liked a specific post
     * @param userId the ID of the user
     * @param postId the ID of the post
     * @return true if the user has liked the post, false otherwise
     */
    public boolean existsByUserAndPost(String userId, String postId) {
        if (userId == null || userId.trim().isEmpty() || postId == null || postId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID and Post ID cannot be null or empty");
        }
        return likes.values().stream()
                .anyMatch(like -> 
                    userId.equals(like.getUserId()) &&
                    postId.equals(like.getPostId())
                );
    }

    /**
     * Remove a like by user ID and post ID
     * @param userId the ID of the user
     * @param postId the ID of the post
     * @return true if a like was found and removed, false otherwise
     */
    public boolean removeByUserAndPost(String userId, String postId) {
        if (userId == null || userId.trim().isEmpty() || postId == null || postId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID and Post ID cannot be null or empty");
        }
        return likes.entrySet().removeIf(entry -> 
            userId.equals(entry.getValue().getUserId()) &&
            postId.equals(entry.getValue().getPostId())
        );
    }
}
