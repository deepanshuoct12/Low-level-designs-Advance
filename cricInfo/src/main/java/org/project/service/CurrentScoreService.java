package org.project.service;

import org.project.model.CurrentScore;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class CurrentScoreService {
    private static final ConcurrentHashMap<Long, CurrentScore> currentScoreStorage = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public CurrentScore create(CurrentScore currentScore) {
        if (currentScore.getId() == null) {
            currentScore.setId(idCounter++);
        }
        currentScore.setCreatedAt(System.currentTimeMillis());
        currentScore.setUpdatedAt(System.currentTimeMillis());
        currentScoreStorage.put(currentScore.getId(), currentScore);
        return currentScore;
    }

    public CurrentScore getById(Long id) {
        return currentScoreStorage.get(id);
    }

    public List<CurrentScore> getAll() {
        return new ArrayList<>(currentScoreStorage.values());
    }

    public CurrentScore update(Long id, CurrentScore currentScore) {
        CurrentScore existing = currentScoreStorage.get(id);
        if (existing != null) {
            currentScore.setId(id);
            currentScore.setCreatedAt(existing.getCreatedAt());
            currentScore.setUpdatedAt(System.currentTimeMillis());
            currentScoreStorage.put(id, currentScore);
            return currentScore;
        }
        return null;
    }

    public boolean delete(Long id) {
        return currentScoreStorage.remove(id) != null;
    }
}
