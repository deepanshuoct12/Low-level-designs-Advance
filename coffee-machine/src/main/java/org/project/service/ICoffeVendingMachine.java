package org.project.service;


import org.project.dto.FilterRequest;
import org.project.enums.FilterCoffeeType;
import org.project.model.Coffee;
import org.project.model.Order;
import org.project.model.Recipe;

import java.util.List;

public interface ICoffeVendingMachine {
    List<Coffee> getMenu();
    List<Coffee> filterCoffeeByType(FilterRequest filterRequest, FilterCoffeeType filterCoffeeType);
    Recipe getRecipe(String coffeeId);
    Order placeOrder(List<String> coffeeIds);
    boolean makePayment(String orderId, double amount, String paymentType);
}
