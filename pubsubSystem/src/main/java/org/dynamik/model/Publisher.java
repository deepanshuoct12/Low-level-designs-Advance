package org.dynamik.model;

import lombok.Data;

@Data
public class Publisher {
    private String name;
    private String id;
    private Long updatedAt;
    private Long createdAt;
}
