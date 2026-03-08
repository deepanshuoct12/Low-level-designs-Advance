package org.dynamik.stratergy.ticket;

import org.dynamik.constants.FareType;
import org.dynamik.model.Ticket;
import org.dynamik.service.TicketService;

import static org.dynamik.constants.FareType.VEHICLE_RATE;

public class HourleFeeStratergy implements IFareStratergy {
    private TicketService ticketService;
    private final Double RATE = 10.00;

    public HourleFeeStratergy() {
        this.ticketService = new TicketService();
    }

    @Override
    public Boolean isApplicable(FareType fareType) {
        return VEHICLE_RATE.equals(fareType);
    }

    @Override
    public Double getFare(String ticketId) {
        Ticket ticket = ticketService.findById(ticketId);
        long startTime = ticket.getEntryTime();
        long endTime = ticket.getExitTime();

        long duration = (endTime - startTime)/(1000 * 60 * 60) + 1;
        return duration * RATE;
    }
}
