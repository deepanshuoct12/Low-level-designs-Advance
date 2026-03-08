package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dynamik.constants.State;
import org.dynamik.constants.VehicleType;

@Data
@AllArgsConstructor
public class Vehicle extends BaseEntity {
    private String      name;
    private VehicleType vehicleType;
    private State       state;
    private Long        price;
    private Slot        slot;
}
