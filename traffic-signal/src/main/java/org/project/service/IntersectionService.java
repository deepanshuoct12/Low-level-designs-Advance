package org.project.service;

import org.project.model.Intersection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class IntersectionService {
    private static final Map<String, Intersection> STORE = new ConcurrentHashMap<>();

    public Intersection create(Intersection intersection) {
        if (intersection.getId() == null) {
            intersection.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        intersection.setCreatedAt(now);
        intersection.setUpdatedAt(now);
        STORE.put(intersection.getId(), intersection);
        return intersection;
    }

    public Intersection getById(String id) {
        return STORE.get(id);
    }

    public List<Intersection> getAll() {
        return new ArrayList<>(STORE.values());
    }

    public Intersection update(String id, Intersection intersection) {
        if (!STORE.containsKey(id)) {
            return null;
        }
        intersection.setId(id);
        intersection.setUpdatedAt(System.currentTimeMillis());
        STORE.put(id, intersection);
        return intersection;
    }

    public boolean delete(String id) {
        return STORE.remove(id) != null;
    }
}
