import org.dynamik.demo.PubSubDriver;
import org.testng.annotations.Test;

public class pubSubDemoTest {
    private PubSubDriver pubSubDriver = new PubSubDriver();

    @Test
    public void testPubSubDemo() {
        pubSubDriver.runDemo();
    }

}
