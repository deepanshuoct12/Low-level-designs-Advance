package org.project.service;

import org.project.model.WatchProgress;
import org.project.validator.WatchProgressValidator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


public class WatchProgressService {
    private static final Map<Long, WatchProgress> watchProgressStorage = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    public WatchProgress create(WatchProgress watchProgress) {
        WatchProgressValidator.validateForCreate(watchProgress);
        Long id = idGenerator.getAndIncrement();
        watchProgress.setId(id);
        watchProgressStorage.put(id, watchProgress);
        return watchProgress;
    }

    public WatchProgress getById(Long id) {
        return watchProgressStorage.get(id);
    }

    public List<WatchProgress> getAll() {
        return new java.util.ArrayList<>(watchProgressStorage.values());
    }

    public List<WatchProgress> getContinueWatching(Long userId) {
        return watchProgressStorage.values().stream()
                .filter(wp -> wp.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public WatchProgress update(Long id, WatchProgress watchProgress) {
        WatchProgressValidator.validateForUpdate(id, watchProgress);
        if (watchProgressStorage.containsKey(id)) {
            watchProgress.setId(id);
            watchProgress.updateTimestamp();
            watchProgressStorage.put(id, watchProgress);
            return watchProgress;
        }
        return null;
    }

    public boolean delete(Long id) {
        return watchProgressStorage.remove(id) != null;
    }

    public void clear() {
        watchProgressStorage.clear();
    }
}
