package org.dynamik.service;

import org.dynamik.constants.FareType;
import org.dynamik.model.ParkingSpot;
import org.dynamik.model.Ticket;
import org.dynamik.model.Vehicle;
import java.util.List;

public interface IParkingLotService {
    List<ParkingSpot> getAvailableParkingSpots(Vehicle vehicle);
    Ticket bookParkingSpot(ParkingSpot parkingSpot, String vehicleId, FareType fareType);
    Double releaseParkingSpot(String ticketId);
}
