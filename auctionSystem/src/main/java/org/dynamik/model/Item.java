package org.dynamik.model;

import lombok.Data;
import org.dynamik.enums.ItemCategory;

@Data
public class Item {
    private String id;
    private String name;
    private String description;
    private ItemCategory category;
    private String userId;
}
