package org.project.service;

import org.project.model.Match;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class MatchService {
    private static final ConcurrentHashMap<Long, Match> matchStorage = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Match create(Match match) {
        if (match.getId() == null) {
            match.setId(idCounter++);
        }
        match.setCreatedAt(System.currentTimeMillis());
        match.setUpdatedAt(System.currentTimeMillis());
        matchStorage.put(match.getId(), match);
        return match;
    }

    public Match getById(Long id) {
        return matchStorage.get(id);
    }

    public List<Match> getAll() {
        return new ArrayList<>(matchStorage.values());
    }

    public Match update(Long id, Match match) {
        Match existing = matchStorage.get(id);
        if (existing != null) {
            match.setId(id);
            match.setCreatedAt(existing.getCreatedAt());
            match.setUpdatedAt(System.currentTimeMillis());
            matchStorage.put(id, match);
            return match;
        }
        return null;
    }

    public boolean delete(Long id) {
        return matchStorage.remove(id) != null;
    }
}
