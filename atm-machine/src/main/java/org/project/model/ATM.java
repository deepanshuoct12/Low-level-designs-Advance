package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ATM extends BaseEntity {
    private Long id;
    private String location;
    private String bankId;
}
