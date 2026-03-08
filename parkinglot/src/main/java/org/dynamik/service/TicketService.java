package org.dynamik.service;

import org.dynamik.dao.TicketDao;
import org.dynamik.model.Ticket;

import java.util.List;

public class TicketService {
    private final TicketDao ticketDao;

    public TicketService() {
        this.ticketDao = new TicketDao();
    }

    public Ticket save(Ticket ticket) {
        return ticketDao.save(ticket);
    }

    public Ticket findById(String id) {
        return ticketDao.findById(id);
    }

    public void deleteById(String id) {
        ticketDao.deleteById(id);
    }

    public void update(Ticket ticket) {
        ticketDao.update(ticket);
    }

    public List<Ticket> getAll() {
        return ticketDao.getAll();
    }

    public Ticket findByVehicleId(String vehicleId) {
        return ticketDao.findByVehicleId(vehicleId);
    }

    public List<Ticket> findByParkingSpotId(String parkingSpotId) {
        return ticketDao.findByParkingSpotId(parkingSpotId);
    }

    public List<Ticket> findActiveTickets() {
        return ticketDao.findActiveTickets();
    }

    public Ticket createTicket(String parkingSpotId, String vehicleId) {
        Ticket ticket = new Ticket(parkingSpotId, vehicleId);
        ticket.setEntryTime(System.currentTimeMillis());
        return save(ticket);
    }

    public void closeTicket(Ticket ticket) {
        if (ticket != null && ticket.getExitTime() == null) {
            ticket.setExitTime(System.currentTimeMillis());
            update(ticket);
        }
    }
}
