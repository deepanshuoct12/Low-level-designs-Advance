package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseEntity {
    private Long id;
    private String accountNumber;
    private Long userId;
    private Long bankId;
    private BigDecimal balance;
}
