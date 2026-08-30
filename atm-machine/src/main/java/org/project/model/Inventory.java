package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class Inventory extends BaseEntity {
    private Long id;
    private Long atmId;
}
