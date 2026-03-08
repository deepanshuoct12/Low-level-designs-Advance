package org.dynamik.service;

import org.dynamik.model.Vehicle;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class VehicleService {
    private static final ConcurrentHashMap<String, Vehicle> vehicles = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, ReentrantLock> vehicleLocks = new ConcurrentHashMap<>();

    public void save(Vehicle vehicle) {
        vehicles.put(vehicle.getId(), vehicle);
    }

    public Vehicle findById(String id) {
        return vehicles.get(id);
    }

    public void saveAll(List<Vehicle> vehicleList) {
        vehicleList.forEach(vehicle -> save(vehicle));
    }

    public void update(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null for update");
        }
        vehicles.put(vehicle.getId(), vehicle);
    }
    
    public ReentrantLock getVehicleLock(String vehicleId) {
        return vehicleLocks.computeIfAbsent(vehicleId, id -> new ReentrantLock());
    }
}
