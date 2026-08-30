package org.project.model;

import lombok.Data;
import org.project.enums.PaymentIntentStatus;
import org.project.enums.TransactionType;

import java.math.BigDecimal;

@Data
public class PaymentIntent extends BaseEntity {
    private String userId;
    private String merchantId;
    private BigDecimal amount;
    private TransactionType paymentMethodType;
    private PaymentIntentStatus status;
}
