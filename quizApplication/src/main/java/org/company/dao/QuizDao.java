package org.company.dao;

import org.company.model.Quiz;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuizDao implements IBaseDao<Quiz, String> {
    private static Map<String, Quiz> quizzes = new ConcurrentHashMap<>();

    @Override
    public Quiz get(String s) {
        return quizzes.get(s);
    }

    @Override
    public void save(Quiz quiz) {
         quizzes.put(quiz.getId(), quiz);
    }

    @Override
    public void deleteById(String s) {
      quizzes.remove(s);
    }

    @Override
    public void update(Quiz quiz) {
        quizzes.put(quiz.getId(), quiz);
        // Implementation to update Quiz
    }

    @Override
    public void delete(Quiz quiz) {
        quizzes.remove(quiz.getId());
    }
}
