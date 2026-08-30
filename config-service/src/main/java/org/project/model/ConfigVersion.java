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
public class ConfigVersion extends BaseEntity {
    private String configId;
    private String value;
    private Integer version;
    private String changedBy;
}
