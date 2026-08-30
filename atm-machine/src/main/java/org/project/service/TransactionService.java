package org.project.service;

import org.project.model.Transaction;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TransactionService {
    private static final Map<Long, Transaction> transactionStore = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Transaction create(Transaction transaction) {
        transaction.setId(idCounter++);
        transactionStore.put(transaction.getId(), transaction);
        return transaction;
    }

    public Transaction getById(Long id) {
        return transactionStore.get(id);
    }

    public List<Transaction> getAll() {
        return transactionStore.values().stream().collect(Collectors.toList());
    }

    public Transaction update(Transaction transaction) {
        if (transactionStore.containsKey(transaction.getId())) {
            transaction.setId(transaction.getId());
            transactionStore.put(transaction.getId(), transaction);
            return transaction;
        }
        return null;
    }

    public boolean delete(Long id) {
        return transactionStore.remove(id) != null;
    }
}
