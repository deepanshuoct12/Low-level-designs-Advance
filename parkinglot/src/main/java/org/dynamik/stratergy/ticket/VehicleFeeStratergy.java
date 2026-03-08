package org.dynamik.stratergy.ticket;

import org.dynamik.constants.FareType;
import org.dynamik.constants.VehicleType;
import org.dynamik.model.Ticket;
import org.dynamik.model.Vehicle;
import org.dynamik.service.TicketService;
import org.dynamik.service.VehicleService;

import java.util.Map;

import static org.dynamik.constants.FareType.HOURLY_RATE;

public class VehicleFeeStratergy implements IFareStratergy {
    private static final Map<VehicleType, Double> HOURLY_RATES = Map.of(
            VehicleType.TRUCK, 10.0,
            VehicleType.BIKE, 20.0,
            VehicleType.CAR, 30.0
    );

    private VehicleService vehicleService;
    private TicketService ticketService;

    public VehicleFeeStratergy() {
        this.vehicleService = new VehicleService();
        this.ticketService = new TicketService();
    }

    @Override
    public Boolean isApplicable(FareType fareType) {
        return HOURLY_RATE.equals(fareType);
    }

    @Override
    public Double getFare(String ticketId) {
        Ticket ticket = ticketService.findById(ticketId);
        Vehicle vehicle = vehicleService.findById(ticket.getVehicleId());

        long startTime = ticket.getEntryTime();
        long endTime = ticket.getExitTime();

        long duration = (endTime - startTime)/(1000 * 60 * 60) + 1;
        return duration * HOURLY_RATES.get(vehicle.getVehicleType());
    }
}
