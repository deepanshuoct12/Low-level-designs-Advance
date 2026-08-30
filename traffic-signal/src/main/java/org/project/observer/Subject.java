package org.project.observer;

import org.project.enums.Color;
import org.project.enums.Direction;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Subject {

    private final List<IObserver> observers = new CopyOnWriteArrayList<>();

    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String intersectionId, Direction direction, Color color) {
        for (IObserver observer : observers) {
            observer.onSignalChange(intersectionId, direction, color);
        }
    }
}
