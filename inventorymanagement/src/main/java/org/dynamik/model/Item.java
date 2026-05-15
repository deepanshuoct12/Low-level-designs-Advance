package org.dynamik.model;


import lombok.Data;
import org.dynamik.enums.Category;

@Data
public class Item {
    private String id;
    private String name;
    private String description;
    private double price;
    private Category category;
}
