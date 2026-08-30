package org.project.observer;

import org.project.model.Expense;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<IObserver> observers = new ArrayList<>();

    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    public void removeObser(IObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Expense expense) {
        for (IObserver observer : observers) {
            observer.update(expense);
        }
    }
}
