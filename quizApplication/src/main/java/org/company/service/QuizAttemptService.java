package org.company.service;

import org.company.dao.QuizAttemptDao;
import org.company.model.QuizAttempt;

import java.util.HashMap;

public class QuizAttemptService {
    private final QuizAttemptDao quizAttemptDao;

    public QuizAttemptService() {
        quizAttemptDao = new QuizAttemptDao();
    }

    public QuizAttempt createQuizAttempt(String quizId, String userId, int totalQuestions) {
        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuizId(quizId);
        attempt.setUserId(userId);
        attempt.setTotalQuestions(totalQuestions);
        attempt.setScore(0);
        attempt.setAnswers(new HashMap<>());
        quizAttemptDao.save(attempt);
        return attempt;
    }

    public QuizAttempt getQuizAttempt(String attemptId) {
        return quizAttemptDao.get(attemptId);
    }

    public void updateQuizAttempt(QuizAttempt attempt) {
        quizAttemptDao.update(attempt);
    }

    public void deleteQuizAttempt(String attemptId) {
        quizAttemptDao.deleteById(attemptId);
    }

    public void submitAnswer(String attemptId, String questionId, String optionId) {
        QuizAttempt attempt = quizAttemptDao.get(attemptId);
        if (attempt != null) {
            attempt.getAnswers().put(questionId, optionId);
            quizAttemptDao.update(attempt);
        }
    }

    public void updateScore(String attemptId, int score) {
        QuizAttempt attempt = quizAttemptDao.get(attemptId);
        if (attempt != null) {
            attempt.setScore(score);
            quizAttemptDao.update(attempt);
        }
    }

    public QuizAttempt getByUserIdAndQuizId(String userId, String quizId) {
        return quizAttemptDao.getByUserIdAndQuizId(userId, quizId);
    }
}
