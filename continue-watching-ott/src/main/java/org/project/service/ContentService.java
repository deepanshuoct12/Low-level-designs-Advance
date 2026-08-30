package org.project.service;

import org.project.model.Content;
import org.project.validator.ContentValidator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class ContentService {
    private static final Map<Long, Content> contentStorage = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    public Content create(Content content) {
        ContentValidator.validateForCreate(content);
        Long id = idGenerator.getAndIncrement();
        content.setId(id);
        contentStorage.put(id, content);
        return content;
    }

    public Content getById(Long id) {
        return contentStorage.get(id);
    }

    public List<Content> getAll() {
        return new java.util.ArrayList<>(contentStorage.values());
    }

    public Content update(Long id, Content content) {
        ContentValidator.validateForUpdate(id, content);
        if (contentStorage.containsKey(id)) {
            content.setId(id);
            content.updateTimestamp();
            contentStorage.put(id, content);
            return content;
        }
        return null;
    }

    public boolean delete(Long id) {
        return contentStorage.remove(id) != null;
    }

    public void clear() {
        contentStorage.clear();
    }
}
