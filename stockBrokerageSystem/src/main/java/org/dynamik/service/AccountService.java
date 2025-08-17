package org.dynamik.service;

import org.dynamik.dao.AccountDao;
import org.dynamik.model.Account;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    public Account createAccount(String userId, String email) {
        Account account = new Account();
        account.setUserId(userId);
        account.setEmail(email);
        return accountDao.save(account);
    }

    public void deleteAccount(String accountId) {
        accountDao.delete(accountId);
    }

    public Account saveAccount(Account account) {
        return accountDao.save(account);
    }

    public Account getAccount(String accountId) {
        return accountDao.getById(accountId);
    }

    public Account getAccountByUserId(String userId) {
        return accountDao.findByUserId(userId);
    }

    /**
     * Deposits the specified amount into the account
     * @param accountId The ID of the account
     * @param amount The amount to deposit (must be positive)
     * @return true if the deposit was successful, false otherwise
     * @throws IllegalArgumentException if amount is not positive
     */
    public boolean deposit(String accountId, Long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        return accountDao.updateBalance(accountId, amount);
    }

    /**
     * Withdraws the specified amount from the account
     * @param accountId The ID of the account
     * @param amount The amount to withdraw (must be positive and not exceed balance)
     * @return true if the withdrawal was successful, false otherwise
     * @throws IllegalArgumentException if amount is not positive or exceeds balance
     */
    public boolean withdraw(String accountId, Long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        // Check if account has sufficient balance
        if (!hasSufficientBalance(accountId, amount)) {
            throw new IllegalArgumentException("Insufficient balance for withdrawal");
        }
        
        return accountDao.updateBalance(accountId, -amount);
    }
    
    /**
     * Adds the specified amount to the account balance
     * @param accountId The ID of the account
     * @param amount The amount to add (must be positive)
     * @return true if the operation was successful, false otherwise
     * @throws IllegalArgumentException if amount is not positive
     */
    public boolean addBalance(String accountId, Long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return accountDao.updateBalance(accountId, amount);
    }

    public boolean hasSufficientBalance(String accountId, Long amount) {
        Account account = accountDao.getById(accountId);
        return account != null && account.getBalance() >= amount;
    }

    public Long getBalance(String accountId) {
        Account account = accountDao.getById(accountId);
        return account != null ? account.getBalance() : 0L;
    }
}
