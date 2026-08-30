package org.project.service;

import org.project.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OrderService {
    private static ConcurrentHashMap<String, Order> orders = new ConcurrentHashMap<>();

    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }

    public Order createOrder(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    public List<Order> getAll() {
        return new ArrayList<>(orders.values());
    }

    public void update(Order order) {
        orders.put(order.getId(), order);
    }

    public void delete(String orderId) {
        orders.remove(orderId);
    }
}
