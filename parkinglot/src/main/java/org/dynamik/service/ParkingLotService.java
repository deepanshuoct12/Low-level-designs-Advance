package org.dynamik.service;

import org.dynamik.constants.FareType;
import org.dynamik.model.ParkingSpot;
import org.dynamik.model.Ticket;
import org.dynamik.model.Vehicle;
import org.dynamik.constants.BookingStatus;
import org.dynamik.stratergy.ticket.HourleFeeStratergy;
import org.dynamik.stratergy.ticket.IFareStratergy;
import org.dynamik.stratergy.ticket.VehicleFeeStratergy;
import org.dynamik.stratergy.vehicle.BikeParkingStratergy;
import org.dynamik.stratergy.vehicle.CarParkingStratergy;
import org.dynamik.stratergy.vehicle.IParkingSpotStratergy;
import org.dynamik.stratergy.vehicle.TruckParkingStratergy;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class ParkingLotService implements IParkingLotService {
    private static ParkingLotService instance;
    private final ParkingSpotService          parkingSpotService;
    private final List<IParkingSpotStratergy> strategies;
    private final List<IFareStratergy>        fareStrategies;
    private final TicketService               ticketService;

    private ParkingLotService() {
        this.parkingSpotService = new ParkingSpotService();
        this.strategies = new ArrayList<>();
        fareStrategies = new ArrayList<>();
        strategies.add(new BikeParkingStratergy());
        strategies.add(new CarParkingStratergy());
        strategies.add(new TruckParkingStratergy());
        fareStrategies.add(new VehicleFeeStratergy());
        fareStrategies.add(new HourleFeeStratergy());
        ticketService = new TicketService();
    }

    public static synchronized ParkingLotService getInstance() {
        if (instance == null) {
            instance = new ParkingLotService();
        }
        return instance;
    }

    @Override
    public List<ParkingSpot> getAvailableParkingSpots(Vehicle vehicle) {
        for (IParkingSpotStratergy strategy : strategies) {
            if (strategy.isApplicable(vehicle.getVehicleType())) {
                return strategy.getParkingSpot();
            }
        }

        return new ArrayList<>();
    }

    @Override
    public synchronized Ticket bookParkingSpot(ParkingSpot parkingSpot, String vehicleId, FareType fareType) {
        if (parkingSpot != null && BookingStatus.AVAILABLE.equals(parkingSpot.getBookingStatus())) {
            parkingSpot.setBookingStatus(BookingStatus.UN_AVAILABLE);
            Ticket ticket = new Ticket(parkingSpot.getId(), vehicleId);
            ticket.setFareType(fareType);
            ticketService.save(ticket);
            parkingSpotService.update(parkingSpot);
            return ticket;
        }

        return null;
    }

    @Override
    public synchronized Double releaseParkingSpot(String ticketId) {
        Ticket ticket = ticketService.findById(ticketId);
        ParkingSpot parkingSpot = parkingSpotService.findById(ticket.getParkingSpotId());
        if (parkingSpot != null && BookingStatus.UN_AVAILABLE.equals(parkingSpot.getBookingStatus())) {
            parkingSpot.setBookingStatus(BookingStatus.AVAILABLE);
            ticket.setExitTime(new Date().getTime());
            FareType fareType = ticket.getFareType();
            Double price = fareStrategies.stream().filter(iFareStratergy -> iFareStratergy.isApplicable(fareType)).findFirst().get().getFare(ticketId);
            parkingSpotService.update(parkingSpot);
            ticketService.deleteById(ticketId);
            return price;
        }

        return null;
    }
}
