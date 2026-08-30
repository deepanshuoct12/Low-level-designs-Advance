package org.project.service;

import org.project.model.Leaderboard;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

public class LeaderboardService {
    private static final ConcurrentHashMap<Long, Leaderboard> leaderboardStorage = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Leaderboard create(Leaderboard leaderboard) {
        if (leaderboard.getId() == null) {
            leaderboard.setId(idCounter++);
        }
        leaderboard.setCreatedAt(System.currentTimeMillis());
        leaderboard.setUpdatedAt(System.currentTimeMillis());
        leaderboardStorage.put(leaderboard.getId(), leaderboard);
        return leaderboard;
    }

    public Leaderboard getById(Long id) {
        return leaderboardStorage.get(id);
    }

    public List<Leaderboard> getAll() {
        return new ArrayList<>(leaderboardStorage.values());
    }

    public Leaderboard update(Long id, Leaderboard leaderboard) {
        Leaderboard existing = leaderboardStorage.get(id);
        if (existing != null) {
            leaderboard.setId(id);
            leaderboard.setCreatedAt(existing.getCreatedAt());
            leaderboard.setUpdatedAt(System.currentTimeMillis());
            leaderboardStorage.put(id, leaderboard);
            return leaderboard;
        }
        return null;
    }

    public boolean delete(Long id) {
        return leaderboardStorage.remove(id) != null;
    }
}
