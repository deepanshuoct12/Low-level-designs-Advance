package org.project.service;

import org.project.model.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VoteService {
    private static final Map<String, Vote> storage = new ConcurrentHashMap<>();

    public Vote create(Vote vote) {
        vote.setId(UUID.randomUUID().toString());
        long now = System.currentTimeMillis();
        vote.setCreatedAt(now);
        vote.setUpdatedAt(now);
        storage.put(vote.getId(), vote);
        return vote;
    }

    public Vote getById(String id) {
        return storage.get(id);
    }

    public List<Vote> getAll() {
        return new ArrayList<>(storage.values());
    }

    public Vote update(String id, Vote vote) {
        Vote existing = storage.get(id);
        if (existing == null) {
            return null;
        }
        vote.setId(id);
        vote.setCreatedAt(existing.getCreatedAt());
        vote.setUpdatedAt(System.currentTimeMillis());
        storage.put(id, vote);
        return vote;
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }
}
