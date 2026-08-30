package org.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.project.enums.CoffeeType;

@Data
@AllArgsConstructor
public class FilterRequest {
    private PriceRange priceRange;
    private CoffeeType coffeeType;

    @Data
    @AllArgsConstructor
    public static class PriceRange {
        private double min;
        private double max;
    }
}
