package org.project.observer;

import org.project.model.Expense;

public interface IObserver {
    void update(Expense expense);
}
