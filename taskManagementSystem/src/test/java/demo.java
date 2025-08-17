import org.example.demo.TaskManageMentDriver;
import org.example.service.ITaskManagementService;
import org.example.service.TaskManagementServiceImpl;
import org.testng.annotations.Test;

public class demo {

   private TaskManageMentDriver taskManageMentDriver = new TaskManageMentDriver();

    @Test
    public void test() {
       taskManageMentDriver.runDemo();
    }
}
