package org.project.service;

import org.project.enums.FlagReason;
import org.project.enums.FlagStatus;
import org.project.enums.PostType;
import org.project.enums.Tag;
import org.project.enums.TargetType;
import org.project.enums.VoteType;
import org.project.model.Answer;
import org.project.model.Comment;
import org.project.model.Flag;
import org.project.model.Question;
import org.project.model.Vote;

import java.util.List;

public interface IStackOverFlowService {
    Question postQuestion(String userId, String title, String body, List<Tag> tags);

    Answer postAnswer(String userId, String questionId, String body);

    Comment addComment(String userId, TargetType targetType, String targetId, String body);

    Vote addVote(String userId, PostType targetType, String targetId, VoteType voteType);

    boolean acceptAnswer(String userId, String questionId, String answerId);

    Flag report(String userId, TargetType targetType, String targetId, FlagReason reason);

    Flag reviewFlag(String moderatorId, String flagId, FlagStatus resolution);
}
