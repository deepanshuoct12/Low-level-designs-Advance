package org.project.stratergy;

import org.project.dto.FilterRequest;
import org.project.model.Coffee;
import org.project.service.CoffeeService;

import java.util.List;

public class CoffeePriceStratergy implements CoffeeFilterStratergy {
    private CoffeeService coffeeService;

    public CoffeePriceStratergy(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @Override
    public List<Coffee> filter(FilterRequest filterRequest) {
        return coffeeService.filterByPriceRange(
            filterRequest.getPriceRange().getMin(),
            filterRequest.getPriceRange().getMax()
        );
    }
}
