package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.project.enums.Denomination;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cash extends BaseEntity {
    private Long id;
    private Denomination denomination;
    private Integer quantity;
    private Integer totalValue;
    private Long inventoryId;
}
