package org.dynamik.stratergy.vehicle;

import org.dynamik.constants.VehicleType;
import org.dynamik.model.ParkingSpot;
import org.dynamik.model.Vehicle;
import org.dynamik.service.ParkingSpotService;

import java.util.List;

import static org.dynamik.constants.VehicleType.CAR;

public class CarParkingStratergy implements IParkingSpotStratergy {
    private ParkingSpotService parkingSpotService = new ParkingSpotService();

    @Override
    public Boolean isApplicable(VehicleType vehicle) {
        return CAR.equals(vehicle);
    }

    @Override
    public List<ParkingSpot> getParkingSpot() {
        return parkingSpotService.findByVehicleType(CAR);
    }
}
