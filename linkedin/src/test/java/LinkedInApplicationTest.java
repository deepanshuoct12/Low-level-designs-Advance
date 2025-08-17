import org.dynamik.demo.LinkedInApplicationDriver;
import org.testng.annotations.Test;

public class LinkedInApplicationTest {

    private LinkedInApplicationDriver linkedInApplicationDriver = new LinkedInApplicationDriver();

    @Test
    public void test() {
        linkedInApplicationDriver.runDemo();
    }
}
