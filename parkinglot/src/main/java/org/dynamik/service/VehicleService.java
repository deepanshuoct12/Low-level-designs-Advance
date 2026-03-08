package org.dynamik.service;

import org.dynamik.dao.VehicleDao;
import org.dynamik.model.Vehicle;

import java.util.List;

public class VehicleService {
    private final VehicleDao vehicleDao;

    public VehicleService() {
        this.vehicleDao = new VehicleDao();
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleDao.save(vehicle);
    }

    public Vehicle findById(String id) {
        return vehicleDao.findById(id);
    }

    public void deleteById(String id) {
        vehicleDao.deleteById(id);
    }

    public void update(Vehicle vehicle) {
        vehicleDao.update(vehicle);
    }

    public List<Vehicle> getAll() {
        return vehicleDao.getAll();
    }

    public Vehicle findByLicensePlate(String licensePlate) {
        return vehicleDao.findByLicensePlate(licensePlate);
    }

    public List<Vehicle> findByVehicleType(org.dynamik.constants.VehicleType vehicleType) {
        return vehicleDao.findByVehicleType(vehicleType);
    }
}
