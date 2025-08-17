import org.dynamik.demo.AuctionDriver;
import org.testng.annotations.Test;

public class AuctionTest {


    private AuctionDriver auctionDriver = new AuctionDriver();

    @Test
    public void testAuction() {
        auctionDriver.runDemo();
    }
}
