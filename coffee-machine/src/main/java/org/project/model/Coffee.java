package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.project.enums.CoffeeType;

@Data
@AllArgsConstructor
public class Coffee extends BaseEntity {
    private String id;
    private String name;
    private String menuId;
    private double price;
    private String recipeId;
    private CoffeeType coffeeType;
    private boolean available;
}
