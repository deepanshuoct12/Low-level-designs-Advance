package org.dynamik.dao;

import org.dynamik.model.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketDao implements IBaseDao<Ticket, String> {
    private Map<String, Ticket> tickets = new HashMap<>();

    @Override
    public Ticket save(Ticket ticket) {
        return tickets.put(ticket.getId(), ticket);
    }

    @Override
    public Ticket findById(String id) {
        return tickets.get(id);
    }

    @Override
    public void deleteById(String id) {
        tickets.remove(id);
    }

    @Override
    public void update(Ticket ticket) {
        if (tickets.get(ticket.getId()) != null) {
            tickets.put(ticket.getId(), ticket);
        }
    }

    @Override
    public List<Ticket> getAll() {
        return tickets.values().stream().collect(Collectors.toList());
    }

    public Ticket findByVehicleId(String vehicleId) {
        return tickets.values().stream()
                .filter(ticket -> vehicleId.equals(ticket.getVehicleId())).findFirst().orElse(null);
    }

    public List<Ticket> findByParkingSpotId(String parkingSpotId) {
        return tickets.values().stream()
                .filter(ticket -> parkingSpotId.equals(ticket.getParkingSpotId()))
                .collect(Collectors.toList());
    }

    public List<Ticket> findActiveTickets() {
        return tickets.values().stream()
                .filter(ticket -> ticket.getExitTime() == null)
                .collect(Collectors.toList());
    }
}
