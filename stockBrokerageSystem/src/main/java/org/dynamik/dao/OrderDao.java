package org.dynamik.dao;

import org.dynamik.enums.OrderState;
import org.dynamik.model.Order;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class OrderDao implements IBaseDao<Order, String> {
    private final static ConcurrentHashMap<String, Order> orders = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
       return orders.put(order.getId(), order);
    }

    @Override
    public Order getById(String s) {
     return orders.get(s);
    }

    @Override
    public void update(Order order) {
      orders.put(order.getId(), order);
    }

    @Override
    public void delete(String id) {
    orders.remove(id);
    }

    @Override
    public List<Order> getAll() {
        return orders.values().stream().toList();
    }

    public List<Order> findByUserId(String userId) {
        return orders.values().stream().filter(order -> order.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public List<Order> findByStockId(String stockId) {
        return orders.values().stream().filter(order -> order.getStockId().equals(stockId)).collect(Collectors.toList());
    }

    public boolean updateOrderState(String orderId, OrderState state) {
        return orders.computeIfPresent(orderId, (id, order) -> {
            order.setOrderState(state);
            return order;
        }) != null;
    }
}
