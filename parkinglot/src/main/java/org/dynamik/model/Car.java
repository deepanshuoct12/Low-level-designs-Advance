package org.dynamik.model;

import lombok.Data;

@Data
public class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, org.dynamik.constants.VehicleType.CAR);
    }
}
