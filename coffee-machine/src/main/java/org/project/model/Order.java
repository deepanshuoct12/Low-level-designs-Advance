package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Order extends BaseEntity {
    private String id;
    private String userId;
    private List<String> coffeeIds;
    private double totalPrice;
}
