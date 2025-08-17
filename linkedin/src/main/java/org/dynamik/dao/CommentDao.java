package org.dynamik.dao;

import org.dynamik.model.Comment;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Comment data access object using an in-memory HashMap.
 * This class provides CRUD operations for Comment entities.
 */
public class CommentDao implements IBaseDao<Comment, String> {
    private static final Map<String, Comment> comments = new HashMap<>();

    @Override
    public Comment save(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }
        if (comment.getId() == null || comment.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment ID cannot be null or empty");
        }
        comments.put(comment.getId(), comment);
        return comment;
    }

    @Override
    public List<Comment> findAll() {
        if (comments == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(comments.values());
    }

    @Override
    public void delete(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment ID cannot be null or empty");
        }
        comments.remove(id);
    }

    @Override
    public Comment findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment ID cannot be null or empty");
        }
        return comments.get(id);
    }

    /**
     * Find all comments for a specific post
     * @param postId the ID of the post
     * @return list of comments for the specified post
     */
    public List<Comment> findByPostId(String postId) {
        if (postId == null || postId.trim().isEmpty()) {
            throw new IllegalArgumentException("Post ID cannot be null or empty");
        }
        return comments.values().stream()
                .filter(comment -> postId.equals(comment.getPost_id()))
                .collect(Collectors.toList());
    }

    /**
     * Find all comments by a specific user
     * @param userId the ID of the user
     * @return list of comments by the specified user
     */
    public List<Comment> findByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        return comments.values().stream()
                .filter(comment -> userId.equals(comment.getUser_id()))
                .collect(Collectors.toList());
    }
}
