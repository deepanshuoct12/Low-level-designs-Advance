package org.project.service;

import org.project.dto.FilterRequest;
import org.project.enums.FilterCoffeeType;
import org.project.enums.PaymentStatus;
import org.project.exceptions.*;
import org.project.model.*;
import org.project.observer.Subject;
import org.project.observer.UserObserver;
import org.project.stratergy.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CoffeeVendingMachine extends Subject implements ICoffeVendingMachine {
    private CoffeeService coffeeService;
    private RecipeService recipeService;
    private OrderService orderService;
    private PaymentIntentService paymentIntentService;
    private MenuService menuService;
    private UserService userService;

    public CoffeeVendingMachine() {
        this.coffeeService = new CoffeeService();
        this.recipeService = new RecipeService();
        this.orderService = new OrderService();
        this.paymentIntentService = new PaymentIntentService();
        this.menuService = new MenuService();
        this.userService = new UserService();
    }

    @Override
    public List<Coffee> getMenu() {
        List<Menu> menus = menuService.getAll();
        List<Coffee> availableCoffees = new ArrayList<>();
        
        for (Menu menu : menus) {
            List<Coffee> coffeesInMenu = coffeeService.getAll().stream()
                    .filter(coffee -> coffee.getMenuId() != null)
                    .filter(coffee -> coffee.getMenuId().equals(menu.getId()))
                    .filter(Coffee::isAvailable)
                    .toList();
            
            availableCoffees.addAll(coffeesInMenu);
        }
        
        return availableCoffees;
    }

    @Override
    public List<Coffee> filterCoffeeByType(FilterRequest filterRequest, FilterCoffeeType filterCoffeeType) {
        CoffeeFilterStratergy filterStrategy;
        
        if (filterCoffeeType == FilterCoffeeType.COFFEE_TYPE) {
            filterStrategy = new CoffeeTypeStratergy(coffeeService);
        } else if (filterCoffeeType == FilterCoffeeType.PRICE_RANGE) {
            filterStrategy = new CoffeePriceStratergy(coffeeService);
        } else {
            throw new InvalidInputException("Unsupported filter type: " + filterCoffeeType);
        }
        
        return filterStrategy.filter(filterRequest);
    }

    @Override
    public Recipe getRecipe(String coffeeId) {
        Coffee coffee = coffeeService.getCoffee(coffeeId);

        if (coffee == null) {
            throw new CoffeeNotAvailableException(coffeeId);
        }

        return recipeService.getRecipe(coffee.getRecipeId());
    }

    @Override
    public Order placeOrder(List<String> coffeeIds) {
        double totalPrice = 0;
        
        for (String coffeeId : coffeeIds) {
            Coffee coffee = coffeeService.getCoffee(coffeeId);
            if (coffee == null || !coffee.isAvailable()) {
                throw new InvalidInputException("Coffee not available: " + coffeeId);
            }
            totalPrice += coffee.getPrice();
            
            // Mark coffee as unavailable
            coffee.setAvailable(false);
            coffeeService.update(coffee);
        }
        
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, "user-1", coffeeIds, totalPrice);
        order.setCreatedAt(System.currentTimeMillis());
        order.setUpdatedAt(System.currentTimeMillis());
        
        return orderService.createOrder(order);
    }

    @Override
    public boolean makePayment(String orderId, double amount, String paymentType) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found: " + orderId);
        }
        
        if (amount < order.getTotalPrice()) {
            throw new InsufficientAmountException(order.getTotalPrice(), amount);
        }

        String paymentIntentId = UUID.randomUUID().toString();
        PaymentIntent paymentIntent = new PaymentIntent(paymentIntentId, orderId, amount, PaymentStatus.INIT);
        paymentIntent.setCreatedAt(System.currentTimeMillis());
        paymentIntent.setUpdatedAt(System.currentTimeMillis());
        paymentIntentService.createPaymentIntent(paymentIntent);


        PaymentStrategy paymentStrategy;
        if ("UPI".equalsIgnoreCase(paymentType)) {
            paymentStrategy = new UPIStrategy();
        } else if ("CREDIT_CARD".equalsIgnoreCase(paymentType)) {
            paymentStrategy = new CreditCardStrategy();
        } else {
            throw new UnsupportedPaymentTypeException("Unsupported payment type: " + paymentType);
        }

        boolean paymentSuccess = paymentStrategy.processPayment(amount);
        paymentIntent.setUpdatedAt(System.currentTimeMillis());


        if (paymentSuccess) {
            paymentIntent.setStatus(PaymentStatus.COMPLETED);
            UserObserver userObserver = new UserObserver(order.getUserId());
            this.attach(userObserver);
            this.notifyObservers("Payment successful for order: " + orderId);
            this.detach(userObserver);
        } else {
            paymentIntent.setStatus(PaymentStatus.FAILED);
            UserObserver userObserver = new UserObserver(order.getUserId());
            this.attach(userObserver);
            this.notifyObservers("Payment Failed for order: " + orderId);
            this.detach(userObserver);
        }

        paymentIntentService.update(paymentIntent);
        return paymentSuccess;
    }
}
