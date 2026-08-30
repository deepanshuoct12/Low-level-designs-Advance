package org.project.service;

import org.project.enums.TransactionStatus;
import org.project.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionService {
    private static final double DELTA = 0.01;
    private static final ConcurrentHashMap<String, Transaction> TRANSACTIONS = new ConcurrentHashMap<>();

    public Transaction create(Transaction transaction) {
        if (transaction.getId() == null) {
            transaction.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        transaction.setCreatedAt(now);
        transaction.setUpdatedAt(now);
        TRANSACTIONS.put(transaction.getId(), transaction);
        return transaction;
    }

    public Transaction getById(String id) {
        return TRANSACTIONS.get(id);
    }

    public List<Transaction> getAll() {
        return new ArrayList<>(TRANSACTIONS.values());
    }

    public Transaction update(Transaction transaction) {
        if (!TRANSACTIONS.containsKey(transaction.getId())) {
            return null;
        }
        transaction.setUpdatedAt(System.currentTimeMillis());
        TRANSACTIONS.put(transaction.getId(), transaction);
        return transaction;
    }

    public void delete(String id) {
        TRANSACTIONS.remove(id);
    }
}
