package pl.edu.agh.to2.kitkats.codinlearner.model;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class CursorTest {

    @Test
    public void testInitTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);
        Cursor cursor = new Cursor(300.0f, 200.0f, arena, 10.0f);

        double x1 = cursor.getX();
        double y1 = cursor.getY();
        assertEquals(x1, 150, 0.0);
        assertEquals(100, y1, 0.0);
    }


    @Test
    public void testResetTurtlePosition() {

        Arena arena = new Arena(600.0f, 400.0f);
        Cursor cursor = new Cursor(300.0f, 200.0f, arena, 20.0f);

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
    public void testGoForwardTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);
        Cursor cursor = new Cursor(300.0f, 200.0f, arena, 10.0f);

        cursor.move();

        double x1 = cursor.getX();
        double y1 = cursor.getY();
        assertEquals(160, x1, 0.0);
        assertEquals(100, y1, 0.0);
    }

    @Test
    public void testGoLeftTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);
        Cursor cursor = new Cursor(300.0f, 200.0f, arena, 20.0f);

        cursor.turnLeft();
        cursor.move();

        double x1 = cursor.getX();
        double y1 = cursor.getY();
        assertEquals(150, x1, 0.0);
        assertEquals(80, y1, 0.0);
    }

    @Test
    public void testGoRightTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);
        Cursor cursor = new Cursor(300.0f, 200.0f, arena, 30.0f);

        cursor.turnRight();
        cursor.move();

        double x1 = cursor.getX();
        double y1 = cursor.getY();
        assertEquals(150, x1, 0.0);
        assertEquals(130, y1, 0.0);
    }

    @Test
    public void testRotateTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);
        Cursor cursor = new Cursor(300.0f, 200.0f, arena, 10.0f);

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
