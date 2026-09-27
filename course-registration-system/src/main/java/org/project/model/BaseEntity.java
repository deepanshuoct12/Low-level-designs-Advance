package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public abstract class BaseEntity {
    private String id;
    private Long ua;
    private Long ca;
}
