package org.dynamik.dao;

import org.dynamik.model.Seat;
import org.dynamik.enums.SeatState;
import org.dynamik.enums.SeatType;

import java.util.*;

public class SeatDao {
    private static final Map<String, Seat> seats = new HashMap<>();

    public void save(Seat seat) {
        if (seat.getId() == null) {
            seat.setId(UUID.randomUUID().toString());
        }
        seats.put(seat.getId(), seat);
    }

    public Seat findById(String id) {
        return seats.get(id);
    }

    public List<Seat> findAll() {
        return new ArrayList<>(seats.values());
    }

    public void delete(String id) {
        seats.remove(id);
    }

    public List<Seat> findByShowId(String showId) {
        return seats.values().stream()
                .filter(seat -> seat.getShowId().equals(showId))
                .toList();
    }

    public List<Seat> findByState(SeatState state) {
        return seats.values().stream()
                .filter(seat -> seat.getState() == state)
                .toList();
    }

    public List<Seat> findByType(SeatType type) {
        return seats.values().stream()
                .filter(seat -> seat.getType() == type)
                .toList();
    }

    public List<Seat> findByBookingId(String bookingId) {
        return seats.values().stream()
                .filter(seat -> bookingId.equals(seat.getBookingId()))
                .toList();
    }
}
