package org.company.dao;

import org.company.model.Answer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AnswerDao implements IBaseDao<Answer, String> {
    private static Map<String, Answer> answers = new ConcurrentHashMap<>();

    @Override
    public Answer get(String id) {
        return answers.get(id);
    }

    @Override
    public void save(Answer answer) {
        answers.put(answer.getId(), answer);
    }

    @Override
    public void deleteById(String id) {
        answers.remove(id);
    }

    @Override
    public void update(Answer answer) {
        answers.put(answer.getId(), answer);
    }

    @Override
    public void delete(Answer answer) {
        answers.remove(answer.getId());
    }

    public Answer getAnswerByQuestionId(String questionId) {
        return answers.values().stream()
                .filter(answer -> answer.getQuestionId().equals(questionId))
                .findFirst()
                .orElse(null);
    }
}
