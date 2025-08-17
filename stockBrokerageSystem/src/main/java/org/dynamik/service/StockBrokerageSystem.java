package org.dynamik.service;


import org.dynamik.enums.OrderState;
import org.dynamik.enums.PaymentState;
import org.dynamik.model.*;
import org.dynamik.stratergy.ClosedOrderStratergy;
import org.dynamik.stratergy.IOrderStratergy;
import org.dynamik.stratergy.OpenOrderStratergy;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.dynamik.enums.OrderType.BUY;
import static org.dynamik.enums.OrderType.SELL;

public class StockBrokerageSystem implements IStockBrokerageSystem {
    private static StockService stockService;
    private static OrderService orderService;
    private static PaymentService paymentService;
    private static AccountService accountService;
    private static PortfolioService portfolioService;
    private static Map<OrderState, IOrderStratergy> orderStrategies;
    private static StockBrokerageSystem stockBrokerageSystem;

    private StockBrokerageSystem() {

    }

    public static StockBrokerageSystem getInstance() {
       if (stockBrokerageSystem == null) {
           synchronized (StockBrokerageSystem.class) {
               if (stockBrokerageSystem == null) {
                   stockBrokerageSystem = new StockBrokerageSystem();
                   stockService = new StockService();
                   orderService = new OrderService();
                   paymentService = new PaymentService();
                   accountService = new AccountService();
                   portfolioService = new PortfolioService();
                   orderStrategies = new HashMap<>();
                   orderStrategies.put(OrderState.OPEN, new OpenOrderStratergy());
                   orderStrategies.put(OrderState.CLOSED, new ClosedOrderStratergy());
               }
           }
       }

       return stockBrokerageSystem;
    }

    @Override
    public Stock buyStock(String userId, String stockId, Long quantity, Long amount) {
        // 1. Validate inputs
        if (quantity <= 0 || amount <= 0) {
            throw new IllegalArgumentException("Quantity and amount must be positive");
        }

        // 2. Get account and validate balance
        Account account = accountService.getAccountByUserId(userId);
        if (account == null) {
            throw new IllegalStateException("Account not found for user: " + userId);
        }

        // 3. Get stock and validate
        Stock stock = stockService.findById(stockId);
        if (stock == null) {
            throw new IllegalStateException("Stock not found: " + stockId);
        }

        long totalCost = stock.getPrice() * quantity;
        if (!accountService.hasSufficientBalance(account.getId(), totalCost)) {
            throw new IllegalStateException("Insufficient balance");
        }

        // 4. Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setStockId(stockId);
        order.setQuantity(quantity);
        order.setOrderState(OrderState.OPEN);
        order.setOrderType(BUY);
        order.setCreatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        
        order = orderService.save(order);

        // 5. Process payment
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setAmount(totalCost);
        payment.setPaymentState(PaymentState.PENDING);
        payment = paymentService.save(payment);

        // 6. Update account balance and portfolio if payment is successful
        if (paymentService.processPayment(payment.getId())) {
            payment.notifyObservers(payment);   // notify observers
            accountService.withdraw(account.getId(), totalCost);
            portfolioService.addStockToPortfolio(userId, stockId, (long) quantity);
            order.setOrderState(OrderState.CLOSED);
            orderService.save(order);
        }

        return stock;
    }

    @Override
    public void sellStock(String userId, String stockId, Long quantity) {
        // 1. Validate inputs
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // 2. Get portfolio and check stock quantity
        PortFolio portfolio = portfolioService.getUserPortfolio(userId);
        if (portfolio == null || portfolio.getHoldings() == null || 
            portfolio.getHoldings().get(stockId) == null || 
            portfolio.getHoldings().get(stockId) < quantity) {
            throw new IllegalStateException("Insufficient stock quantity");
        }

        // 3. Get stock and calculate total amount
        Stock stock = stockService.findById(stockId);
        if (stock == null) {
            throw new IllegalStateException("Stock not found: " + stockId);
        }
        long totalAmount = stock.getPrice() * quantity;

        // 4. Create sell order
        Order order = new Order();
        order.setUserId(userId);
        order.setStockId(stockId);
        order.setQuantity(quantity);
        order.setOrderState(OrderState.OPEN);
        order.setOrderType(SELL);
        order.setCreatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        
        order = orderService.save(order);

        // 5. Process the sale
        portfolioService.removeStockFromPortfolio(userId, stockId, (long) quantity);
        accountService.deposit(userId, totalAmount);
        
        order.setOrderState(OrderState.CLOSED);
        orderService.save(order);
    }

    @Override
    public List<Stock> getStocks() {
        return stockService.findAll();
    }

    @Override
    public List<Order> getOrderHistory(String userId, OrderState state) {
        IOrderStratergy strategy = orderStrategies.get(state);
        if (strategy != null && strategy.isApplicable(state)) {
            return strategy.getOrders(userId);
        }
        return orderService.findByUserId(userId);
    }

    @Override
    public PortFolio getPortfolio(String userId) {
        return portfolioService.getUserPortfolio(userId);
    }

    @Override
    public List<Payment> getTransactionHistory(String userId) {
        List<Order> orders = orderService.findByUserId(userId);
        List<Payment> payments = new ArrayList<>();
        for (Order order : orders) {
            payments.add(paymentService.findByOrderId(order.getId()));
        }

        return payments;
    }

    @Override
    public Account getAccount(String userId) {
        return accountService.getAccountByUserId(userId);
    }

    @Override
    public void deposit(String accountId, Long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        accountService.deposit(accountId, amount);
    }

    @Override
    public void withdraw(String accountId, Long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (!accountService.hasSufficientBalance(accountId, amount)) {
            throw new IllegalStateException("Insufficient balance");
        }
        accountService.withdraw(accountId, amount);
    }
}
