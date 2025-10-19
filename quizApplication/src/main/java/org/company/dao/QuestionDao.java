package org.company.dao;

import org.company.model.Question;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuestionDao implements IBaseDao<Question, String> {
    private static Map<String, Question> questions = new ConcurrentHashMap<>();

    @Override
    public Question get(String id) {
        return questions.get(id);
    }

    @Override
    public void save(Question question) {
        questions.put(question.getId(), question);
    }

    @Override
    public void deleteById(String id) {
        questions.remove(id);
    }

    @Override
    public void update(Question question) {
        questions.put(question.getId(), question);
    }

    @Override
    public void delete(Question question) {
        questions.remove(question.getId());
    }

    public List<Question> getQuestionsByQuizId(String quizId) {
        return questions.values().stream()
                .filter(q -> q.getQuizId().equals(quizId))
                .toList();
    }
}
