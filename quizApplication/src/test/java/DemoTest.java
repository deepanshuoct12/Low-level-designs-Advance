import org.company.demo.QuizDriver;
import org.testng.annotations.Test;

public class DemoTest {
    private QuizDriver quizDriver = new QuizDriver();

    @Test
    public void test() {
       quizDriver.demo();
    }
}
