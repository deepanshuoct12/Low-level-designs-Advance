package org.project.service;

import org.project.model.Split;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SplitService {
    private static final ConcurrentHashMap<String, Split> SPLITS = new ConcurrentHashMap<>();

    public Split create(Split split) {
        if (split.getId() == null) {
            split.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        split.setCreatedAt(now);
        split.setUpdatedAt(now);
        SPLITS.put(split.getId(), split);
        return split;
    }

    public Split getById(String id) {
        return SPLITS.get(id);
    }

    public List<Split> getAll() {
        return new ArrayList<>(SPLITS.values());
    }

    public Split update(Split split) {
        if (!SPLITS.containsKey(split.getId())) {
            return null;
        }
        split.setUpdatedAt(System.currentTimeMillis());
        SPLITS.put(split.getId(), split);
        return split;
    }

    public void delete(String id) {
        SPLITS.remove(id);
    }
}
