package org.dynamik.observer;

import org.dynamik.model.Notification;

import java.util.ArrayList;
import java.util.List;

public  abstract class NotificationSubject {
    private List<IObserver> observers = new ArrayList<>();

    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Notification notification) {
        for (IObserver observer : observers) {
            observer.update(notification);
        }
    }

    public void clearObservers() {
        observers.clear();
    }

}
