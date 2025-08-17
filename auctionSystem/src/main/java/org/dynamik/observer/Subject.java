package org.dynamik.observer;

import org.dynamik.model.Bid;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<IObserver> observers = new ArrayList<>();

    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Bid bid) {
        for (IObserver observer : observers) {
            observer.update(bid);
        }
    }
}
