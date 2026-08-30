package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bank extends BaseEntity {
    private Long id;
    private String name;
    private String code;
    private String address;
}
