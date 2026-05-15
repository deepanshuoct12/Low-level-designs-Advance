package org.dynamik.service;

import org.dynamik.dao.OrderDao;
import org.dynamik.enums.OrderStatus;
import org.dynamik.model.Inventory;
import org.dynamik.model.Order;

import java.util.List;
import java.util.UUID;

public class OrderService {
    private OrderDao orderDao = new OrderDao();
    private InventoryService inventoryService = new InventoryService();

    public void saveOrder(Order order) {
        orderDao.save(order);
    }

    public Order getOrder(String id) {
        return orderDao.get(id);
    }

    public List<Order> getAllOrders() {
        return orderDao.getAll();
    }

    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    public void deleteOrder(String id) {
        orderDao.delete(id);
    }

    // 8. Place order
    public Order placeOrder(String itemId, Integer quantity, String userId) {
        // Check if item is available
        if (!inventoryService.isItemAvailable(itemId, quantity)) {
            throw new IllegalArgumentException("Item not available or insufficient stock");
        }

        // Reserve the quantity
        List<Inventory> inventories = inventoryService.getAllInventories();
        String inventoryId = null;
        for (Inventory inv : inventories) {
            if (inv.getItemId().equals(itemId)) {
                inventoryId = inv.getId();
                break;
            }
        }

        if (inventoryId != null) {
            inventoryService.reserveQuantity(inventoryId, quantity);
        }

        // Create and save the order
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setItemId(itemId);
        order.setQuantity(quantity);
        order.setUserId(userId);
        order.setStatus(OrderStatus.CREATED);

        orderDao.save(order);
        return order;
    }

    // 9. Cancel order
    public void cancelOrder(String orderId) {
        Order order = orderDao.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new IllegalArgumentException("Cannot cancel confirmed order");
        }

        // Release the reserved quantity
        List<Inventory> inventories = inventoryService.getAllInventories();
        String inventoryId = null;
        for (Inventory inv : inventories) {
            if (inv.getItemId().equals(order.getItemId())) {
                inventoryId = inv.getId();
                break;
            }
        }

        if (inventoryId != null) {
            inventoryService.releaseQuantity(inventoryId, order.getQuantity());
        }

        // Update order status
        order.setStatus(OrderStatus.CANCELLED);
        orderDao.update(order);
    }

    // 10. Confirm order
    public void confirmOrder(String orderId) {
        Order order = orderDao.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalArgumentException("Only created orders can be confirmed");
        }

        // Deduct the quantity (already reserved, now permanently deduct)
        List<Inventory> inventories = inventoryService.getAllInventories();
        String inventoryId = null;
        for (Inventory inv : inventories) {
            if (inv.getItemId().equals(order.getItemId())) {
                inventoryId = inv.getId();
                break;
            }
        }

        if (inventoryId != null) {
            inventoryService.deductQuantity(inventoryId, order.getQuantity());
        }

        // Update order status
        order.setStatus(OrderStatus.CONFIRMED);
        orderDao.update(order);
    }
}
