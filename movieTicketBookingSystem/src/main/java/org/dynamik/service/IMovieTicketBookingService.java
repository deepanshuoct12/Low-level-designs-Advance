package org.dynamik.service;

import org.dynamik.enums.PaymentStratergy;
import org.dynamik.enums.PriceStratergy;
import org.dynamik.model.Seat;
import org.dynamik.model.Show;

import java.util.List;

public interface IMovieTicketBookingService {
   void bookTicket(String userId, Double amount, List<String> seatIds, PaymentStratergy paymentStratergy, PriceStratergy priceStratergy);
   List<Show> searchShowsOfMovie(String movieId); // search movie by title, description, language, genre
   List<Seat> findAvailableSeats(String showId);
   List<Show> findShowsByTheatreId(String theatreId);
   void cancelBooking(String bookingId);
}
