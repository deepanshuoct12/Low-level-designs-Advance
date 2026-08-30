package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.project.enums.CoffeeBeansType;

@Data
@AllArgsConstructor
public class Recipe extends BaseEntity {
    private String id;
    private String name;
    private double milkQty;
    private double waterQty;
    private CoffeeBeansType coffeeBeansType;
}
