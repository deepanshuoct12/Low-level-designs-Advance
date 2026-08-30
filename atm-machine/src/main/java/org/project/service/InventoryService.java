package org.project.service;

import org.project.model.Inventory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InventoryService {
    private static final Map<Long, Inventory> inventoryStore = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Inventory create(Inventory inventory) {
        inventory.setId(idCounter++);
        inventoryStore.put(inventory.getId(), inventory);
        return inventory;
    }

    public Inventory getById(Long id) {
        return inventoryStore.get(id);
    }

    public List<Inventory> getAll() {
        return inventoryStore.values().stream().collect(Collectors.toList());
    }

    public Inventory update(Long id, Inventory inventory) {
        if (inventoryStore.containsKey(id)) {
            inventory.setId(id);
            inventoryStore.put(id, inventory);
            return inventory;
        }
        return null;
    }

    public boolean delete(Long id) {
        return inventoryStore.remove(id) != null;
    }

    public Inventory getByATMId(Long atmId) {
        return inventoryStore.values().stream().filter(inv -> inv.getAtmId().equals(atmId)).findFirst().orElse(null);
    }
}
