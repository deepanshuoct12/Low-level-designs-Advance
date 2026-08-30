package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    private Long id;
    private Long createdAt;
    private Long updatedAt;
}
