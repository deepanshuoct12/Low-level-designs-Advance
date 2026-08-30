package org.project.observer;

import org.project.model.Answer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<IObserver> observers = new ArrayList<>();

    public void add(IObserver observer) {
        observers.add(observer);
    }

    public void remove(IObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Answer answer) {
        for (IObserver observer : observers) {
            observer.update(answer);
        }
    }
}
