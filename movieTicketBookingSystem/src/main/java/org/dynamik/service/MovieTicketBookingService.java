package org.dynamik.service;

import org.dynamik.enums.*;
import org.dynamik.lock.LockManager;
import org.dynamik.model.*;
import org.dynamik.stratergy.IPaymentStratergy;
import org.dynamik.stratergy.IPricingStratergy;
import org.dynamik.stratergy.CreditCardStratergy;
import org.dynamik.stratergy.UPIStratergy;
import org.dynamik.stratergy.WeekdayStratergy;
import org.dynamik.stratergy.WeekendStratergy;

import java.util.*;
import java.util.stream.Collectors;

public class MovieTicketBookingService implements IMovieTicketBookingService {
    private final BookingService bookingService;
    private final ShowService showService;
    private final SeatService seatService;
    private final PaymentService paymentService;
    private final UserService userService;
    private final LockManager lockManager = new LockManager();
    private List<IPaymentStratergy> paymentStratergies = new ArrayList<>();
    private List<IPricingStratergy> priceStratergies = new ArrayList<>();

    public MovieTicketBookingService() {
        this.bookingService = new BookingService();
        this.showService = new ShowService();
        this.seatService = new SeatService();
        this.paymentService = new PaymentService();
        this.userService = new UserService();

        paymentStratergies.add(new CreditCardStratergy());
        paymentStratergies.add(new UPIStratergy());
        priceStratergies.add(new WeekdayStratergy());
        priceStratergies.add(new WeekendStratergy());
    }

    @Override
    public void bookTicket(String userId, Double amount, List<String> seatIds, PaymentStratergy paymentStratergy, PriceStratergy priceStratergy) {
        // 1. Sort seat IDs to prevent deadlocks
        List<String> sortedSeatIds = new ArrayList<>(seatIds);
        Collections.sort(sortedSeatIds);

        try {
            // 2. Lock all seats
            lockManager.lockAll(sortedSeatIds.toArray(new String[0]));

            // 3. Check seat availability
            for (String seatId : sortedSeatIds) {
                Seat seat = seatService.findById(seatId);
                if (seat == null) {
                    throw new IllegalArgumentException("Seat not found: " + seatId);
                }
                if (seat.getState() == SeatState.UNAVAILABLE) {
                    throw new IllegalStateException("Seat already booked: " + seatId);
                }
            }

            // 4. Get show ID (assuming all seats are from same show)
            String showId = getShowIdForSeats(sortedSeatIds);

            // 5. Validate user exists
            User user = userService.findById(userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }

            // 6. Get and validate show
            Show show = showService.findById(showId);
            if (show == null) {
                throw new IllegalStateException("Show not found");
            }

            // 7. Create booking
            Booking booking = createBooking(userId, showId);

            try {
                // 8. Process payment
                processPayment(amount, paymentStratergy, priceStratergy, booking.getId());

                // 9. Update seats
                updateSeats(sortedSeatIds, booking.getId());

                // 10. Confirm booking
                booking.setStatus(BookingStatus.CONFIRMED);
                bookingService.save(booking);

            } catch (Exception e) {
                // 11. Handle payment failure
                booking.setStatus(BookingStatus.FAILED);
                bookingService.save(booking);
                throw new RuntimeException("Payment failed: " + e.getMessage(), e);
            }
        } finally {
            // 12. Unlock all seats
            lockManager.unlockAll(sortedSeatIds.toArray(new String[0]));
        }
    }

    @Override
    public void cancelBooking(String bookingId) {
        // 1. Get and validate booking
        Booking booking = bookingService.findById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return;
        }

        // 2. Get all seats for this booking
        List<Seat> seats = seatService.findByBookingId(bookingId);
        if (seats.isEmpty()) {
            throw new IllegalStateException("No seats found for booking");
        }

        // 3. Sort seat IDs to prevent deadlocks
        List<String> seatIds = seats.stream()
                .map(Seat::getId)
                .sorted()
                .collect(Collectors.toList());

        try {
            // 4. Lock all seats
            lockManager.lockAll(seatIds.toArray(new String[0]));

            // 5. Double check booking status
            booking = bookingService.findById(bookingId);
            if (booking.getStatus() == BookingStatus.CANCELLED) {
                return;
            }

            // 6. Update booking status
            booking.setStatus(BookingStatus.CANCELLED);
            bookingService.save(booking);

            // 7. Release seats
            for (Seat seat : seats) {
                seat.setBookingId(null);
                seat.setState(SeatState.AVAILABLE);
                seatService.save(seat);
            }

            // 8. Process refund if needed
            processRefund(bookingId);

        } finally {
            // 9. Unlock all seats
            lockManager.unlockAll(seatIds.toArray(new String[0]));
        }
    }

    // Helper method to get show ID for seats
    private String getShowIdForSeats(List<String> seatIds) {
        if (seatIds == null || seatIds.isEmpty()) {
            throw new IllegalArgumentException("No seat IDs provided");
        }
        Seat firstSeat = seatService.findById(seatIds.get(0));
        if (firstSeat == null) {
            throw new IllegalArgumentException("First seat not found");
        }
        return firstSeat.getShowId();
    }

    private Booking createBooking(String userId, String showId) {
        Booking booking = new Booking();
        booking.setId(UUID.randomUUID().toString());
        booking.setUserId(userId);
        booking.setShowId(showId);
        booking.setStatus(BookingStatus.PENDING);
        return bookingService.save(booking);
    }

    private void processPayment(Double amount, PaymentStratergy paymentStratergy,
                                PriceStratergy priceStratergy, String bookingId) {
        try {
            // Apply pricing strategy
            IPricingStratergy pricing = priceStratergies.stream()
                    .filter(strategy -> strategy.isApplicable(priceStratergy))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No matching price strategy found"));

            double finalAmount = pricing.calculatePrice(amount);

            // Process payment
            IPaymentStratergy payment = paymentStratergies.stream()
                    .filter(strategy -> strategy.isApplicable(paymentStratergy))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No matching payment strategy found"));

            payment.pay(finalAmount, bookingId);

        } catch (Exception e) {
            throw new RuntimeException("Payment processing failed: " + e.getMessage(), e);
        }
    }

    private void updateSeats(List<String> seatIds, String bookingId) {
        for (String seatId : seatIds) {
            Seat seat = seatService.findById(seatId);
            seat.setBookingId(bookingId);
            seat.setState(SeatState.UNAVAILABLE);
            seatService.save(seat);
        }
    }

    private void processRefund(String bookingId) {
        Payment payment = paymentService.findByBookingId(bookingId);
        if (payment != null && payment.getStatus() == PaymentStatus.SUCCESS) {
            payment.setStatus(PaymentStatus.REFUNDED);
            paymentService.save(payment);
        }
    }

    @Override
    public List<Show> searchShowsOfMovie(String movieId) {
        return showService.findShowsByMovieId(movieId);
    }

    @Override
    public List<Seat> findAvailableSeats(String showId) {
        return seatService.findAvailableSeats(showId);
    }

    @Override
    public List<Show> findShowsByTheatreId(String theatreId) {
        return showService.findShowsByTheatreId(theatreId);
    }
}