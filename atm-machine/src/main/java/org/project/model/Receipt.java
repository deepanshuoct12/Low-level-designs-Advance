package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class Receipt extends BaseEntity {
    private Long id;
    private Long transactionId;
    private String content;
    private LocalDateTime printedAt;
    private String status;
}
