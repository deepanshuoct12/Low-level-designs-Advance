package org.dynamik.service;

import org.dynamik.dao.TopicDao;
import org.dynamik.model.Topic;

import java.util.List;

public class TopicService {
    private final TopicDao topicDao;

    public TopicService() {
        this.topicDao = new TopicDao();
    }

    // Create
    public Topic save(Topic topic) {
        return topicDao.save(topic);
    }

    // Read
    public Topic findById(String id) {
        return topicDao.findById(id);
    }

    public Topic findByName(String name) {
        return topicDao.findByName(name);
    }

    // Update
    public Topic update(Topic topic) {
        return topicDao.save(topic);
    }

    // Delete
    public void delete(Topic topic) {
        topicDao.delete(topic);
    }

    public void deleteById(String id) {
        topicDao.deleteById(id);
    }

    // Exists
    public boolean existsById(String id) {
        return topicDao.existsById(id);
    }
}
