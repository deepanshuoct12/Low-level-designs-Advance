package org.dynamik.service;

import org.dynamik.constants.VehicleBookingType;
import org.dynamik.constants.VehicleType;
import org.dynamik.exception.ResourceNotFoundException;
import org.dynamik.model.Branch;
import org.dynamik.model.Slot;
import org.dynamik.model.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public interface ICarRentalService {
    List<Vehicle> displayVehicle(String branchId);
    Vehicle getVehicleForAGivenSlot(String branchId, Slot slot, VehicleType vehicleType, VehicleBookingType vehicleBookingType);
    void bookVehicle(Vehicle vehicle, Branch branch) throws ResourceNotFoundException;
    void releaseVehicle(Vehicle vehicle) throws ResourceNotFoundException;
}
