package org.project.stratergy;

import org.project.dto.FilterRequest;
import org.project.model.Coffee;
import org.project.service.CoffeeService;

import java.util.List;

public class CoffeeTypeStratergy implements CoffeeFilterStratergy {
    private CoffeeService coffeeService;

    public CoffeeTypeStratergy(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @Override
    public List<Coffee> filter(FilterRequest filterRequest) {
        return coffeeService.filterByType(filterRequest.getCoffeeType());
    }
}
