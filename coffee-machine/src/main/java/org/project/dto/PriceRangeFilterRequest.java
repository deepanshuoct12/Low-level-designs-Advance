package org.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceRangeFilterRequest {
    private double minPrice;
    private double maxPrice;
}
