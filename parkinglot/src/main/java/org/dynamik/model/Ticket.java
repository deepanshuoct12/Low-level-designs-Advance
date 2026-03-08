package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dynamik.constants.FareType;

import java.util.Date;

@Data
public class Ticket extends AbstractEntity {
    private Long entryTime;
    private Long exitTime;
    private String vehicleId;
    private String parkingSpotId;
    private FareType fareType;

    public Ticket(String parkingSpotId, String vehicleId) {
        this.parkingSpotId = parkingSpotId;
        this.vehicleId = vehicleId;
        entryTime = new Date().getTime();
    }
}
