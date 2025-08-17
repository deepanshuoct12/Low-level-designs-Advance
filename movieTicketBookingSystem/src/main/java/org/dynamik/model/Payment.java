package org.dynamik.model;


import lombok.Builder;
import lombok.Data;
import org.dynamik.enums.PaymentStatus;

@Data
@Builder
public class Payment {
    private String id;
    private String bookingId;
    private Double amount;
    private PaymentStatus status;
}
