package org.dynamik.model;

import lombok.Data;

@Data
public class Truck extends Vehicle {
    public Truck(String licensePlate) {
        super(licensePlate, org.dynamik.constants.VehicleType.TRUCK);
    }
}
