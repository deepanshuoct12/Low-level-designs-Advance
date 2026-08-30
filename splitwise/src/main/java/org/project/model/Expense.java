package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Expense extends BaseEntity {
    private String groupId;
    private String description;
    private double amount;
    private String paidBy;
    private String category;
}
