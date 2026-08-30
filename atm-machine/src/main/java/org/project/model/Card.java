package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class Card extends BaseEntity {
    private Long id;
    private String cardNumber;
    private String cvv;
    private LocalDate expiry;
    private Long userId;
    private String pin;
}
