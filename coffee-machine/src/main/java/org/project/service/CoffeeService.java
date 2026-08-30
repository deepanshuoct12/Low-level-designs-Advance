package org.project.service;

import org.project.dto.FilterRequest;
import org.project.enums.CoffeeType;
import org.project.exceptions.InvalidPriceRangeException;
import org.project.model.Coffee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CoffeeService {
    private static ConcurrentHashMap<String, Coffee> coffees = new ConcurrentHashMap<>();

    public Coffee getCoffee(String coffeeId) {
        return coffees.get(coffeeId);
    }

    public Coffee createCoffee(Coffee coffee) {
        coffees.put(coffee.getId(), coffee);
        return coffee;
    }

    public List<Coffee> getAll() {
        return new ArrayList<>(coffees.values());
    }

    public void update(Coffee coffee) {
        coffees.put(coffee.getId(), coffee);
    }

    public void delete(String coffeeId) {
        coffees.remove(coffeeId);
    }

    public List<Coffee> filterByType(CoffeeType coffeeType) {
        return coffees.values().stream()
                .filter(coffee -> coffee.isAvailable())
                .filter(coffee -> coffee.getCoffeeType() == coffeeType)
                .collect(Collectors.toList());
    }

    public List<Coffee> filterByPriceRange(double minPrice, double maxPrice) {
        if (minPrice > maxPrice) {
            throw new InvalidPriceRangeException(minPrice, maxPrice);
        }
        return coffees.values().stream()
                .filter(coffee -> coffee.isAvailable())
                .filter(coffee -> {
                    double price = coffee.getPrice();
                    return price >= minPrice && price <= maxPrice;
                })
                .collect(Collectors.toList());
    }
}
