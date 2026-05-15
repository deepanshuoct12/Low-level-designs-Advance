package org.dynamik.demo;

import org.dynamik.enums.Category;
import org.dynamik.enums.OrderStatus;
import org.dynamik.model.Inventory;
import org.dynamik.model.Item;
import org.dynamik.model.Order;
import org.dynamik.service.InventoryManagementService;
import org.dynamik.service.InventoryService;
import org.dynamik.service.ItemService;
import org.dynamik.service.impl.InventoryManagementServiceImpl;

import java.util.List;
import java.util.UUID;

public class InventoryManagementDemo {

    public static void main(String[] args) {
        // Get singleton instance
        InventoryManagementService inventoryManagementService = InventoryManagementServiceImpl.getInstance();
        ItemService itemService = new ItemService();
        InventoryService inventoryService = new InventoryService();

        System.out.println("=== Inventory Management System Demo ===\n");

        // 1. Create and save items
        System.out.println("1. Creating items...");
        Item shirt = createItem("1", "Cotton Shirt", "Comfortable cotton shirt", 29.99, Category.CLOTHES);
        Item laptop = createItem("2", "Laptop", "High performance laptop", 999.99, Category.ELECTRONICS);
        Item apple = createItem("3", "Apple", "Fresh red apple", 1.99, Category.GROCERIES);

        itemService.addItem(shirt);
        itemService.addItem(laptop);
        itemService.addItem(apple);
        System.out.println("Items created successfully\n");

        // 2. Create inventory for items
        System.out.println("2. Creating inventory...");
        Inventory shirtInventory = createInventory("inv1", "1", 50);
        Inventory laptopInventory = createInventory("inv2", "2", 20);
        Inventory appleInventory = createInventory("inv3", "3", 100);

        inventoryService.saveInventory(shirtInventory);
        inventoryService.saveInventory(laptopInventory);
        inventoryService.saveInventory(appleInventory);
        System.out.println("Inventory created successfully\n");

        // 3. Get items by category using strategy pattern
        System.out.println("3. Getting items by category (Strategy Pattern)...");
        List<Item> clothes = inventoryManagementService.getItemsByCategory(Category.CLOTHES);
        System.out.println("Clothes: " + clothes);

        List<Item> electronics = inventoryManagementService.getItemsByCategory(Category.ELECTRONICS);
        System.out.println("Electronics: " + electronics);

        List<Item> groceries = inventoryManagementService.getItemsByCategory(Category.GROCERIES);
        System.out.println("Groceries: " + groceries + "\n");

        // 4. Get stock for items
        System.out.println("4. Getting stock levels...");
        System.out.println("Shirt stock: " + inventoryManagementService.getStock("1"));
        System.out.println("Laptop stock: " + inventoryManagementService.getStock("2"));
        System.out.println("Apple stock: " + inventoryManagementService.getStock("3") + "\n");

        // 5. Place an order (reserves quantity)
        System.out.println("5. Placing order for 5 shirts (reserves quantity)...");
        Order order1 = inventoryManagementService.placeOrder("1", 5, "user1");
        System.out.println("Order placed: " + order1.getId() + ", Status: " + order1.getStatus());
        System.out.println("Shirt stock after order: " + inventoryManagementService.getStock("1"));
        System.out.println("Shirt reserved quantity: " + getReservedQuantity(inventoryService, "inv1") + "\n");

        // 6. Place another order
        System.out.println("6. Placing order for 3 laptops (reserves quantity)...");
        Order order2 = inventoryManagementService.placeOrder("2", 3, "user2");
        System.out.println("Order placed: " + order2.getId() + ", Status: " + order2.getStatus());
        System.out.println("Laptop stock after order: " + inventoryManagementService.getStock("2"));
        System.out.println("Laptop reserved quantity: " + getReservedQuantity(inventoryService, "inv2") + "\n");

        // 7. Confirm the first order (deducts quantity)
        System.out.println("7. Confirming shirt order (deducts quantity)...");
        inventoryManagementService.confirmOrder(order1.getId());
        Order confirmedOrder = inventoryManagementService.getOrder(order1.getId());
        System.out.println("Order status: " + confirmedOrder.getStatus());
        System.out.println("Shirt stock after confirmation: " + inventoryManagementService.getStock("1"));
        System.out.println("Shirt reserved quantity: " + getReservedQuantity(inventoryService, "inv1") + "\n");

        // 8. Cancel the second order (releases quantity)
        System.out.println("8. Canceling laptop order (releases quantity)...");
        inventoryManagementService.cancelOrder(order2.getId());
        Order cancelledOrder = inventoryManagementService.getOrder(order2.getId());
        System.out.println("Order status: " + cancelledOrder.getStatus());
        System.out.println("Laptop stock after cancellation: " + inventoryManagementService.getStock("2"));
        System.out.println("Laptop reserved quantity: " + getReservedQuantity(inventoryService, "inv2") + "\n");

        // 9. Increase stock
        System.out.println("9. Increasing apple stock by 50...");
        inventoryManagementService.increaseStock("3", 50);
        System.out.println("Apple stock after increase: " + inventoryManagementService.getStock("3") + "\n");

        // 10. Decrease stock
        System.out.println("10. Decreasing apple stock by 20...");
        inventoryManagementService.decreaseStock("3", 20);
        System.out.println("Apple stock after decrease: " + inventoryManagementService.getStock("3") + "\n");

        System.out.println("=== Demo Complete ===");
    }

    private static Item createItem(String id, String name, String description, double price, Category category) {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);
        return item;
    }

    private static Inventory createInventory(String id, String itemId, Integer stock) {
        Inventory inventory = new Inventory();
        inventory.setId(id);
        inventory.setItemId(itemId);
        inventory.setStock(stock);
        inventory.setReservedQuantity(0);
        return inventory;
    }

    private static Integer getReservedQuantity(InventoryService inventoryService, String inventoryId) {
        Inventory inventory = inventoryService.getInventory(inventoryId);
        return inventory != null ? inventory.getReservedQuantity() : 0;
    }
}
