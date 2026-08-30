package org.project.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseEntity {
    private String id;
    private long createdAt;
    private long updatedAt;
}
