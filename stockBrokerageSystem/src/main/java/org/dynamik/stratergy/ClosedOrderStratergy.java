package org.dynamik.stratergy;

import org.dynamik.enums.OrderState;
import org.dynamik.model.Order;
import org.dynamik.service.OrderService;

import java.util.List;

public class ClosedOrderStratergy implements IOrderStratergy {
    private OrderService orderService;
    public ClosedOrderStratergy() {
        orderService = new OrderService();
    }

    @Override
    public boolean isApplicable(OrderState state) {
        return state == OrderState.CLOSED;
    }

    @Override
    public List<Order> getOrders(String userId) {
        return orderService.findByUserIdAndState(userId, OrderState.CLOSED);
    }
}
