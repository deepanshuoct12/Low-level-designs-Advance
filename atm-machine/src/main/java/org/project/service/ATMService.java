package org.project.service;

import org.project.model.ATM;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ATMService {
    private static final Map<Long, ATM> atmStore = new ConcurrentHashMap<>();
    private static Long idCounter = 1L;

    public ATM create(ATM atm) {
        atm.setId(idCounter++);
        atmStore.put(atm.getId(), atm);
        return atm;
    }

    public ATM getById(Long id) {
        return atmStore.get(id);
    }

    public List<ATM> getAll() {
        return atmStore.values().stream().collect(Collectors.toList());
    }

    public ATM update(Long id, ATM atm) {
        if (atmStore.containsKey(id)) {
            atm.setId(id);
            atmStore.put(id, atm);
            return atm;
        }
        return null;
    }

    public boolean delete(Long id) {
        return atmStore.remove(id) != null;
    }
}
