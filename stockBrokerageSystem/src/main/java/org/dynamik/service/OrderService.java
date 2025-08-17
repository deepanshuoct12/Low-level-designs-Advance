package org.dynamik.service;


import org.dynamik.dao.OrderDao;
import org.dynamik.model.Order;
import org.dynamik.enums.OrderState;

import java.util.List;
import java.util.stream.Collectors;


public class OrderService {
    
    private final OrderDao orderDao ;

    public OrderService() {
        this.orderDao = new OrderDao();
    }

    public Order save(Order order) {
        return orderDao.save(order);
    }

    public Order findById(String id) {
        return orderDao.getById(id);
    }

    public List<Order> findAll() {
        return orderDao.getAll();
    }

    public void deleteById(String id) {
        orderDao.delete(id);
    }

    public Order update(Order order) {
        return orderDao.save(order);
    }
    
    public List<Order> findByUserId(String userId) {
        return orderDao.findByUserId(userId);
    }
    
    public List<Order> findByStockId(String stockId) {
        return orderDao.findByStockId(stockId);
    }
    
    public boolean updateOrderState(String orderId, OrderState state) {
        return orderDao.updateOrderState(orderId, state);
    }

    public List<Order> findByUserIdAndState(String userId, OrderState orderState) {
        return orderDao.findByUserId(userId).stream()
                .filter(order -> order.getOrderState().equals(orderState))
                .collect(Collectors.toList());
    }
}
