package org.dynamik.observer;

import org.dynamik.model.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
     List<IObserver> observers = new ArrayList<>();

     default void addObserver(IObserver observer) {
        observers.add(observer);
    }

     default void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

     default void notifyObservers(AbstractEntity entity) {
        for (IObserver observer : observers) {
            observer.update(entity);
        }
    }

     default void clearObservers() {
        observers.clear();
    }
}
