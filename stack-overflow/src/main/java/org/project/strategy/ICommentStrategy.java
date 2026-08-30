package org.project.strategy;

import org.project.model.Comment;

public interface ICommentStrategy {
    Comment addComment(String userId, String targetId, String body);
}
