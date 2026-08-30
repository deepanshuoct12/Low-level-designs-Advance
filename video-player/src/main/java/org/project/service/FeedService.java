package org.project.service;

import org.project.model.Feed;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FeedService {
    private static final Map<Long, Feed> feedStore = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    public Feed create(Feed feed) {
        feed.setId(idGenerator.getAndIncrement());
        feed.onCreate();
        feedStore.put(feed.getId(), feed);
        return feed;
    }

    public Feed getById(Long id) {
        return feedStore.get(id);
    }

    public List<Feed> getAll() {
        return feedStore.values().stream().collect(Collectors.toList());
    }

    public Feed update(Long id, Feed feed) {
        if (feedStore.containsKey(id)) {
            feed.setId(id);
            feed.onUpdate();
            feedStore.put(id, feed);
            return feed;
        }
        return null;
    }

    public List<Feed> getFeedByUserId(Long userId) {
        return feedStore.values().stream().filter(feed -> feed.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        return feedStore.remove(id) != null;
    }
}
