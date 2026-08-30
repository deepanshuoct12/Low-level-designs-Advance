package org.project.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account extends BaseEntity {
    private String userId;
    private String merchantId;
    private BigDecimal balance;
    private String status;
}
