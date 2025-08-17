package org.dynamik.dao;

import org.dynamik.model.Booking;

import java.util.*;

public class BookingDao {
    private static final Map<String, Booking> bookings = new HashMap<>();

    public void save(Booking booking) {
        if (booking.getId() == null) {
            booking.setId(UUID.randomUUID().toString());
        }
        bookings.put(booking.getId(), booking);
    }

    public Booking findById(String id) {
        return bookings.get(id);
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookings.values());
    }

    public void delete(String id) {
        bookings.remove(id);
    }

    public List<Booking> findByUserId(String userId) {
        return bookings.values().stream()
                .filter(booking -> booking.getUserId().equals(userId))
                .toList();
    }

    public List<Booking> findByShowId(String showId) {
        return bookings.values().stream()
                .filter(booking -> booking.getShowId().equals(showId))
                .toList();
    }
}
