package org.project.service.impl;

import org.project.enums.ServiceStatus;
import org.project.exception.ServiceNotFoundException;
import org.project.model.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ServiceServiceImpl {
    private static final Map<String, Service> serviceStore = new ConcurrentHashMap<>();

    public Service get(String id) {
        Service service = serviceStore.get(id);
        if (service == null) {
            throw new ServiceNotFoundException(id);
        }
        return service;
    }

    public Service put(Service service) {
        String id = UUID.randomUUID().toString();
        service.setId(id);
        service.setCreatedAt(LocalDateTime.now());
        service.setUpdatedAt(LocalDateTime.now());
        serviceStore.put(id, service);
        return service;
    }

    public Service update(String id, Service service) {
        Service existing = serviceStore.get(id);
        if (existing == null) {
            throw new ServiceNotFoundException(id);
        }
        service.setId(id);
        service.setCreatedAt(existing.getCreatedAt());
        service.setUpdatedAt(LocalDateTime.now());
        serviceStore.put(id, service);
        return service;
    }

    public boolean delete(String id) {
        Service removed = serviceStore.remove(id);
        if (removed == null) {
            throw new ServiceNotFoundException(id);
        }
        return true;
    }

    public List<Service> getAll() {
        return new ArrayList<>(serviceStore.values());
    }


    public List<Service> getByServiceName(String serviceName) {
        return serviceStore.values().stream()
                .filter(service -> serviceName.equals(service.getServiceName()))
                .collect(Collectors.toList());
    }
}
