package org.project.service;

import org.project.model.Watch;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class WatchService {
    private static final Map<Long, Watch> watchStore = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    public Watch create(Watch watch) {
        watch.setId(idGenerator.getAndIncrement());
        watch.onCreate();
        watchStore.put(watch.getId(), watch);
        return watch;
    }

    public Watch getById(Long id) {
        return watchStore.get(id);
    }

    public List<Watch> getAll() {
        return watchStore.values().stream().collect(Collectors.toList());
    }

    public Watch update(Long id, Watch watch) {
        if (watchStore.containsKey(id)) {
            watch.setId(id);
            watch.onUpdate();
            watchStore.put(id, watch);
            return watch;
        }

        return null;
    }

    public boolean delete(Long id) {
        return watchStore.remove(id) != null;
    }

    public Watch getByUserIdAndContentId(Long userId, Long contentId) {
        return watchStore.values().stream()
                .filter(w -> w.getUserId().equals(userId) && w.getContentId().equals(contentId))
                .findFirst()
                .orElse(null);
    }
}
