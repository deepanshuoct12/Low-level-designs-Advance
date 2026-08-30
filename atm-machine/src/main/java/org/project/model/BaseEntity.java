package org.project.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public abstract class BaseEntity {
    private LocalDateTime ca;
    private LocalDateTime ua;

    public BaseEntity() {
        this.ca = LocalDateTime.now();
        this.ua = LocalDateTime.now();
    }
}
