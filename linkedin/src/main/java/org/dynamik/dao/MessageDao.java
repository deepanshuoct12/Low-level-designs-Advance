package org.dynamik.dao;

import org.dynamik.model.Message;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Message data access object using an in-memory HashMap.
 * This class provides CRUD operations for Message entities.
 */
public class MessageDao implements IBaseDao<Message, String> {
    private static final Map<String, Message> messages = new HashMap<>();

    @Override
    public Message save(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        if (message.getId() == null || message.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Message ID cannot be null or empty");
        }
        messages.put(message.getId(), message);
        return message;
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(messages.values());
    }

    @Override
    public void delete(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Message ID cannot be null or empty");
        }
        messages.remove(id);
    }

    @Override
    public Message findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Message ID cannot be null or empty");
        }
        return messages.get(id);
    }

    /**
     * Find all messages between two users
     * @param userId1 the ID of the first user
     * @param userId2 the ID of the second user
     * @return list of messages between the two users, ordered by creation time
     */
    public List<Message> findMessagesBetweenUsers(String userId1, String userId2) {
        if (userId1 == null || userId1.trim().isEmpty() || userId2 == null || userId2.trim().isEmpty()) {
            throw new IllegalArgumentException("User IDs cannot be null or empty");
        }
        return messages.values().stream()
                .filter(message -> 
                    (userId1.equals(message.getSenderId()) && userId2.equals(message.getReceiverId())) ||
                    (userId2.equals(message.getSenderId()) && userId1.equals(message.getReceiverId()))
                )
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .collect(Collectors.toList());
    }

    /**
     * Find all messages sent by a specific user
     * @param senderId the ID of the sender
     * @return list of messages sent by the user
     */
    public List<Message> findBySenderId(String senderId) {
        if (senderId == null || senderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender ID cannot be null or empty");
        }
        return messages.values().stream()
                .filter(message -> senderId.equals(message.getSenderId()))
                .sorted(Comparator.comparing(Message::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Find all messages received by a specific user
     * @param receiverId the ID of the receiver
     * @return list of messages received by the user
     */
    public List<Message> findByReceiverId(String receiverId) {
        if (receiverId == null || receiverId.trim().isEmpty()) {
            throw new IllegalArgumentException("Receiver ID cannot be null or empty");
        }
        return messages.values().stream()
                .filter(message -> receiverId.equals(message.getReceiverId()))
                .sorted(Comparator.comparing(Message::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Find all unread messages for a specific user
     * @param userId the ID of the user
     * @return list of unread messages for the user
     */
//    public List<Message> findUnreadMessages(String userId) {
//        if (userId == null || userId.trim().isEmpty()) {
//            throw new IllegalArgumentException("User ID cannot be null or empty");
//        }
//        return messages.values().stream()
//                .filter(message ->
//                    userId.equals(message.getReceiver_id()) &&
//                    !message.isRead()
//                )
//                .sorted(Comparator.comparing(Message::getCreated_at))
//                .collect(Collectors.toList());
//    }
}
