package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course extends BaseEntity {
    private String name;
    private Long maxCapacity;
    private Long capacity;
    private String code;
}
