package pl.edu.agh.to2.kitkats.codinlearner.model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class CursorTest {
    private Arena arena = null;
    private Cursor cursor = null;

    @Before
    public void beforeEachTest(){
        arena = Mockito.mock(Arena.class);
        arena = new Arena(600.0f, 400.0f,21.0f, 8.0f);
        cursor = Mockito.mock(Cursor.class);
        cursor = new Cursor(300.0f, 200.0f,21.0f, 8.0f , arena, 10.0f);
    }

    @Test
    public void testInitCursor() {
        double x1 = cursor.getX();
        double y1 = cursor.getY();
        assertEquals(x1, 150, 0.0);
        assertEquals(100, y1, 0.0);
    }


    @Test
    public void testResetCursorPosition() {
        cursor.move();
        cursor.turnLeft();
        cursor.move();
        cursor.turnRight();
        cursor.move();
        cursor.reset();

        double x1 = cursor.getX();
        double y1 = cursor.getY();
        assertEquals(300, x1, 0.0);
        assertEquals(200, y1, 0.0);
    }


    @Test
    public void testGoForwardCursor() {
        cursor.move();

        double x1 = cursor.getX();
        double y1 = cursor.getY();
        assertEquals(160, x1, 0.0);
        assertEquals(100, y1, 0.0);
    }

    @Test
    public void testGoLeftCursor() {
        cursor.turnLeft();
        cursor.move();

        double x1 = cursor.getX();
        double y1 = cursor.getY();
        assertEquals(150, x1, 0.0);
        assertEquals(90, y1, 0.0);
    }

    @Test
    public void testGoRightCursor() {
        cursor.turnRight();
        cursor.move();

        double x1 = cursor.getX();
        double y1 = cursor.getY();
        assertEquals(150, x1, 0.0);
        assertEquals(110, y1, 0.0);
    }

    @Test
    public void testRotateCursor() {
        cursor.rotateLeft(60);
        cursor.move();
        cursor.rotateLeft(120);
        cursor.move();
        cursor.rotateLeft(120);
        cursor.move();

        double x1 = cursor.getX();
        double y1 = cursor.getY();
        assertEquals(150, x1, 0.0);
        assertEquals(100, y1, 0.0);
    }
}
