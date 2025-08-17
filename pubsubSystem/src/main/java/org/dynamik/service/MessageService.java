package org.dynamik.service;

import org.dynamik.dao.MessageDao;
import org.dynamik.model.Message;

import java.util.List;

public class MessageService {
    private final MessageDao messageDao;

    public MessageService() {
        this.messageDao = new MessageDao();
    }

    // Create
    public Message save(Message message) {
        return messageDao.save(message);
    }

    // Read
    public Message findById(String id) {
        return messageDao.findById(id);
    }

    public List<Message> findByTopicId(String topicId) {
        return messageDao.findByTopicId(topicId);
    }

    public List<Message> findByPublisherId(String publisherId) {
        return messageDao.findByPublisherId(publisherId);
    }

    // Update
    public Message update(Message message) {
        return messageDao.save(message);
    }

    // Delete
    public void delete(Message message) {
        messageDao.delete(message);
    }

    public void deleteById(String id) {
        messageDao.deleteById(id);
    }

    // Exists
    public boolean existsById(String id) {
        return messageDao.existsById(id);
    }
}
