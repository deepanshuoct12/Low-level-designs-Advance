package org.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
public abstract class BaseEntity {
    private LocalDateTime ca;
    private LocalDateTime ua;

    public BaseEntity() {
        this.ca = LocalDateTime.now();
        this.ua = LocalDateTime.now();
    }

    public void updateTimestamp() {
        this.ua = LocalDateTime.now();
    }
}
