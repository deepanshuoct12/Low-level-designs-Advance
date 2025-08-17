package org.dynamik.observer;

import org.dynamik.model.Notification;
import org.dynamik.model.User;

public class UserObserver implements IObserver {
    private User user;
    public UserObserver(User user) {
        this.user = user;
    }

    @Override
    public void update(Notification notification) {
        System.out.println("User " + notification.getUserId() + " has received a notification: " + notification.getContent());
    }
}
