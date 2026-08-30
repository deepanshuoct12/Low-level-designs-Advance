package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client extends BaseEntity {
    private String clientId;
    private String clientName;
    private String clientType;
    private String description;
    private boolean isActive;
}
