package org.dynamik.stratergy;

import org.dynamik.constants.VehicleBookingType;
import org.dynamik.constants.VehicleType;
import org.dynamik.model.Slot;
import org.dynamik.model.Vehicle;

public interface IVehicleBookStratergy {
    Boolean isApplicable(VehicleBookingType vehicleBookingType);
    Vehicle getVehicle(String branchId, VehicleType vehicleType, Slot slot);
}
