package org.project.service;

import org.project.model.Receipt;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ReceiptService {
    private static final Map<Long, Receipt> receiptStore = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public Receipt create(Receipt receipt) {
        receipt.setId(idCounter++);
        receiptStore.put(receipt.getId(), receipt);
        return receipt;
    }

    public Receipt getById(Long id) {
        return receiptStore.get(id);
    }

    public List<Receipt> getAll() {
        return receiptStore.values().stream().collect(Collectors.toList());
    }

    public Receipt update(Long id, Receipt receipt) {
        if (receiptStore.containsKey(id)) {
            receipt.setId(id);
            receiptStore.put(id, receipt);
            return receipt;
        }
        return null;
    }

    public boolean delete(Long id) {
        return receiptStore.remove(id) != null;
    }
}
