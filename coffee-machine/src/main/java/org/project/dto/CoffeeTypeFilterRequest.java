package org.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.project.enums.CoffeeType;

@Data
@AllArgsConstructor
public class CoffeeTypeFilterRequest {
    private CoffeeType coffeeType;
}
