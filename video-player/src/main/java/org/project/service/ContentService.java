package org.project.service;

import org.project.model.Content;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ContentService {
    private static final Map<Long, Content> contentStore = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    public Content create(Content content) {
        content.setId(idGenerator.getAndIncrement());
        content.onCreate();
        contentStore.put(content.getId(), content);
        return content;
    }

    public Content getById(Long id) {
        return contentStore.get(id);
    }

    public List<Content> getAll() {
        return contentStore.values().stream().collect(Collectors.toList());
    }

    public Content update(Long id, Content content) {
        if (contentStore.containsKey(id)) {
            content.setId(id);
            content.onUpdate();
            contentStore.put(id, content);
            return content;
        }
        return null;
    }

    public boolean delete(Long id) {
        return contentStore.remove(id) != null;
    }
}
