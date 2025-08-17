package org.dynamik.service;

import org.dynamik.dao.BookingDao;
import org.dynamik.dao.SeatDao;
import org.dynamik.dao.ShowDao;
import org.dynamik.enums.BookingStatus;
import org.dynamik.model.Booking;
import org.dynamik.model.Seat;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingService implements BaseService<Booking, String> {
    private final BookingDao bookingDao;
    private final ShowDao showDao;
    private final SeatDao seatDao;
    private final PaymentService paymentService;

    public BookingService() {
        this.bookingDao = new BookingDao();
        this.showDao = new ShowDao();
        this.seatDao = new SeatDao();
        this.paymentService = new PaymentService();
    }

    @Override
    public Booking save(Booking booking) {
        if (booking.getId() == null) {
            booking.setId(UUID.randomUUID().toString());
        }
        bookingDao.save(booking);
        return booking;
    }

    @Override
    public Booking findById(String id) {
        return bookingDao.findById(id);
    }

    @Override
    public List<Booking> findAll() {
        return bookingDao.findAll();
    }

    @Override
    public void delete(String id) {
        bookingDao.delete(id);
    }

    public List<Booking> findByUserId(String userId) {
        return bookingDao.findByUserId(userId);
    }

    public List<Booking> findByShowId(String showId) {
        return bookingDao.findByShowId(showId);
    }

//    public Booking createBooking(String userId, String showId, List<String> seatIds, IPricingStratergy pricingStrategy) {
//        // 1. Validate show exists and is in the future
//        Show show = showDao.findById(showId);
//        if (show == null) {
//            throw new IllegalArgumentException("Show not found");
//        }
//
//        // 2. Check if seats are available
//        List<Seat> seats = seatDao.findByIds(seatIds);
//        if (seats.isEmpty() || seats.size() != seatIds.size()) {
//            throw new IllegalArgumentException("One or more seats not found");
//        }
//
//        if (seats.stream().anyMatch(seat -> seat.getBookingId() != null)) {
//            throw new IllegalStateException("One or more seats are already booked");
//        }
//
//        // 3. Calculate total price
//        double basePrice = seats.stream()
//                .mapToDouble(Seat::getPrice)
//                .sum();
//
//        // 4. Apply pricing strategy
//        double finalPrice = pricingStrategy.calculatePrice(basePrice);
//
//        // 5. Create and save booking
//        Booking booking = new Booking();
//        booking.setId(UUID.randomUUID().toString());
//        booking.setUserId(userId);
//        booking.setShowId(showId);
//        booking.setStatus(BookingStatus.CREATED);
//        save(booking);
//
//        // 6. Update seats with booking reference
//        seats.forEach(seat -> {
//            seat.setBookingId(booking.getId());
//            seatDao.save(seat);
//        });
//
//        return booking;
//    }

//    public void confirmBooking(String bookingId, IPricingStratergy pricingStrategy) {
//        Booking booking = findById(bookingId);
//        if (booking == null) {
//            throw new IllegalArgumentException("Booking not found");
//        }
//
//        if (booking.getStatus() != BookingStatus.CREATED) {
//            throw new IllegalStateException("Invalid booking status for confirmation");
//        }
//
//        // Update booking status
//        booking.setStatus(BookingStatus.CONFIRMED);
//        save(booking);
//    }

//    public void cancelBooking(String bookingId) {
//        Booking booking = findById(bookingId);
//        if (booking == null) {
//            throw new IllegalArgumentException("Booking not found");
//        }
//
//        if (booking.getStatus() == BookingStatus.CANCELLED) {
//            return; // Already cancelled
//        }
//
//        // Release seats
//        List<Seat> seats = seatDao.findByBookingId(bookingId);
//        seats.forEach(seat -> {
//            seat.setBookingId(null);
//            seatDao.save(seat);
//        });
//
//        // Update booking status
//        booking.setStatus(BookingStatus.CANCELLED);
//        save(booking);
//    }

    public List<Seat> getBookedSeats(String showId) {
        return seatDao.findByShowId(showId).stream()
                .filter(seat -> seat.getBookingId() != null)
                .collect(Collectors.toList());
    }

    public List<Seat> getAvailableSeats(String showId) {
        return seatDao.findByShowId(showId).stream()
                .filter(seat -> seat.getBookingId() == null)
                .collect(Collectors.toList());
    }
}
