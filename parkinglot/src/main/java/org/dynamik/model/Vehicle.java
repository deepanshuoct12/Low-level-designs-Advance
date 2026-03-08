package org.dynamik.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dynamik.constants.VehicleType;

@Data
@NoArgsConstructor
public class Vehicle extends AbstractEntity {
    private String licensePlate;
    private VehicleType vehicleType;

    public Vehicle(String licensePlate, org.dynamik.constants.VehicleType vehicleType) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }
}
