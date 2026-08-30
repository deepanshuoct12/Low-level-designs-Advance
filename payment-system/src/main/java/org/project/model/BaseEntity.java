package org.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
public abstract class BaseEntity {
    private String id;
    private long ca;
    private long ua;
}
