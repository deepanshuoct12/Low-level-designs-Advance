package org.dynamik.model;

import lombok.Data;

@Data
public class AbstractEntity {
    private String id;
    private Long createdAt;
    private Long updatedAt;
}
