package org.project.observer;

public class UserObserver implements Observer {
    private String userId;

    public UserObserver(String userId) {
        this.userId = userId;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification for user " + userId + ": " + message);
    }
}
