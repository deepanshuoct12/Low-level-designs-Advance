import org.dynamik.demo.StockSystemDriver;
import org.testng.annotations.Test;

public class StockDemoTest {
    private StockSystemDriver driver = new StockSystemDriver();

    @Test
    public void runDemo() {
        driver.runDemo();
    }
}
