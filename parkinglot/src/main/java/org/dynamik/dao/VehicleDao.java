package org.dynamik.dao;

import org.dynamik.model.Vehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VehicleDao implements IBaseDao<Vehicle, String> {
    private Map<String, Vehicle> vehicles = new HashMap<>();

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicles.put(vehicle.getId(), vehicle);
    }

    @Override
    public Vehicle findById(String id) {
        return vehicles.get(id);
    }

    @Override
    public void deleteById(String id) {
        vehicles.remove(id);
    }

    @Override
    public void update(Vehicle vehicle) {
        if (vehicles.get(vehicle.getId()) != null) {
            vehicles.put(vehicle.getId(), vehicle);
        }
    }

    @Override
    public List<Vehicle> getAll() {
        return vehicles.values().stream().collect(Collectors.toList());
    }

    public Vehicle findByLicensePlate(String licensePlate) {
        return vehicles.values().stream()
                .filter(vehicle -> licensePlate.equals(vehicle.getLicensePlate()))
                .findFirst()
                .orElse(null);
    }

    public List<Vehicle> findByVehicleType(org.dynamik.constants.VehicleType vehicleType) {
        return vehicles.values().stream()
                .filter(vehicle -> vehicleType.equals(vehicle.getVehicleType()))
                .collect(Collectors.toList());
    }
}
