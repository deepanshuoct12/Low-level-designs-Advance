package org.dynamik.dao;

import org.dynamik.model.Topic;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TopicDao implements BaseDao<Topic, String> {
    private static final Map<String, Topic> topics = new ConcurrentHashMap<>();
    private static final Map<String, Topic> topicsByName = new ConcurrentHashMap<>();

    public Topic findByName(String name) {
        return topicsByName.get(name);
    }

    @Override
    public Topic save(Topic topic) {
        topics.put(topic.getId(), topic);
        topicsByName.put(topic.getTopicName(), topic);
        return topic;
    }

    @Override
    public Topic findById(String id) {
        return topics.get(id);
    }

    @Override
    public void delete(Topic topic) {
        topics.remove(topic.getId());
        topicsByName.remove(topic.getTopicName());
    }

    @Override
    public void deleteById(String id) {
        Topic topic = topics.get(id);
        if (topic != null) {
            topicsByName.remove(topic.getTopicName());
        }
        topics.remove(id);
    }

    @Override
    public boolean existsById(String id) {
        return topics.containsKey(id);
    }
}
