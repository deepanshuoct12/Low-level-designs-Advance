package org.project.strategy;

import org.project.enums.PostType;
import org.project.model.Comment;
import org.project.service.CommentService;

public class QuestionCommentStrategy implements ICommentStrategy {

    private final CommentService commentService = new CommentService();

    @Override
    public Comment addComment(String userId, String targetId, String body) {
        Comment comment = new Comment();
        comment.setAuthorId(userId);
        comment.setBody(body);
        comment.setParentType(PostType.QUESTION);
        comment.setParentId(targetId);
        return commentService.create(comment);
    }
}
