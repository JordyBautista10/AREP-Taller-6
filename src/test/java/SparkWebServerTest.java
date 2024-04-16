import org.escuelaing.LogService;
import org.escuelaing.SparkWebService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SparkWebServerTest {

    @Test
    public void roundRobin1(){
        SparkWebService.roundRobin("2");
        SparkWebService.roundRobin("3");
        assertEquals(SparkWebService.roundRobin("1"), "http://logservice1:35000/logs?msg=1");
    }

    @Test
    public void roundRobin2(){
        assertEquals(SparkWebService.roundRobin("2"), "http://logservice2:35000/logs?msg=2");
    }

    @Test
    public void roundRobin3(){
        assertEquals(SparkWebService.roundRobin("3"), "http://logservice3:35000/logs?msg=3");
    }

    @Test
    public void portGetCorrect(){
        assertEquals(LogService.getPort(), 35001);
    }
}
