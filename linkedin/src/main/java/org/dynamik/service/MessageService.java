package org.dynamik.service;

import org.dynamik.dao.MessageDao;
import org.dynamik.model.Message;

import java.util.List;
import java.util.UUID;

public class MessageService {
    private final MessageDao messageDao;

    public MessageService() {
        this.messageDao = new MessageDao();
    }

    public Message createMessage(Message message) {
        if (message.getId() == null || message.getId().trim().isEmpty()) {
            message.setId(UUID.randomUUID().toString());
        }

        return messageDao.save(message);
    }

    public List<Message> getConversation(String userId1, String userId2) {
        return messageDao.findMessagesBetweenUsers(userId1, userId2);
    }

    public List<Message> getSentMessages(String userId) {
        return messageDao.findBySenderId(userId);
    }

    public List<Message> getReceivedMessages(String userId) {
        return messageDao.findByReceiverId(userId);
    }

    public void deleteMessage(String messageId) {
        messageDao.delete(messageId);
    }
}
