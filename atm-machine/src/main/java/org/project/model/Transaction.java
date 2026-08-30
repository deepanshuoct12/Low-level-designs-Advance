package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.project.enums.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class Transaction extends BaseEntity {
    private Long id;
    private String type;
    private BigDecimal amount;
    private TransactionStatus status;
    private LocalDateTime timestamp;
}
