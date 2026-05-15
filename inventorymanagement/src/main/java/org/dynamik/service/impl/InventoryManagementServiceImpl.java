package org.dynamik.service.impl;

import org.dynamik.enums.Category;
import org.dynamik.enums.OrderStatus;
import org.dynamik.model.Inventory;
import org.dynamik.model.Item;
import org.dynamik.model.Order;
import org.dynamik.service.InventoryManagementService;
import org.dynamik.service.InventoryService;
import org.dynamik.service.ItemService;
import org.dynamik.service.OrderService;
import org.dynamik.stratergy.ClothFilterStratergy;
import org.dynamik.stratergy.ElectronicsItemFilterStratergy;
import org.dynamik.stratergy.GroceryStratergy;
import org.dynamik.stratergy.IFilterStratergy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryManagementServiceImpl implements InventoryManagementService {
    private static InventoryManagementServiceImpl instance;

    private ItemService itemService = new ItemService();
    private InventoryService inventoryService = new InventoryService();
    private OrderService orderService = new OrderService();

    private List<IFilterStratergy> filterStrategies = new ArrayList<>();

    private InventoryManagementServiceImpl() {
        filterStrategies.add(new ClothFilterStratergy());
        filterStrategies.add(new ElectronicsItemFilterStratergy());
        filterStrategies.add(new GroceryStratergy());
    }

    public static synchronized InventoryManagementServiceImpl getInstance() {
        if (instance == null) {
            instance = new InventoryManagementServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Item> getItemsByCategory(Category category) {
        for (IFilterStratergy strategy : filterStrategies) {
            if (strategy.isApplicable(category)) {
                return strategy.getItems(category);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Integer getStock(String itemId) {
        List<Inventory> inventories = inventoryService.getAllInventories();
        for (Inventory inventory : inventories) {
            if (inventory.getItemId().equals(itemId)) {
                return inventory.getStock();
            }
        }
        return 0;
    }

    @Override
    public Order getOrder(String orderId) {
        return orderService.getOrder(orderId);
    }

    @Override
    public Order placeOrder(String itemId, Integer quantity, String userId) {
        // Check if item is available
        if (!inventoryService.isItemAvailable(itemId, quantity)) {
            throw new IllegalArgumentException("Item not available or insufficient stock");
        }

        // Find inventory ID for the item
        List<Inventory> inventories = inventoryService.getAllInventories();
        String inventoryId = null;
        for (Inventory inv : inventories) {
            if (inv.getItemId().equals(itemId)) {
                inventoryId = inv.getId();
                break;
            }
        }

        if (inventoryId == null) {
            throw new IllegalArgumentException("Inventory not found for item");
        }

        // Reserve the quantity
        inventoryService.reserveQuantity(inventoryId, quantity);

        // Create and save the order
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setItemId(itemId);
        order.setQuantity(quantity);
        order.setUserId(userId);
        order.setStatus(OrderStatus.CREATED);

        orderService.saveOrder(order);
        return order;
    }

    @Override
    public void cancelOrder(String orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new IllegalArgumentException("Cannot cancel confirmed order");
        }

        // Find inventory ID for the item
        List<Inventory> inventories = inventoryService.getAllInventories();
        String inventoryId = null;
        for (Inventory inv : inventories) {
            if (inv.getItemId().equals(order.getItemId())) {
                inventoryId = inv.getId();
                break;
            }
        }

        if (inventoryId != null) {
            // Release the reserved quantity
            inventoryService.releaseQuantity(inventoryId, order.getQuantity());
        }

        // Update order status
        order.setStatus(OrderStatus.CANCELLED);
        orderService.updateOrder(order);
    }

    @Override
    public void confirmOrder(String orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalArgumentException("Only created orders can be confirmed");
        }

        // Find inventory ID for the item
        List<Inventory> inventories = inventoryService.getAllInventories();
        String inventoryId = null;
        for (Inventory inv : inventories) {
            if (inv.getItemId().equals(order.getItemId())) {
                inventoryId = inv.getId();
                break;
            }
        }

        if (inventoryId != null) {
            // Deduct the quantity (already reserved, now permanently deduct)
            inventoryService.deductQuantity(inventoryId, order.getQuantity());
        }

        // Update order status
        order.setStatus(OrderStatus.CONFIRMED);
        orderService.updateOrder(order);
    }

    @Override
    public void increaseStock(String itemId, Integer qty) {
        List<Inventory> inventories = inventoryService.getAllInventories();
        for (Inventory inventory : inventories) {
            if (inventory.getItemId().equals(itemId)) {
                inventoryService.increaseStock(inventory.getId(), qty);
                return;
            }
        }
        throw new IllegalArgumentException("Inventory not found for item");
    }

    @Override
    public void decreaseStock(String itemId, Integer qty) {
        List<Inventory> inventories = inventoryService.getAllInventories();
        for (Inventory inventory : inventories) {
            if (inventory.getItemId().equals(itemId)) {
                inventoryService.decreaseStock(inventory.getId(), qty);
                return;
            }
        }
        throw new IllegalArgumentException("Inventory not found for item");
    }
}
