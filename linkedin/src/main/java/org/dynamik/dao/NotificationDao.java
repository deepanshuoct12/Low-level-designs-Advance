package org.dynamik.dao;

import org.dynamik.enums.NotificationType;
import org.dynamik.model.Notification;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Notification data access object using an in-memory HashMap.
 * This class provides CRUD operations for Notification entities.
 */
public class NotificationDao implements IBaseDao<Notification, String> {
    private static final Map<String, Notification> notifications = new HashMap<>();

    @Override
    public Notification save(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("Notification cannot be null");
        }
        if (notification.getId() == null || notification.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Notification ID cannot be null or empty");
        }
        notifications.put(notification.getId(), notification);
        return notification;
    }

    @Override
    public List<Notification> findAll() {
        return new ArrayList<>(notifications.values());
    }

    @Override
    public void delete(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Notification ID cannot be null or empty");
        }
        notifications.remove(id);
    }

    @Override
    public Notification findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Notification ID cannot be null or empty");
        }
        return notifications.get(id);
    }

    /**
     * Find all notifications for a specific user
     * @param userId the ID of the user
     * @return list of notifications for the specified user, ordered by creation time (newest first)
     */
    public List<Notification> findByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        return notifications.values().stream()
                .filter(notification -> userId.equals(notification.getUserId()))
                .sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Find all unread notifications for a specific user
     * @param userId the ID of the user
     * @return list of unread notifications for the user
     */
//    public List<Notification> findUnreadByUserId(String userId) {
//        if (userId == null || userId.trim().isEmpty()) {
//            throw new IllegalArgumentException("User ID cannot be null or empty");
//        }
//        return notifications.values().stream()
//                .filter(notification ->
//                    userId.equals(notification.getUser_id()) &&
//                    !notification.isRead()
//                )
//                .sorted(Comparator.comparing(Notification::getCreated_at).reversed())
//                .collect(Collectors.toList());
//    }

    /**
     * Find notifications by type for a specific user
     * @param userId the ID of the user
     * @param type the type of notification
     * @return list of matching notifications
     */
    public List<Notification> findByUserIdAndType(String userId, NotificationType type) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("Notification type cannot be null");
        }
        return notifications.values().stream()
                .filter(notification -> 
                    userId.equals(notification.getUserId()) &&
                    type.equals(notification.getType())
                )
                .sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Mark all notifications as read for a specific user
     * @param userId the ID of the user
     * @return number of notifications marked as read
     */
//    public int markAllAsRead(String userId) {
//        if (userId == null || userId.trim().isEmpty()) {
//            throw new IllegalArgumentException("User ID cannot be null or empty");
//        }
//        int count = 0;
//        for (Notification notification : notifications.values()) {
//            if (userId.equals(notification.getUser_id()) && !notification.isRead()) {
//                notification.setRead(true);
//                count++;
//            }
//        }
//        return count;
//    }
}
