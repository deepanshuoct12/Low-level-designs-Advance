package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.project.enums.PaymentStatus;

@Data
@AllArgsConstructor
public class PaymentIntent extends BaseEntity {
    private String id;
    private String orderId;
    private double amount;
    private PaymentStatus status;
}
