package org.project.service;

import org.project.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionService {
    private static final Map<String, Transaction> STORE = new ConcurrentHashMap<>();

    public Transaction create(Transaction entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        entity.setCa(now);
        entity.setUa(now);
        STORE.put(entity.getId(), entity);
        return entity;
    }

    public Transaction getById(String id) {
        return STORE.get(id);
    }

    public List<Transaction> getAll() {
        return new ArrayList<>(STORE.values());
    }

    public Transaction update(String id, Transaction entity) {
        if (!STORE.containsKey(id)) {
            return null;
        }
        entity.setId(id);
        entity.setUa(System.currentTimeMillis());
        STORE.put(id, entity);
        return entity;
    }

    public boolean delete(String id) {
        return STORE.remove(id) != null;
    }
}
