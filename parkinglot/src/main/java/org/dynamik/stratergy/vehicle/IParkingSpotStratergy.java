package org.dynamik.stratergy.vehicle;

import org.dynamik.constants.VehicleType;
import org.dynamik.model.ParkingSpot;
import org.dynamik.model.Vehicle;

import java.util.List;

public interface IParkingSpotStratergy {
    Boolean isApplicable(VehicleType vehicle);
    List<ParkingSpot> getParkingSpot();
}
