package org.dynamik.dao;

import org.dynamik.model.Account;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AccountDao implements IBaseDao<Account, String> {
    private final static Map<String, Account> accounts = new ConcurrentHashMap<>();
    
    @Override
    public Account save(Account account) {
        if (account.getId() == null) {
            account.setId(UUID.randomUUID().toString());
        }
        accounts.put(account.getId(), account);
        return account;
    }
    
    @Override
    public Account getById(String id) {
        return accounts.get(id);
    }
    
    @Override
    public void update(Account account) {
        if (account.getId() != null) {
            accounts.put(account.getId(), account);
        }
    }
    
    @Override
    public void delete(String id) {
        accounts.remove(id);
    }
    
    @Override
    public List<Account> getAll() {
        return accounts.values().stream().collect(Collectors.toList());
    }
    
    public Account findByUserId(String userId) {
        return accounts.values().stream()
                .filter(account -> account.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }
    
    public boolean updateBalance(String accountId, Long amount) {
        return accounts.computeIfPresent(accountId, (id, account) -> {
            account.setBalance(account.getBalance() + amount);
            return account;
        }) != null;
    }
}
