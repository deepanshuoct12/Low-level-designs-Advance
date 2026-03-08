package org.dynamik.model;

import lombok.Data;

@Data
public class Bike extends Vehicle {
    public Bike(String licensePlate) {
        super(licensePlate, org.dynamik.constants.VehicleType.BIKE);
    }
}
