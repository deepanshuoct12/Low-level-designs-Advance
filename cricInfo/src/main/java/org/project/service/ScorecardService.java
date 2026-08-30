package org.project.service;

import org.project.model.Scorecard;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class ScorecardService {
    private static final ConcurrentHashMap<Long, Scorecard> scorecardStorage = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Scorecard create(Scorecard scorecard) {
        if (scorecard.getId() == null) {
            scorecard.setId(idCounter++);
        }
        scorecard.setCreatedAt(System.currentTimeMillis());
        scorecard.setUpdatedAt(System.currentTimeMillis());
        scorecardStorage.put(scorecard.getId(), scorecard);
        return scorecard;
    }

    public Scorecard getById(Long id) {
        return scorecardStorage.get(id);
    }

    public List<Scorecard> getAll() {
        return new ArrayList<>(scorecardStorage.values());
    }

    public Scorecard update(Long id, Scorecard scorecard) {
        Scorecard existing = scorecardStorage.get(id);
        if (existing != null) {
            scorecard.setId(id);
            scorecard.setCreatedAt(existing.getCreatedAt());
            scorecard.setUpdatedAt(System.currentTimeMillis());
            scorecardStorage.put(id, scorecard);
            return scorecard;
        }
        return null;
    }

    public boolean delete(Long id) {
        return scorecardStorage.remove(id) != null;
    }
}
