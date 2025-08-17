package org.dynamik.demo;

import org.dynamik.enums.OrderState;
import org.dynamik.model.*;
import org.dynamik.service.*;
import org.dynamik.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * Demonstrates the capabilities of the Stock Brokerage System.
 * This class serves as a driver to showcase all major functionalities.
 */
public class StockSystemDriver {
    private final IStockBrokerageSystem brokerageSystem;
    private final AccountService accountService;
    private final StockService stockService;
    private final PortfolioService portfolioService;
    private final UserService userService;
    
    public StockSystemDriver() {
        this.brokerageSystem = StockBrokerageSystem.getInstance();
        this.accountService = new AccountService();
        this.stockService = new StockService();
        this.portfolioService = new PortfolioService();
        this.userService = new UserService();
    }
    
    public void runDemo() {
        System.out.println("=== Stock Brokerage System Demo ===\n");
        
        // 1. Create sample users and their accounts
        createSampleUsers();
        
        // 2. Add sample stocks
        addSampleStocks();
        
        // 3. Demonstrate account operations
        demonstrateAccountOperations("1");
        
        // 4. Demonstrate stock trading
        demonstrateTrading("1", "AAPL", 10L, 15000L);
        
        // 5. Show portfolio and transaction history
        showPortfolioAndHistory("1");
    }
    
    private void createSampleUsers() {
        System.out.println("=== Creating Sample Users ===");
        
        // Create sample user 1
        User user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setId("1");
        user1 = userService.createUser(user1);
        
        // Create sample user 2
        User user2 = new User();
        user2.setName("Jane Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setId("2");
        user2 = userService.createUser(user2);
        
        System.out.println("Sample users created: " + user1.getName() + " and " + user2.getName() + "\n");
        
        createAccountsForUsers(user1, user2);
    }
    
    private void createAccountsForUsers(User... users) {
        System.out.println("=== Creating Accounts for Users ===");
        
        for (User user : users) {
            // Create account for user
            Account account = new Account();
            account.setUserId(user.getId());
            account.setEmail(user.getEmail());
            account.setId(user.getName());

            long initialBalance = 50000L;
            account.setBalance(initialBalance);
            
            accountService.saveAccount(account);
            
            // Initialize empty portfolio
            PortFolio portfolio = new PortFolio();
            portfolio.setAccountId(account.getUserId());
            portfolioService.save(portfolio);
            
            System.out.printf("Created account for %s with initial balance: $%.2f%n", 
                user.getName(), initialBalance / 100.0);
        }
        System.out.println();
    }
    
    private void addSampleStocks() {
        System.out.println("=== Adding Sample Stocks ===");
        
        // Add Apple Inc.
        Stock aapl = new Stock();
        aapl.setId("AAPL");
        aapl.setName("Apple Inc.");
        aapl.setPrice(17500L); // $175.00
        stockService.save(aapl);
        
        // Add Alphabet Inc. (Google)
        Stock googl = new Stock();
        googl.setId("GOOGL");
        googl.setName("Alphabet Inc.");
        googl.setPrice(135000L); // $1,350.00
        stockService.save(googl);
        
        // Add Microsoft
        Stock msft = new Stock();
        msft.setId("MSFT");
        msft.setName("Microsoft Corporation");
        msft.setPrice(25000L); // $250.00
        stockService.save(msft);
        
        System.out.println("Sample stocks added to the system:");
        System.out.println("- AAPL: Apple Inc. ($175.00)");
        System.out.println("- GOOGL: Alphabet Inc. ($1,350.00)");
        System.out.println("- MSFT: Microsoft Corporation ($250.00)\n");
    }
    
    private void demonstrateAccountOperations(String userId) {
        try {
            System.out.println("=== Account Operations ===");

            // Initial deposit
            brokerageSystem.deposit(userId, 50000L);
            System.out.println("Initial deposit of $50,000 completed");

            // Check balance
            Account account = brokerageSystem.getAccount(userId);
            System.out.println("Current balance: $" + account.getBalance() / 100.0);

            // Withdraw some amount
            brokerageSystem.withdraw(userId, 100L);
            System.out.println("Withdrawn $100.00");

            account = brokerageSystem.getAccount(userId);
            System.out.println("Updated balance: $" + account.getBalance() / 100.0 + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void demonstrateTrading(String userId, String stockId, Long quantity, Long amount) {
        System.out.println("=== Stock Trading ===");
        
        try {
            // Buy stocks
            System.out.println("Placing buy order for " + quantity + " shares of " + stockId);
            Stock boughtStock = brokerageSystem.buyStock(userId, stockId, quantity, amount);
            System.out.println("Successfully bought " + quantity + " shares of " + boughtStock.getName() + 
                             " at $" + (boughtStock.getPrice() / 100.0) + " per share");
            boughtStock.notifyObservers(boughtStock);

            // Show portfolio
            
            // Show open orders
            List<Order> openOrders = brokerageSystem.getOrderHistory(userId, OrderState.OPEN);
            System.out.println("\nOpen Orders:");
            openOrders.forEach(order -> 
                System.out.printf("- %s %d shares of %s at $%.2f%n", 
                    order.getOrderType(), 
                    order.getQuantity(),
                    order.getStockId()
                  //  order.getPrice() / 100.0)
            ));
            
            // Sell some stocks
            System.out.println("\nPlacing sell order for " + (quantity / 2) + " shares of " + stockId);
            brokerageSystem.sellStock(userId, stockId, quantity / 2);
            System.out.println("Successfully sold " + (quantity / 2) + " shares of " + stockId);
            
        } catch (Exception e) {
            System.err.println("Trading error: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    private void showPortfolioAndHistory(String userId) {
        System.out.println("=== Portfolio and Transaction History ===");
        
        // Show portfolio
        PortFolio portfolio = brokerageSystem.getPortfolio(userId);
        System.out.println("\nPortfolio:");
        if (portfolio != null && portfolio.getHoldings() != null) {
            portfolio.getHoldings().forEach((stockId, quantity) -> 
                System.out.println("- " + stockId + ": " + quantity + " shares")
            );
        }
        
        // Show transaction history
        System.out.println("\nTransaction History:");
        List<Payment> transactions = brokerageSystem.getTransactionHistory(userId);
        transactions.forEach(payment -> 
            System.out.printf("- %s: $%.2f - %s%n", 
                payment.getId(), 
                payment.getAmount() / 100.0,
                payment.getPaymentState())
        );
        
        // Show order history
        System.out.println("\nOrder History (All):");
        List<Order> allOrders = brokerageSystem.getOrderHistory(userId, OrderState.CLOSED);
        allOrders.forEach(order -> 
            System.out.printf("- %s %d shares of %s at $%.2f - %s%n", 
                order.getOrderType(), 
                order.getQuantity(),
                order.getStockId(),
             //   order.getPrice() / 100.0,
                order.getOrderState())
        );
    }
}
