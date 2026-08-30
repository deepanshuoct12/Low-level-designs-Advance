package org.project.model;

import lombok.Data;

@Data
public abstract class BaseEntity {
    private Long id;
    private Long createdAt;
    private Long updatedAt;

    public void onCreate() {
        createdAt = System.currentTimeMillis();
        updatedAt = System.currentTimeMillis();
    }

    public void onUpdate() {
        updatedAt = System.currentTimeMillis();
    }
}
