package org.dynamik.service;

import org.dynamik.dao.NotificationDao;
import org.dynamik.enums.NotificationType;
import org.dynamik.model.Notification;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class NotificationService {
    private final NotificationDao notificationDao;

    public NotificationService() {
        this.notificationDao = new NotificationDao();
    }

    public Notification createNotification(Notification notification) {
        return notificationDao.save(notification);
    }

    public Notification createNotification(String userId, String content, NotificationType type) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setContent(content);
        notification.setType(type);
        notification.setId(UUID.randomUUID().toString());
        return notificationDao.save(notification);
    }

    public List<Notification> getUserNotifications(String userId) {
        return notificationDao.findByUserId(userId);
    }

    public List<Notification> getNotificationsByType(String userId, NotificationType type) {
        return notificationDao.findByUserIdAndType(userId, type);
    }

//    public void markAsRead(String notificationId) {
//        Notification notification = notificationDao.findById(notificationId);
//        if (notification != null) {
//            notification.setRead(true);
//            notificationDao.save(notification);
//        }
//    }

    public void deleteNotification(String notificationId) {
        notificationDao.delete(notificationId);
    }
}
