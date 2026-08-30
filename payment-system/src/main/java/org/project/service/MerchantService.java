package org.project.service;

import org.project.model.Merchant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MerchantService {
    private static final Map<String, Merchant> STORE = new ConcurrentHashMap<>();

    public Merchant create(Merchant entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        entity.setCa(now);
        entity.setUa(now);
        STORE.put(entity.getId(), entity);
        return entity;
    }

    public Merchant getById(String id) {
        return STORE.get(id);
    }

    public List<Merchant> getAll() {
        return new ArrayList<>(STORE.values());
    }

    public Merchant update(String id, Merchant entity) {
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
