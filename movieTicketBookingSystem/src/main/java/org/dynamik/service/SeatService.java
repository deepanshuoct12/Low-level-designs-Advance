package org.dynamik.service;

import org.dynamik.dao.SeatDao;
import org.dynamik.enums.SeatState;
import org.dynamik.enums.SeatType;
import org.dynamik.model.Seat;

import java.util.List;
import java.util.stream.Collectors;

public class SeatService implements BaseService<Seat, String> {
    private final SeatDao seatDao = new SeatDao();


    @Override
    public Seat save(Seat seat) {
        seatDao.save(seat);
        return seat;
    }

    @Override
    public Seat findById(String id) {
        return seatDao.findById(id);
    }

    @Override
    public List<Seat> findAll() {
        return seatDao.findAll();
    }

    @Override
    public void delete(String id) {
        seatDao.delete(id);
    }

    public List<Seat> findAvailableSeats(String showId) {
        return seatDao.findByShowId(showId).stream()
                .filter(seat -> seat.getState() == SeatState.AVAILABLE)
                .collect(Collectors.toList());
    }

    public List<Seat> findSeatsByType(SeatType type) {
        return seatDao.findAll().stream()
                .filter(seat -> seat.getType() == type)
                .collect(Collectors.toList());
    }

    public boolean isSeatAvailable(String seatId) {
        Seat seat = findById(seatId);
        return seat != null && seat.getState() == SeatState.AVAILABLE;
    }

    public List<Seat> findByBookingId(String bookingId) {
        return seatDao.findByBookingId(bookingId);
    }
}
