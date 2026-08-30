package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.project.enums.TransactionStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class Transaction extends BaseEntity {
    private String fromUserId;
    private String toUserId;
    private double amount;
    private TransactionStatus status;
}
