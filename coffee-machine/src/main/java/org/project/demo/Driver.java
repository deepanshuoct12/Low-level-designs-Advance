package org.project.demo;

import org.project.dto.FilterRequest;
import org.project.enums.CoffeeBeansType;
import org.project.enums.CoffeeType;
import org.project.enums.FilterCoffeeType;
import org.project.model.Coffee;
import org.project.model.Menu;
import org.project.model.Order;
import org.project.model.Recipe;
import org.project.service.CoffeeService;
import org.project.service.CoffeeVendingMachine;
import org.project.service.MenuService;
import org.project.service.RecipeService;

import java.util.Arrays;
import java.util.List;

public class Driver {
    public void runDemo() {
        System.out.println("=== Coffee Vending Machine Demo ===\n");

        // Initialize services
        CoffeeService coffeeService = new CoffeeService();
        RecipeService recipeService = new RecipeService();
        MenuService menuService = new MenuService();

        // Create recipes
        Recipe latteRecipe = new Recipe("recipe-1", "Latte Recipe", 200, 150, CoffeeBeansType.ARABICA);
        latteRecipe.setCreatedAt(System.currentTimeMillis());
        latteRecipe.setUpdatedAt(System.currentTimeMillis());
        recipeService.createRecipe(latteRecipe);

        Recipe cappuccinoRecipe = new Recipe("recipe-2", "Cappuccino Recipe", 150, 100, CoffeeBeansType.ROBUSTA);
        cappuccinoRecipe.setCreatedAt(System.currentTimeMillis());
        cappuccinoRecipe.setUpdatedAt(System.currentTimeMillis());
        recipeService.createRecipe(cappuccinoRecipe);

        Recipe espressoRecipe = new Recipe("recipe-3", "Espresso Recipe", 50, 50, CoffeeBeansType.ARABICA);
        espressoRecipe.setCreatedAt(System.currentTimeMillis());
        espressoRecipe.setUpdatedAt(System.currentTimeMillis());
        recipeService.createRecipe(espressoRecipe);

        // Create coffees
        Coffee latte = new Coffee("coffee-1", "Latte", "menu-1", 5.99, "recipe-1", CoffeeType.LATTE, true);
        latte.setCreatedAt(System.currentTimeMillis());
        latte.setUpdatedAt(System.currentTimeMillis());
        coffeeService.createCoffee(latte);

        Coffee cappuccino = new Coffee("coffee-2", "Cappuccino", "menu-1", 4.99, "recipe-2", CoffeeType.CAPPUCCINO, true);
        cappuccino.setCreatedAt(System.currentTimeMillis());
        cappuccino.setUpdatedAt(System.currentTimeMillis());
        coffeeService.createCoffee(cappuccino);

        Coffee espresso = new Coffee("coffee-3", "Espresso", "menu-1", 3.99, "recipe-3", CoffeeType.ESPRESSO, true);
        espresso.setCreatedAt(System.currentTimeMillis());
        espresso.setUpdatedAt(System.currentTimeMillis());
        coffeeService.createCoffee(espresso);

        // Create menu
        Menu menu = new Menu("menu-1", "Main Menu");
        menu.setCreatedAt(System.currentTimeMillis());
        menu.setUpdatedAt(System.currentTimeMillis());
        menuService.createMenu(menu);

        // Initialize vending machine
        CoffeeVendingMachine vendingMachine = new CoffeeVendingMachine();

        // Step 1: Get menu
        System.out.println("Step 1: Getting coffee menu...");
        List<Coffee> menuCoffees = vendingMachine.getMenu();
        System.out.println("Available coffees in menu:");
        for (Coffee coffee : menuCoffees) {
            System.out.println("  - " + coffee.getName() + " ($" + coffee.getPrice() + ")");
        }
        System.out.println();

        // Step 2: Filter by coffee type
        System.out.println("Step 2: Filtering coffees by type (LATTE)...");
        FilterRequest filterRequest = new FilterRequest(null, CoffeeType.LATTE);
        List<Coffee> filteredCoffees = vendingMachine.filterCoffeeByType(filterRequest, FilterCoffeeType.COFFEE_TYPE);
        System.out.println("Filtered coffees (LATTE):");
        for (Coffee coffee : filteredCoffees) {
            System.out.println("  - " + coffee.getName() + " ($" + coffee.getPrice() + ")");
        }
        System.out.println();

        // Step 3: Select a coffee
        System.out.println("Step 3: Selecting a coffee...");
        if (!filteredCoffees.isEmpty()) {
            Coffee selectedCoffee = filteredCoffees.get(0);
            System.out.println("Selected: " + selectedCoffee.getName() + " ($" + selectedCoffee.getPrice() + ")");
            System.out.println();

            // Step 4: Place order
            System.out.println("Step 4: Placing order for selected coffee...");
            Order order = vendingMachine.placeOrder(Arrays.asList(selectedCoffee.getId()));
            System.out.println("Order placed successfully!");
            System.out.println("Order ID: " + order.getId());
            System.out.println("Total Price: $" + order.getTotalPrice());
            System.out.println();

            // Step 5: Make payment
            System.out.println("Step 5: Making payment...");
            boolean paymentSuccess = vendingMachine.makePayment(order.getId(), order.getTotalPrice(), "UPI");
            System.out.println("Payment " + (paymentSuccess ? "successful" : "failed"));
            System.out.println();

            // Step 6: Fetch menu again to show purchased coffee is unavailable
            System.out.println("Step 6: Fetching menu again after purchase...");
            List<Coffee> menuCoffeesAfter = vendingMachine.getMenu();
            System.out.println("Available coffees in menu after purchase:");
            if (menuCoffeesAfter.isEmpty()) {
                System.out.println("  No coffees available (purchased coffee marked as unavailable)");
            } else {
                for (Coffee coffee : menuCoffeesAfter) {
                    System.out.println("  - " + coffee.getName() + " ($" + coffee.getPrice() + ") - Available: " + coffee.isAvailable());
                }
            }
            System.out.println();
        } else {
            System.out.println("No coffees available after filtering.");
        }

        System.out.println("=== Demo Complete ===");
    }
}
