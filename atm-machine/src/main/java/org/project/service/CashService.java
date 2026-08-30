package org.project.service;

import org.project.model.Cash;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CashService {
    private static final Map<Long, Cash> cashStore = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Cash create(Cash cash) {
        cash.setId(idCounter++);
        cashStore.put(cash.getId(), cash);
        return cash;
    }

    public Cash getById(Long id) {
        return cashStore.get(id);
    }

    public List<Cash> getAll() {
        return cashStore.values().stream().collect(Collectors.toList());
    }

    public Cash update(Long id, Cash cash) {
        if (cashStore.containsKey(id)) {
            cash.setId(id);
            cashStore.put(id, cash);
            return cash;
        }
        return null;
    }

    public boolean delete(Long id) {
        return cashStore.remove(id) != null;
    }

    public List<Cash> getCashByInventory(Long inventoryId) {
        return cashStore.values().stream()
                .filter(cash -> cash.getInventoryId().equals(inventoryId))
                .collect(Collectors.toList());
    }
}
