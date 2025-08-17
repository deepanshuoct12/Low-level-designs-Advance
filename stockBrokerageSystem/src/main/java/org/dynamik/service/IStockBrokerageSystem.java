package org.dynamik.service;


import org.dynamik.enums.OrderState;
import org.dynamik.model.*;

import java.util.List;

public interface IStockBrokerageSystem {
    Stock buyStock(String userId, String stockId, Long quantity, Long amount);
    void sellStock(String userId, String stockId, Long quantity);
    List<Stock> getStocks();
    List<Order> getOrderHistory(String userId, OrderState state);
    PortFolio getPortfolio(String userId);
    List<Payment> getTransactionHistory(String userId);
    Account getAccount(String userId);
    void deposit(String accountId, Long amount);
    void withdraw(String accountId, Long amount);
}
