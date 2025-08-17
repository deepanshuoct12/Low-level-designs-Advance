import org.dynamik.demo.BookingSystemDriver;
import org.testng.annotations.Test;


public class MovieBookingTest {

    BookingSystemDriver bookingSystemDriver = new BookingSystemDriver();

    @Test
    public void testMovieBooking() {
        bookingSystemDriver.runDemo();
    }
}
