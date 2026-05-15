package org.dynamik.dao;

import org.dynamik.model.Inventory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryDao implements IBaseDao<Inventory, String>{
    private static ConcurrentHashMap<String, Inventory> inventories = new ConcurrentHashMap<>();

    @Override
    public void update(Inventory inventory) {
        inventories.put(inventory.getId(), inventory);
    }

    @Override
    public void delete(String id) {
       inventories.remove(id);
    }

    @Override
    public void save(Inventory inventory) {
      inventories.put(inventory.getId(), inventory);
    }

    @Override
    public Inventory get(String id) {
        return inventories.get(id);
    }

    @Override
    public List<Inventory> getAll() {
        return inventories.values().stream().toList();
    }
}
