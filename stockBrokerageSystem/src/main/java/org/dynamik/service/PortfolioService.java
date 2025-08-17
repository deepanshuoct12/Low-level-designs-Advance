package org.dynamik.service;

import org.dynamik.dao.PortFolioDao;
import org.dynamik.model.Account;
import org.dynamik.model.PortFolio;

import java.util.Map;


public class PortfolioService {
    
    private final PortFolioDao portfolioDao;
    private final AccountService accountService;

    public PortfolioService() {
        this.portfolioDao = new PortFolioDao();
        this.accountService = new AccountService();
    }

    public PortFolio save(PortFolio portFolio) {
        return portfolioDao.save(portFolio);
    }

    public PortFolio getUserPortfolio(String userId) {
        Account account = accountService.getAccountByUserId(userId);
        if (account == null) {
            throw new IllegalStateException("Account not found for user: " + userId);
        }

        return portfolioDao.findByAccountId(account.getUserId());
    }
    
    public void addStockToPortfolio(String userId, String stockId, Long quantity) {
        PortFolio portfolio = getUserPortfolio(userId);
        portfolio.getHoldings().merge(stockId, quantity, Long::sum);
        portfolioDao.save(portfolio);
    }
    
    public void removeStockFromPortfolio(String userId, String stockId, Long quantity) {
        PortFolio portfolio = getUserPortfolio(userId);
        Long currentQuantity = portfolio.getHoldings().getOrDefault(stockId, 0L);
        if (currentQuantity < quantity) {
            throw new IllegalStateException("Insufficient stock quantity");
        }
        portfolio.getHoldings().put(stockId, currentQuantity - quantity);
        portfolioDao.save(portfolio);
    }
    
    public Map<String, Long> getHoldings(String userId) {
        return getUserPortfolio(userId).getHoldings();
    }
    
    public boolean updatePortfolio(String userId, Map<String, Long> holdings) {
        PortFolio portfolio = getUserPortfolio(userId);
        portfolio.setHoldings(holdings);
        portfolioDao.save(portfolio);
        return true;
    }
}
