//package org.dynamik.dao;
//
//import org.dynamik.model.Ticket;
//import org.dynamik.enums.TicketType;
//
//import java.util.*;
//
//public class TicketDao {
//    private static final Map<String, Ticket> tickets = new HashMap<>();
//
//    public void save(Ticket ticket) {
//        if (ticket.getId() == null) {
//            ticket.setId(UUID.randomUUID().toString());
//        }
//        tickets.put(ticket.getId(), ticket);
//    }
//
//    public Ticket findById(String id) {
//        return tickets.get(id);
//    }
//
//    public List<Ticket> findAll() {
//        return new ArrayList<>(tickets.values());
//    }
//
//    public void delete(String id) {
//        tickets.remove(id);
//    }
//
//    public List<Ticket> findByShowId(String showId) {
//        return tickets.values().stream()
//                .filter(ticket -> ticket.getShowId().equals(showId))
//                .toList();
//    }
//
//    public List<Ticket> findByType(TicketType type) {
//        return tickets.values().stream()
//                .filter(ticket -> ticket.getType() == type)
//                .toList();
//    }
//
//    public List<Ticket> findByUserId(String userId) {
//        return tickets.values().stream()
//                .filter(ticket -> ticket.getUserId().equals(userId))
//                .toList();
//    }
//}
