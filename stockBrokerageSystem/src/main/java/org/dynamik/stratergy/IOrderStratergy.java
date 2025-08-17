package org.dynamik.stratergy;

import org.dynamik.enums.OrderState;
import org.dynamik.model.Order;

import java.util.List;

public interface IOrderStratergy {
  boolean isApplicable(OrderState state);
  List<Order> getOrders(String userId);
}
