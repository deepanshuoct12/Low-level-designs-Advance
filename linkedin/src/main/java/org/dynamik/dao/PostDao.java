package org.dynamik.dao;

import org.dynamik.model.Post;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Post data access object using an in-memory HashMap.
 * This class provides CRUD operations for Post entities.
 */
public class PostDao implements IBaseDao<Post, String> {
    private static final Map<String, Post> posts = new HashMap<>();

    @Override
    public Post save(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }
        if (post.getId() == null || post.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Post ID cannot be null or empty");
        }
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return posts.values().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Post ID cannot be null or empty");
        }
        posts.remove(id);
    }

    @Override
    public Post findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Post ID cannot be null or empty");
        }
        return posts.get(id);
    }

    /**
     * Find all posts by a specific user
     * @param userId the ID of the user
     * @return list of posts by the specified user, ordered by creation time (newest first)
     */
    public List<Post> findByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        return posts.values().stream()
                .filter(post -> userId.equals(post.getUserId()))
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Find posts containing specific text in content (case-insensitive)
     * @param searchText the text to search for
     * @return list of matching posts
     */
    public List<Post> searchInContent(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchTerm = searchText.toLowerCase();
        return posts.values().stream()
                .filter(post -> 
                    post.getContent() != null && 
                    post.getContent().toLowerCase().contains(searchTerm)
                )
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Find the most recent posts with pagination
     * @param page the page number (0-based)
     * @param size the number of posts per page
     * @return list of posts for the specified page
     */
    public List<Post> findRecentPosts(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be positive");
        }
        
        int skip = page * size;
        return posts.values().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .skip(skip)
                .limit(size)
                .collect(Collectors.toList());
    }
}
