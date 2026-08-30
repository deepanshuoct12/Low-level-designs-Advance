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
import org.project.model.User;
import org.project.model.Vote;
import org.project.exception.AnswerNotFoundException;
import org.project.exception.FlagNotFoundException;
import org.project.exception.InvalidTargetTypeException;
import org.project.exception.QuestionNotFoundException;
import org.project.exception.UnauthorizedActionException;
import org.project.observer.Subject;
import org.project.strategy.AnswerCommentStrategy;
import org.project.strategy.AnswerReportStrategy;
import org.project.strategy.CommentReportStratergy;
import org.project.strategy.ICommentStrategy;
import org.project.strategy.IReportStrategy;
import org.project.strategy.QuestionCommentStrategy;
import org.project.strategy.QuestionReportStrategy;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class StackOverFlowService extends Subject implements IStackOverFlowService {

    private static  StackOverFlowService instance;

    private final Map<TargetType, ICommentStrategy> commentStrategyMap = new EnumMap<>(TargetType.class);
    private final Map<TargetType, IReportStrategy> reportStrategyMap = new EnumMap<>(TargetType.class);

    private final QuestionService questionService = new QuestionService();
    private final AnswerService answerService = new AnswerService();
    private final VoteService voteService = new VoteService();
    private final FlagService flagService = new FlagService();
    private final UserService userService = new UserService();

    private StackOverFlowService() {
        commentStrategyMap.put(TargetType.QUESTION, new QuestionCommentStrategy());
        commentStrategyMap.put(TargetType.ANSWER, new AnswerCommentStrategy());

        reportStrategyMap.put(TargetType.QUESTION, new QuestionReportStrategy());
        reportStrategyMap.put(TargetType.ANSWER, new AnswerReportStrategy());
        reportStrategyMap.put(TargetType.COMMENT, new CommentReportStratergy());
    }

    public static StackOverFlowService getInstance() {
        if (instance == null) {
            synchronized (StackOverFlowService.class) {
                if (instance == null) {
                    instance = new StackOverFlowService();
                }
            }
        }
        return instance;
    }

    @Override
    public Question postQuestion(String userId, String title, String body, List<Tag> tags) {
        Question question = new Question();
        question.setAuthorId(userId);
        question.setTitle(title);
        question.setBody(body);
        question.setTags(tags);
        Question created = questionService.create(question);
        User author = userService.getById(userId);
        if (author != null) {
            add(author);
        }
        return created;
    }

    @Override
    public Answer postAnswer(String userId, String questionId, String body) {
        Answer answer = new Answer();
        answer.setAuthorId(userId);
        answer.setQuestionId(questionId);
        answer.setBody(body);
        Answer created = answerService.create(answer);
        notifyObservers(answer);
        return created;
    }

    @Override
    public Comment addComment(String userId, TargetType targetType, String targetId, String body) {
        ICommentStrategy strategy = commentStrategyMap.get(targetType);
        if (strategy == null) {
            throw new InvalidTargetTypeException("Cannot add a comment on target type: " + targetType);
        }
        return strategy.addComment(userId, targetId, body);
    }

    @Override
    public Vote addVote(String userId, PostType targetType, String targetId, VoteType voteType) {
        Vote vote = new Vote();
        vote.setUserId(userId);
        vote.setTargetType(targetType);
        vote.setTargetId(targetId);
        vote.setVoteType(voteType);
        return voteService.create(vote);
    }

    @Override
    public boolean acceptAnswer(String userId, String questionId, String answerId) {
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new QuestionNotFoundException("Question not found: " + questionId);
        }
        if (!question.getAuthorId().equals(userId)) {
            throw new UnauthorizedActionException("User " + userId + " is not authorized to accept an answer for question " + questionId);
        }
        Answer answer = answerService.getById(answerId);
        if (answer == null) {
            throw new AnswerNotFoundException("Answer not found: " + answerId);
        }
        answer.setAccepted(true);
        answerService.update(answerId, answer);
        question.setAcceptedAnswerId(answerId);
        questionService.update(questionId, question);
        return true;
    }

    @Override
    public Flag report(String userId, TargetType targetType, String targetId, FlagReason reason) {
        IReportStrategy strategy = reportStrategyMap.get(targetType);
        if (strategy == null) {
            throw new InvalidTargetTypeException("Cannot report target type: " + targetType);
        }
        return strategy.report(userId, targetId, reason);
    }

    @Override
    public Flag reviewFlag(String moderatorId, String flagId, FlagStatus resolution) {
        Flag flag = flagService.getById(flagId);
        if (flag == null) {
            throw new FlagNotFoundException("Flag not found: " + flagId);
        }
        flag.setStatus(resolution);
        flag.setResolvedBy(moderatorId);
        return flagService.update(flagId, flag);
    }
}
