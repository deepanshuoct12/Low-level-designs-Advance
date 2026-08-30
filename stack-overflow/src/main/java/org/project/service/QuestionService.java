package org.project.service;

import org.project.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class QuestionService {
    private static final Map<String, Question> storage = new ConcurrentHashMap<>();

    public Question create(Question question) {
        question.setId(UUID.randomUUID().toString());
        long now = System.currentTimeMillis();
        question.setCreatedAt(now);
        question.setUpdatedAt(now);
        storage.put(question.getId(), question);
        return question;
    }

    public Question getById(String id) {
        return storage.get(id);
    }

    public List<Question> getAll() {
        return new ArrayList<>(storage.values());
    }

    public Question update(String id, Question question) {
        Question existing = storage.get(id);
        if (existing == null) {
            return null;
        }
        question.setId(id);
        question.setCreatedAt(existing.getCreatedAt());
        question.setUpdatedAt(System.currentTimeMillis());
        storage.put(id, question);
        return question;
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }
}
