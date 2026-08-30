package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Config extends BaseEntity {
    private String key;
    private String description;
    private String environMentId;
    private String serviceId;
    private boolean isActive;
}
