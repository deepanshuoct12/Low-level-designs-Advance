package org.project.service;

import org.project.model.Account;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AccountService {
    private static final Map<Long, Account> accountStore = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Account create(Account account) {
        account.setId(idCounter++);
        accountStore.put(account.getId(), account);
        return account;
    }

    public Account getById(Long id) {
        return accountStore.get(id);
    }

    public List<Account> getAll() {
        return accountStore.values().stream().collect(Collectors.toList());
    }

    public Account update(Long id, Account account) {
        if (accountStore.containsKey(id)) {
            account.setId(id);
            accountStore.put(id, account);
            return account;
        }
        return null;
    }

    public boolean delete(Long id) {
        return accountStore.remove(id) != null;
    }
}
