package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
public class Booking extends BaseEntity {
    private Slot slot;
    private Vehicle vehicle;
    private Branch branch;

    public Booking(Slot slot, Vehicle vehicle, Branch branch) {
        this.slot = slot;
        this.vehicle = vehicle;
        this.branch = branch;
        this.setId(UUID.randomUUID().toString());
        this.setCreatedAt(System.currentTimeMillis());
    }
}
