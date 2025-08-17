package org.dynamik.dao;

import org.dynamik.model.PortFolio;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PortFolioDao implements IBaseDao<PortFolio, String> {
    private static Map<String, PortFolio> portFolios = new ConcurrentHashMap<>();

    @Override
    public PortFolio save(PortFolio portFolio) {
        if (portFolio.getId() == null) {
            portFolio.setId(UUID.randomUUID().toString());
        }

        return portFolios.put(portFolio.getId(), portFolio);
    }

    @Override
    public PortFolio getById(String s) {
        return portFolios.get(s);
    }

    @Override
    public void update(PortFolio portFolio) {
        portFolios.put(portFolio.getId(), portFolio);
    }

    @Override
    public void delete(String id) {
        portFolios.remove(id);
    }

    @Override
    public List<PortFolio> getAll() {
        return portFolios.values().stream().toList();
    }

    public PortFolio findByAccountId(String accountId) {
        return portFolios.values().stream()
                .filter(portFolio -> portFolio.getAccountId().equals(accountId))
                .findFirst()
                .orElse(null);
    }
}
