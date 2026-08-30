package org.project.service;

import org.project.model.DisplayScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DisplayScreenService {
    private static final Map<String, DisplayScreen> STORE = new ConcurrentHashMap<>();

    public DisplayScreen create(DisplayScreen displayScreen) {
        if (displayScreen.getId() == null) {
            displayScreen.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        displayScreen.setCreatedAt(now);
        displayScreen.setUpdatedAt(now);
        STORE.put(displayScreen.getId(), displayScreen);
        return displayScreen;
    }

    public DisplayScreen getById(String id) {
        return STORE.get(id);
    }

    public List<DisplayScreen> getAll() {
        return new ArrayList<>(STORE.values());
    }

    public DisplayScreen update(String id, DisplayScreen displayScreen) {
        if (!STORE.containsKey(id)) {
            return null;
        }
        displayScreen.setId(id);
        displayScreen.setUpdatedAt(System.currentTimeMillis());
        STORE.put(id, displayScreen);
        return displayScreen;
    }

    public boolean delete(String id) {
        return STORE.remove(id) != null;
    }
}
