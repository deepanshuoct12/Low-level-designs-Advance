package org.dynamik.model;

import lombok.Data;

@Data
public class Company extends AbstractEntity {
    private String name;
    private String description;
    private String pictureUrl;
}
