package org.project.model;

import lombok.Data;
import org.project.enums.TransactionStatus;
import org.project.enums.TransactionType;

import java.math.BigDecimal;

@Data
public class Transaction extends BaseEntity {
    private String paymentIntentId;
    private String senderAccountId;
    private String receiverAccountId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionStatus status;
}
