package org.project.service.impl;

import org.project.model.Client;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ClientServiceImpl {
    private static final Map<String, Client> clientStore = new ConcurrentHashMap<>();

    public Client get(String id) {
        return clientStore.get(id);
    }

    public Client put(Client client) {
        String id = UUID.randomUUID().toString();
        client.setId(id);
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());
        clientStore.put(id, client);
        return client;
    }

    public Client update(String id, Client client) {
        Client existing = clientStore.get(id);
        if (existing == null) {
            return null;
        }
        client.setId(id);
        client.setCreatedAt(existing.getCreatedAt());
        client.setUpdatedAt(LocalDateTime.now());
        clientStore.put(id, client);
        return client;
    }

    public boolean delete(String id) {
        Client removed = clientStore.remove(id);
        return removed != null;
    }

    public List<Client> getAll() {
        return new ArrayList<>(clientStore.values());
    }

    public List<Client> getActiveClients() {
        return clientStore.values().stream()
                .filter(Client::isActive)
                .collect(Collectors.toList());
    }

    public Client getByClientId(String clientId) {
        return clientStore.values().stream()
                .filter(client -> clientId.equals(client.getClientId()))
                .findFirst()
                .orElse(null);
    }

    public List<Client> getByClientType(String clientType) {
        return clientStore.values().stream()
                .filter(client -> clientType.equals(client.getClientType()))
                .collect(Collectors.toList());
    }

    public boolean deactivate(String id) {
        Client client = clientStore.get(id);
        if (client == null) {
            return false;
        }
        client.setActive(false);
        client.setUpdatedAt(LocalDateTime.now());
        return true;
    }
}
