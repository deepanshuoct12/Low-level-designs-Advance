package org.dynamik.observer;


import org.dynamik.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Subject {
    List<IObserver> observers = new ArrayList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void init(List<IObserver> observers) {
        this.observers = observers;
    }

    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IObserver observer) {
        executorService.submit(() -> observers.remove(observer));
    }

    public void notifyObservers(Message message) {
        for (IObserver observer : observers) {
            executorService.submit(() -> observer.update(message));
        }
    }

    public void clearObservers() {
        observers.clear();
    }
}
