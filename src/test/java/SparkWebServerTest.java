import org.escuelaing.LogService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SparkWebServerTest {

    static LogService server ;

    @Test
    public void palindromeTrueCase(){
        assertTrue(LogService.palindromo("arenera"));
    }

    @Test
    public void portGetCorrect(){
        assertEquals(LogService.getPort(), 35001);
    }
}
