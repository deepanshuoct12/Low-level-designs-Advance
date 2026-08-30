package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.project.enums.SplitType;

@Data
@EqualsAndHashCode(callSuper = true)
public class Split extends BaseEntity {
    private String expenseId;
    private String userId;
    private double amount;
    private SplitType splitType;
}
