package org.project.service;

import org.project.model.Expense;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ExpenseService {
    private static final ConcurrentHashMap<String, Expense> EXPENSES = new ConcurrentHashMap<>();

    public Expense create(Expense expense) {
        if (expense.getId() == null) {
            expense.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        expense.setCreatedAt(now);
        expense.setUpdatedAt(now);
        EXPENSES.put(expense.getId(), expense);
        return expense;
    }

    public Expense getById(String id) {
        return EXPENSES.get(id);
    }

    public List<Expense> getAll() {
        return new ArrayList<>(EXPENSES.values());
    }

    public Expense update(Expense expense) {
        if (!EXPENSES.containsKey(expense.getId())) {
            return null;
        }
        expense.setUpdatedAt(System.currentTimeMillis());
        EXPENSES.put(expense.getId(), expense);
        return expense;
    }

    public void delete(String id) {
        EXPENSES.remove(id);
    }
}
