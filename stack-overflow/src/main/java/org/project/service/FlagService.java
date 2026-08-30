package org.project.service;

import org.project.model.Flag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FlagService {
    private static final Map<String, Flag> storage = new ConcurrentHashMap<>();

    public Flag create(Flag flag) {
        flag.setId(UUID.randomUUID().toString());
        long now = System.currentTimeMillis();
        flag.setCreatedAt(now);
        flag.setUpdatedAt(now);
        storage.put(flag.getId(), flag);
        return flag;
    }

    public Flag getById(String id) {
        return storage.get(id);
    }

    public List<Flag> getAll() {
        return new ArrayList<>(storage.values());
    }

    public Flag update(String id, Flag flag) {
        Flag existing = storage.get(id);
        if (existing == null) {
            return null;
        }
        flag.setId(id);
        flag.setCreatedAt(existing.getCreatedAt());
        flag.setUpdatedAt(System.currentTimeMillis());
        storage.put(id, flag);
        return flag;
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }
}
