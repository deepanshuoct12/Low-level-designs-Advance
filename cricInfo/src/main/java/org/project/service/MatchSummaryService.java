package org.project.service;

import org.project.model.MatchSummary;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class MatchSummaryService {
    private static final ConcurrentHashMap<Long, MatchSummary> matchSummaryStorage = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public MatchSummary create(MatchSummary matchSummary) {
        if (matchSummary.getId() == null) {
            matchSummary.setId(idCounter++);
        }
        matchSummary.setCreatedAt(System.currentTimeMillis());
        matchSummary.setUpdatedAt(System.currentTimeMillis());
        matchSummaryStorage.put(matchSummary.getId(), matchSummary);
        return matchSummary;
    }

    public MatchSummary getById(Long id) {
        return matchSummaryStorage.get(id);
    }

    public List<MatchSummary> getAll() {
        return new ArrayList<>(matchSummaryStorage.values());
    }

    public MatchSummary update(Long id, MatchSummary matchSummary) {
        MatchSummary existing = matchSummaryStorage.get(id);
        if (existing != null) {
            matchSummary.setId(id);
            matchSummary.setCreatedAt(existing.getCreatedAt());
            matchSummary.setUpdatedAt(System.currentTimeMillis());
            matchSummaryStorage.put(id, matchSummary);
            return matchSummary;
        }
        return null;
    }

    public boolean delete(Long id) {
        return matchSummaryStorage.remove(id) != null;
    }
}
