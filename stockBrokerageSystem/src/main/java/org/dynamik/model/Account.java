package org.dynamik.model;

import lombok.Data;

@Data
public class Account extends AbstractEntity {
    private String userId;
    private String email;
    private Long balance = 0L;
}
