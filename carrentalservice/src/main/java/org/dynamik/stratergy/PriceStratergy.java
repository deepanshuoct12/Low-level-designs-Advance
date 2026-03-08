package org.dynamik.stratergy;

import org.dynamik.constants.VehicleBookingType;
import org.dynamik.constants.VehicleType;
import org.dynamik.model.Branch;
import org.dynamik.model.Slot;
import org.dynamik.model.Vehicle;
import org.dynamik.service.BranchService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PriceStratergy implements IVehicleBookStratergy {
    private BranchService branchService = new BranchService();

    @Override
    public Boolean isApplicable(VehicleBookingType vehicleBookingType) {
        return VehicleBookingType.PRICE.equals(vehicleBookingType);
    }

    @Override
    public Vehicle getVehicle(String branchId, VehicleType vehicleType, Slot slot) {
        Branch branch = branchService.findById(branchId);

        //filter vehicles based on slot
        List<Vehicle> vehicles = branch.getVehicles().stream().filter(vehicle -> vehicle.getSlot().equals(slot)).collect(Collectors.toList());

        // sort vehicles based on lowest price
        vehicles.sort(Comparator.comparingLong(Vehicle::getPrice));
        return vehicles.get(0); // return minimum price vehicle
    }
}
