package org.dynamik.service;

import org.dynamik.dao.CommentDao;
import org.dynamik.model.Comment;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CommentService {
    private final CommentDao commentDao;

    public CommentService() {
        this.commentDao = new CommentDao();
    }

    public Comment addComment(Comment comment) {
        if (comment.getId() == null || comment.getId().trim().isEmpty()) {
            comment.setId(UUID.randomUUID().toString());
        }

        return commentDao.save(comment);
    }

    public Comment getCommentById(String commentId) {
        return commentDao.findById(commentId);
    }

    public List<Comment> getCommentsByPost(String postId) {
        return commentDao.findByPostId(postId);
    }

    public List<Comment> getCommentsByUser(String userId) {
        return commentDao.findByUserId(userId);
    }

    public void deleteComment(String commentId) {
        commentDao.delete(commentId);
    }
}
