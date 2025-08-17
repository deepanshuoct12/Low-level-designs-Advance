package org.dynamik.demo;

import org.dynamik.enums.*;
import org.dynamik.model.*;
import org.dynamik.observer.UserObserver;
import org.dynamik.service.*;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.dynamik.enums.SeatType.REGULAR;

public class BookingSystemDriver {
    
    private final UserService userService;
    private final MovieService movieService;
    private final ShowService showService;
    private final SeatService seatService;
    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final TheatreService theatreService;
    private final MovieTicketBookingService bookingSystem;
    
    public BookingSystemDriver() {
        // Initialize services
        this.userService = new UserService();
        this.movieService = new MovieService();
        this.showService = new ShowService();
        this.seatService = new SeatService();
        this.bookingService = new BookingService();
        this.paymentService = new PaymentService();
        this.theatreService = new TheatreService();
        
        // Initialize booking system
        this.bookingSystem = new MovieTicketBookingService();
    }
    
    public void runDemo() {
        System.out.println("=== Starting Movie Ticket Booking System Demo ===\n");
        
        // 1. Create and add users
        User user1 = createUser("U001", "John Doe", "john@example.com", UserRole.CUSTOMER);
        User user2 = createUser("U002", "Jane Smith", "jane@example.com", UserRole.CUSTOMER);
        User admin = createUser("U003", "Admin User", "admin@example.com", UserRole.ADMIN);
        
        // 2. Create and add movies
        Movie movie1 = createMovie("M001", "Inception", "A mind-bending thriller", "action","english", 150l);
        Movie movie2 = createMovie("M002", "The Shawshank Redemption", "Two imprisoned men bond over a number of years...", "romantic","hindi", 20l);
        
        // 3. Create theatres
        Theatre theatre1 = createTheatre("TH001", "PVR Cinemas", "New Delhi");
        Theatre theatre2 = createTheatre("TH002", "INOX Cinemas", "Mumbai");
        
        // 4. Create shows for movies
        Show show1 = createShow("SH001", movie1.getId(), theatre1.getId(), 
                LocalDateTime.now().plusDays(1).withHour(18).withMinute(0), // Tomorrow at 6 PM
                150);
                
        Show show2 = createShow("SH002", movie2.getId(), theatre2.getId(),
                LocalDateTime.now().plusDays(2).withHour(20).withMinute(30), // Day after tomorrow at 8:30 PM
                120);
        
        // 5. Create seats for shows
        createSeatsForShow(show1.getId(), "AUDI_1", 5, 10); // 5 rows x 10 seats
        createSeatsForShow(show2.getId(), "AUDI_2", 4, 10);  // 4 rows x 10 seats
        
        // 6. Register users as observers for movie releases
        Movie newMovie = createMovie("M003", "The Matrix", 
            "A computer hacker learns about the true nature of reality", 
            "Sci-Fi", "English", 136);
            
        newMovie.addObserver(new UserObserver(user1));
        newMovie.addObserver(new UserObserver(admin));
        movie1.addObserver(new UserObserver(user1));
        movie1.addObserver(new UserObserver(user2));
        
        // 7. Simulate a booking for user1
        simulateBooking(user1, show1, Arrays.asList("AUDI_1_A1", "AUDI_1_A2", "AUDI_1_A3"));
        
        // 8. Simulate a booking for user2 (will fail if trying to book same seats)
        simulateBooking(user2, show1, Arrays.asList("AUDI_1_A1", "AUDI_1_A4"));
        
        // 9. Show available seats for show1
        showAvailableSeats(show1.getId());
        
        // 10. Simulate a new movie release (will notify all observers)
        System.out.println("\n=== New Movie Release Notification ===");
        newMovie.notifyObservers(newMovie);
        
        System.out.println("\n=== Demo Completed Successfully ===");
    }
    
    private User createUser(String id, String name, String email, UserRole role) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        userService.save(user);
        System.out.println("Created " + role.toString().toLowerCase() + ": " + name);
        return user;
    }
    
    private Movie createMovie(String id, String title, String description, String genre, String language, long duration) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setGenre(genre);
        movie.setLanguage(language);
        movie.setDuration(duration);
        movieService.save(movie);
        System.out.println("Created movie: " + title);
        return movie;
    }
    
    private Theatre createTheatre(String id, String name, String location) {
        Theatre theatre = new Theatre();
        theatre.setId(id);
        theatre.setName(name);
        theatre.setLocation(location);
        theatreService.save(theatre);
        System.out.println("Created theatre: " + name + " at " + location);
        return theatre;
    }
    
    private Show createShow(String id, String movieId, String theatreId, 
                           LocalDateTime startTime, long durationInMinutes) {
        Show show = new Show();
        show.setId(id);
        show.setMovieId(movieId);
        show.setTheatreId(theatreId);
        show.setStartTime(startTime);
        show.setEndTime(startTime.plusMinutes(durationInMinutes));
        showService.save(show);
        System.out.println("Created show " + id + " at " + startTime);
        return show;
    }
    
    private void createSeatsForShow(String showId, String screenId, int rows, int seatsPerRow) {
        for (int row = 0; row < rows; row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                String seatId = String.format("%s_%c%d", screenId, 'A' + row, seatNum);
                Seat seat = new Seat();
                seat.setId(seatId);
                seat.setShowId(showId);
                seat.setType(REGULAR);
                seat.setState(SeatState.AVAILABLE);
                seat.setBookingId(null);
                seatService.save(seat);
            }
        }
        System.out.println("Created " + (rows * seatsPerRow) + " seats for show " + showId + " in " + screenId);
    }
    
    private void simulateBooking(User user, Show show, List<String> seatNumbers) {
        try {
            System.out.println("\n=== Starting Booking for " + user.getName() + " ===");
            System.out.println("Movie: " + movieService.findById(show.getMovieId()).getTitle());
            System.out.println("Show Time: " + show.getStartTime());
            System.out.println("Seats: " + String.join(", ", seatNumbers));
            
            // Calculate amount (simple pricing for demo)
            double amount = seatNumbers.size() * 250.0; // 250 per seat
            
            // Make booking
            bookingSystem.bookTicket(
                user.getId(),
                amount,
                seatNumbers,
                PaymentStratergy.CREDIT_CARD,
                show.getStartTime().getDayOfWeek().getValue() < 6 ? 
                    PriceStratergy.WEEKDAY : PriceStratergy.WEEKEND
            );
            
            System.out.println("✅ Booking successful!");
            
        } catch (Exception e) {
            System.out.println("❌ Booking failed: " + e.getMessage());
        }
    }
    
    private void showAvailableSeats(String showId) {
        List<Seat> availableSeats = seatService.findAvailableSeats(showId);
        System.out.println("\n=== Available Seats for Show " + showId + " ===");
        System.out.println("Total available: " + availableSeats.size());
        
        // Print seat map
        System.out.println("\nSeat Map (X = Booked, O = Available):");
        // Group seats by row
        availableSeats.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                s -> s.getId().replaceAll("\\d", ""), // Extract row letter
                java.util.TreeMap::new,
                java.util.stream.Collectors.toList()
            ))
            .forEach((row, seats) -> {
                System.out.print(row + " | ");
                for (Seat seat : seats) {
                    System.out.print(seat.getState() == SeatState.AVAILABLE ? "O" : "X");
                    System.out.print(" ");
                }
                System.out.println();
            });
    }
}
