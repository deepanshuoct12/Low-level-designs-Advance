package org.dynamik.model;

import lombok.Data;

@Data
public class Inventory {
    private String id;
    private String itemId;
    private Integer stock;
    private Integer reservedQuantity;
}
