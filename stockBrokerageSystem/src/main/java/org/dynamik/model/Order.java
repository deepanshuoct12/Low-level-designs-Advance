package org.dynamik.model;

import lombok.Data;
import org.dynamik.enums.OrderState;
import org.dynamik.enums.OrderType;

@Data
public class Order extends AbstractEntity {
    private String stockId;
    private String userId;
    private Long quantity;
    private OrderType orderType;
    private OrderState orderState;
}
