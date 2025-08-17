package org.dynamik.model;

import org.dynamik.enums.NotificationType;

import lombok.Data;
import org.dynamik.observer.NotificationSubject;

@Data
public class Notification extends AbstractEntity {
    private String id;
    private String userId;   // The user who received the notification
    private String content;
    private NotificationType type;
}
