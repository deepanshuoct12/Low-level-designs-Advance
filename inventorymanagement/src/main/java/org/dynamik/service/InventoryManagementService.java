package org.dynamik.service;

import org.dynamik.enums.Category;
import org.dynamik.model.Item;
import org.dynamik.model.Order;

import java.util.List;

public interface InventoryManagementService {
    List<Item> getItemsByCategory(Category category);
    Integer getStock(String itemId);
    Order placeOrder(String itemId, Integer quantity, String userId);
    Order getOrder(String orderId);
    void cancelOrder(String orderId);
    void confirmOrder(String orderId);
    void increaseStock(String itemId, Integer qty);
    void decreaseStock(String itemId, Integer qty);
}
