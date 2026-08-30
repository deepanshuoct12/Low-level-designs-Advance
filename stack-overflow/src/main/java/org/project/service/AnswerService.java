package org.project.service;

import org.project.model.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AnswerService {
    private static final Map<String, Answer> storage = new ConcurrentHashMap<>();

    public Answer create(Answer answer) {
        answer.setId(UUID.randomUUID().toString());
        long now = System.currentTimeMillis();
        answer.setCreatedAt(now);
        answer.setUpdatedAt(now);
        storage.put(answer.getId(), answer);
        return answer;
    }

    public Answer getById(String id) {
        return storage.get(id);
    }

    public List<Answer> getAll() {
        return new ArrayList<>(storage.values());
    }

    public Answer update(String id, Answer answer) {
        Answer existing = storage.get(id);
        if (existing == null) {
            return null;
        }
        answer.setId(id);
        answer.setCreatedAt(existing.getCreatedAt());
        answer.setUpdatedAt(System.currentTimeMillis());
        storage.put(id, answer);
        return answer;
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }
}
