package org.project.service;

import org.project.model.Bank;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BankService {
    private static final Map<Long, Bank> bankStore = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Bank create(Bank bank) {
        bank.setId(idCounter++);
        bankStore.put(bank.getId(), bank);
        return bank;
    }

    public Bank getById(Long id) {
        return bankStore.get(id);
    }

    public List<Bank> getAll() {
        return bankStore.values().stream().collect(Collectors.toList());
    }

    public Bank update(Long id, Bank bank) {
        if (bankStore.containsKey(id)) {
            bank.setId(id);
            bankStore.put(id, bank);
            return bank;
        }
        return null;
    }

    public boolean delete(Long id) {
        return bankStore.remove(id) != null;
    }
}
