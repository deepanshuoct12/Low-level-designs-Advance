package org.project.model;

import lombok.Data;

@Data
public class BaseEntity {
    private String id;
    private long createdAt;
    private long updatedAt;
}
