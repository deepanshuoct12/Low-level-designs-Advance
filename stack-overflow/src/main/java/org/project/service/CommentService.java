package org.project.service;

import org.project.model.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CommentService {
    private static final Map<String, Comment> storage = new ConcurrentHashMap<>();

    public Comment create(Comment comment) {
        comment.setId(UUID.randomUUID().toString());
        long now = System.currentTimeMillis();
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);
        storage.put(comment.getId(), comment);
        return comment;
    }

    public Comment getById(String id) {
        return storage.get(id);
    }

    public List<Comment> getAll() {
        return new ArrayList<>(storage.values());
    }

    public Comment update(String id, Comment comment) {
        Comment existing = storage.get(id);
        if (existing == null) {
            return null;
        }
        comment.setId(id);
        comment.setCreatedAt(existing.getCreatedAt());
        comment.setUpdatedAt(System.currentTimeMillis());
        storage.put(id, comment);
        return comment;
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }
}
