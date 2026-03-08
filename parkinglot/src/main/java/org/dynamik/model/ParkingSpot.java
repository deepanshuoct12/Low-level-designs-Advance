package org.dynamik.model;

import lombok.Data;
import org.dynamik.constants.BookingStatus;
import org.dynamik.constants.VehicleType;

@Data
public class ParkingSpot extends AbstractEntity {
    private String number;
    private String levelId;
    private BookingStatus bookingStatus;
    private VehicleType vehicleType;
//    private String vehicleId;
}
