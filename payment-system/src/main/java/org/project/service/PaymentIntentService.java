package org.project.service;

import org.project.model.PaymentIntent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentIntentService {
    private static final Map<String, PaymentIntent> STORE = new ConcurrentHashMap<>();

    public PaymentIntent create(PaymentIntent entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        entity.setCa(now);
        entity.setUa(now);
        STORE.put(entity.getId(), entity);
        return entity;
    }

    public PaymentIntent getById(String id) {
        return STORE.get(id);
    }

    public List<PaymentIntent> getAll() {
        return new ArrayList<>(STORE.values());
    }

    public PaymentIntent update(String id, PaymentIntent entity) {
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
