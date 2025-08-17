package org.dynamik.model;

import lombok.Data;
import org.dynamik.enums.PaymentState;
import org.dynamik.observer.Subject;

@Data
public class Payment extends AbstractEntity implements Subject {
    private String orderId;
    private Long amount;
    private PaymentState paymentState;
}
