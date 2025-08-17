package org.dynamik.dao;

import org.dynamik.model.Subscriber;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class SubscriberDao implements BaseDao<Subscriber, String> {
    private static final Map<String, Subscriber> subscribers = new ConcurrentHashMap<>();
    private static final Map<String, List<String>> topicSubscribers = new ConcurrentHashMap<>();

    public void subscribeToTopic(String topicId, String subscriberId) {
        topicSubscribers.computeIfAbsent(topicId, k -> new CopyOnWriteArrayList<>())
                       .add(subscriberId);
    }

    public void unsubscribeFromTopic(String topicId, String subscriberId) {
        List<String> subscribers = topicSubscribers.get(topicId);
        if (subscribers != null) {
            subscribers.remove(subscriberId);
        }
    }

    public List<String> getSubscribersForTopic(String topicId) {
        return topicSubscribers.getOrDefault(topicId, List.of());
    }

    @Override
    public Subscriber save(Subscriber subscriber) {
        subscribers.put(subscriber.getId(), subscriber);
        return subscriber;
    }

    @Override
    public Subscriber findById(String id) {
        return subscribers.get(id);
    }

    @Override
    public void delete(Subscriber subscriber) {
        subscribers.remove(subscriber.getId());
        // Remove from all topics
        topicSubscribers.values().forEach(list -> list.remove(subscriber.getId()));
    }

    @Override
    public void deleteById(String id) {
        subscribers.remove(id);
        // Remove from all topics
        topicSubscribers.values().forEach(list -> list.remove(id));
    }

    @Override
    public boolean existsById(String id) {
        return subscribers.containsKey(id);
    }

    public Subscriber findByName(String name) {
        return subscribers.values().stream()
                .filter(subscriber -> subscriber.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
