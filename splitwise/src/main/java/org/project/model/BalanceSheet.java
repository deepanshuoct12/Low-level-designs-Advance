package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class BalanceSheet extends BaseEntity {
    private String userId;
    private Map<String, Double> balances;
}
