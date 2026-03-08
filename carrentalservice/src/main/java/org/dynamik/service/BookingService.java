package org.dynamik.service;

import org.dynamik.model.Booking;

import java.util.concurrent.ConcurrentHashMap;

public class BookingService {
    private static final ConcurrentHashMap<String, Booking> bookings = new ConcurrentHashMap<>();

    public void save(Booking booking) {
        bookings.put(booking.getId(), booking);
    }

    public Booking findById(String bookingId) {
        return bookings.get(bookingId);
    }
}
