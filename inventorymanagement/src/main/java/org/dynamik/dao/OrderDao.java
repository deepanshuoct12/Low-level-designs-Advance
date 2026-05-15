package org.dynamik.dao;

import org.dynamik.model.Order;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OrderDao implements IBaseDao<Order, String>{
    private static ConcurrentHashMap<String, Order> orders = new ConcurrentHashMap<>();

    @Override
    public void update(Order order) {
        orders.put(order.getId(), order);
    }

    @Override
    public void delete(String id) {
       orders.remove(id);
    }

    @Override
    public void save(Order order) {
      orders.put(order.getId(), order);
    }

    @Override
    public Order get(String id) {
        return orders.get(id);
    }

    @Override
    public List<Order> getAll() {
        return orders.values().stream().toList();
    }
}
