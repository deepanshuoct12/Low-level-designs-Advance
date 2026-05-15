package org.dynamik.model;

import lombok.Data;
import org.dynamik.enums.OrderStatus;

@Data
public class Order {
    private String id;
    private String itemId;
    private Integer quantity;
    private String userId;
    private OrderStatus status;
}
