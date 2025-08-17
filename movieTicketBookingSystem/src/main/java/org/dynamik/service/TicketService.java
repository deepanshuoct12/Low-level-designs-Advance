//package org.dynamik.service;
//
//import org.dynamik.dao.TicketDao;
//import org.dynamik.enums.TicketType;
//import org.dynamik.model.Ticket;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class TicketService implements BaseService<Ticket, String> {
//    private final TicketDao ticketDao = new TicketDao();
//
//    @Override
//    public Ticket save(Ticket ticket) {
//        ticketDao.save(ticket);
//        return ticket;
//    }
//
//    @Override
//    public Ticket findById(String id) {
//        return ticketDao.findById(id);
//    }
//
//    @Override
//    public List<Ticket> findAll() {
//        return ticketDao.findAll();
//    }
//
//    @Override
//    public void delete(String id) {
//        ticketDao.delete(id);
//    }
//
//    public List<Ticket> findTicketsByUserId(String userId) {
//        return ticketDao.findByUserId(userId);
//    }
//
//    public List<Ticket> findTicketsByShowId(String showId) {
//        return ticketDao.findByShowId(showId);
//    }
//
//
////    public List<Ticket> findTicketsByType(TicketType type) {
////        return ticketDao.findAll().stream()
////                .filter(ticket -> ticket.getType() == type)
////                .collect(Collectors.toList());
////    }
//}
