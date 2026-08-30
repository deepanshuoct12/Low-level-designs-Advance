package org.project.service;

import org.project.model.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TimerService {
    private static final Map<String, Timer> STORE = new ConcurrentHashMap<>();

    public Timer create(Timer timer) {
        if (timer.getId() == null) {
            timer.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        timer.setCreatedAt(now);
        timer.setUpdatedAt(now);
        STORE.put(timer.getId(), timer);
        return timer;
    }

    public Timer getById(String id) {
        return STORE.get(id);
    }

    public List<Timer> getAll() {
        return new ArrayList<>(STORE.values());
    }

    public Timer update(String id, Timer timer) {
        if (!STORE.containsKey(id)) {
            return null;
        }
        timer.setId(id);
        timer.setUpdatedAt(System.currentTimeMillis());
        STORE.put(id, timer);
        return timer;
    }

    public boolean delete(String id) {
        return STORE.remove(id) != null;
    }
}
