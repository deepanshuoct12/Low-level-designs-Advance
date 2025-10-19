package org.company.dao;

import org.company.model.QuizAttempt;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuizAttemptDao implements IBaseDao<QuizAttempt, String> {
    private static Map<String, QuizAttempt> attempts = new ConcurrentHashMap<>();

    @Override
    public QuizAttempt get(String id) {
        return attempts.get(id);
    }

    @Override
    public void save(QuizAttempt quizAttempt) {
        attempts.put(quizAttempt.getId(), quizAttempt);
    }

    @Override
    public void deleteById(String id) {
        attempts.remove(id);
    }

    @Override
    public void update(QuizAttempt quizAttempt) {
        attempts.put(quizAttempt.getId(), quizAttempt);
    }

    @Override
    public void delete(QuizAttempt quizAttempt) {
        attempts.remove(quizAttempt.getId());
    }

    public QuizAttempt getByUserIdAndQuizId(String userId, String quizId) {
        return attempts.values().stream()
                .filter(attempt -> attempt.getUserId().equals(userId) && attempt.getQuizId().equals(quizId))
                .findFirst()
                .orElse(null);
    }
}
