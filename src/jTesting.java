import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class jTesting {

    playBoard gameTest;
    Robot rob;

    @BeforeEach
    public void jTesting() throws IOException, AWTException {
        rob = new Robot();
        gameTest = new playBoard();
    }

    @Test
    public void movingLeft(){
        rob.keyRelease(68);

    }



}
