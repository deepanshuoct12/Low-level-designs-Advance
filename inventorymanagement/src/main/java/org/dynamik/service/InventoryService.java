package org.dynamik.service;

import org.dynamik.dao.InventoryDao;
import org.dynamik.model.Inventory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class InventoryService {
    private InventoryDao inventoryDao = new InventoryDao();
    private final ReentrantLock lock = new ReentrantLock();

    public void saveInventory(Inventory inventory) {
        inventoryDao.save(inventory);
    }

    public Inventory getInventory(String id) {
        return inventoryDao.get(id);
    }

    public List<Inventory> getAllInventories() {
        return inventoryDao.getAll();
    }

    public void updateInventory(Inventory inventory) {
        inventoryDao.update(inventory);
    }

    public void deleteInventory(String id) {
        inventoryDao.delete(id);
    }

    // 5. Update inventory i.e increase stock (thread safe)
    public synchronized void increaseStock(String inventoryId, int quantity) {
        Inventory inventory = inventoryDao.get(inventoryId);
        if (inventory != null) {
            inventory.setStock(inventory.getStock() + quantity);
            inventoryDao.update(inventory);
        }
    }

    // 6. Reduce inventory i.e decrease stock (thread safe)
    public synchronized void decreaseStock(String inventoryId, int quantity) {
        Inventory inventory = inventoryDao.get(inventoryId);
        if (inventory != null && inventory.getStock() >= quantity) {
            inventory.setStock(inventory.getStock() - quantity);
            inventoryDao.update(inventory);
        } else {
            throw new IllegalArgumentException("Insufficient stock");
        }
    }

    // 7. Is item available in inventory
    public boolean isItemAvailable(String itemId, int requiredQuantity) {
        List<Inventory> inventories = getAllInventories();
        for (Inventory inventory : inventories) {
            int availableStock = inventory.getStock() - (inventory.getReservedQuantity() != null ? inventory.getReservedQuantity() : 0);
            if (inventory.getItemId().equals(itemId) && availableStock >= requiredQuantity) {
                return true;
            }
        }
        return false;
    }

    // 11. Reserve item qty
    public synchronized void reserveQuantity(String inventoryId, int quantity) {
        Inventory inventory = inventoryDao.get(inventoryId);
        if (inventory != null) {
            if (inventory.getStock() >= quantity) {
                inventory.setStock(inventory.getStock() - quantity);
                int currentReserved = inventory.getReservedQuantity() != null ? inventory.getReservedQuantity() : 0;
                inventory.setReservedQuantity(currentReserved + quantity);
                inventoryDao.update(inventory);
            } else {
                throw new IllegalArgumentException("Insufficient stock to reserve");
            }
        } else {
            throw new IllegalArgumentException("Inventory not found");
        }
    }

    // 12. Release qty
    public synchronized void releaseQuantity(String inventoryId, int quantity) {
        Inventory inventory = inventoryDao.get(inventoryId);
        if (inventory != null) {
            int currentReserved = inventory.getReservedQuantity() != null ? inventory.getReservedQuantity() : 0;
            if (currentReserved >= quantity) {
                inventory.setStock(inventory.getStock() + quantity);
                inventory.setReservedQuantity(currentReserved - quantity);
                inventoryDao.update(inventory);
            } else {
                throw new IllegalArgumentException("Insufficient reserved quantity to release");
            }
        } else {
            throw new IllegalArgumentException("Inventory not found");
        }
    }

    // 13. Deduct qty
    public synchronized void deductQuantity(String inventoryId, int quantity) {
        Inventory inventory = inventoryDao.get(inventoryId);
        if (inventory != null) {
            int currentReserved = inventory.getReservedQuantity() != null ? inventory.getReservedQuantity() : 0;
            if (currentReserved >= quantity) {
                inventory.setReservedQuantity(currentReserved - quantity);
                inventoryDao.update(inventory);
            } else {
                throw new IllegalArgumentException("Insufficient reserved quantity to deduct");
            }
        } else {
            throw new IllegalArgumentException("Inventory not found");
        }
    }
}
