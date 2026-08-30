package org.project.service;

import org.project.model.BalanceSheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BalanceSheetService {
    private static final ConcurrentHashMap<String, BalanceSheet> BALANCE_SHEETS = new ConcurrentHashMap<>();

    public BalanceSheet create(BalanceSheet balanceSheet) {
        if (balanceSheet.getId() == null) {
            balanceSheet.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        balanceSheet.setCreatedAt(now);
        balanceSheet.setUpdatedAt(now);
        BALANCE_SHEETS.put(balanceSheet.getId(), balanceSheet);
        return balanceSheet;
    }

    public BalanceSheet getById(String id) {
        return BALANCE_SHEETS.get(id);
    }

    public List<BalanceSheet> getAll() {
        return new ArrayList<>(BALANCE_SHEETS.values());
    }

    public BalanceSheet update(BalanceSheet balanceSheet) {
        if (!BALANCE_SHEETS.containsKey(balanceSheet.getId())) {
            return null;
        }
        balanceSheet.setUpdatedAt(System.currentTimeMillis());
        BALANCE_SHEETS.put(balanceSheet.getId(), balanceSheet);
        return balanceSheet;
    }

    public void delete(String id) {
        BALANCE_SHEETS.remove(id);
    }

    public BalanceSheet getByUserId(String userId) {
        for (BalanceSheet balanceSheet : BALANCE_SHEETS.values()) {
            if (userId.equals(balanceSheet.getUserId())) {
                return balanceSheet;
            }
        }

        BalanceSheet balanceSheet = new BalanceSheet();
        balanceSheet.setUserId(userId);
        balanceSheet.setBalances(new HashMap<>());
        return create(balanceSheet);
    }
}
