package org.company.service;

import org.company.dao.QuizDao;
import org.company.model.Quiz;

public class QuizService {
    private final QuizDao quizDao;

    public QuizService() {
        quizDao = new QuizDao();
    }

    public Quiz createQuiz(String name, String description, String imageUrl, String category, int totalQuestions) {
        Quiz quiz = new Quiz();
        quiz.setName(name);
        quiz.setDescription(description);
        quiz.setImageUrl(imageUrl);
        quiz.setCategory(category);
        quiz.setTotalQuestions(totalQuestions);
        quizDao.save(quiz);
        return quiz;
    }

    public Quiz getQuiz(String quizId) {
        return quizDao.get(quizId);
    }

    public void updateQuiz(Quiz quiz) {
        quizDao.update(quiz);
    }

    public void deleteQuiz(String quizId) {
        quizDao.deleteById(quizId);
    }

    public void updateQuizDetails(String quizId, String name, String description, String imageUrl, String category) {
        Quiz quiz = quizDao.get(quizId);
        if (quiz != null) {
            if (name != null) quiz.setName(name);
            if (description != null) quiz.setDescription(description);
            if (imageUrl != null) quiz.setImageUrl(imageUrl);
            if (category != null) quiz.setCategory(category);
            quizDao.update(quiz);
        }
    }

    public void save(Quiz quiz) {
        quizDao.save(quiz);
    }
}
