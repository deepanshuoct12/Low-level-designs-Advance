package org.project.service;

import org.project.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AccountService {
    private static final Map<String, Account> STORE = new ConcurrentHashMap<>();

    public Account create(Account entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        entity.setCa(now);
        entity.setUa(now);
        STORE.put(entity.getId(), entity);
        return entity;
    }

    public Account getById(String id) {
        return STORE.get(id);
    }

    public Account getByUserId(String userId) {
        return STORE.values().stream()
                .filter(account -> userId != null && userId.equals(account.getUserId()))
                .findFirst()
                .orElse(null);
    }

    public Account getByMerchantId(String merchantId) {
        return STORE.values().stream()
                .filter(account -> merchantId != null && merchantId.equals(account.getMerchantId()))
                .findFirst()
                .orElse(null);
    }

    public List<Account> getAll() {
        return new ArrayList<>(STORE.values());
    }

    public Account update(String id, Account entity) {
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
