package org.project.service;

import org.project.model.Signal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SignalService {
    private static final Map<String, Signal> STORE = new ConcurrentHashMap<>();

    public Signal create(Signal signal) {
        if (signal.getId() == null) {
            signal.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        signal.setCreatedAt(now);
        signal.setUpdatedAt(now);
        STORE.put(signal.getId(), signal);
        return signal;
    }

    public Signal getById(String id) {
        return STORE.get(id);
    }

    public List<Signal> getAll() {
        return new ArrayList<>(STORE.values());
    }

    public Signal update(String id, Signal signal) {
        if (!STORE.containsKey(id)) {
            return null;
        }
        signal.setId(id);
        signal.setUpdatedAt(System.currentTimeMillis());
        STORE.put(id, signal);
        return signal;
    }

    public boolean delete(String id) {
        return STORE.remove(id) != null;
    }
}
