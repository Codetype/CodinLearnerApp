package pl.edu.agh.to2.kitkats.codinlearner.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TurtleTest {
    @Test
    public void testInitTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);
        Turtle turtle = new Turtle(300.0f, 200.0f, arena, 10.0f);

        double x1 = turtle.getX();
        double y1 = turtle.getY();
        assertEquals(150, x1);
        assertEquals(100, y1);
    }

    @Test
    public void testResetTurtlePosition() {

        Arena arena = new Arena(600.0f, 400.0f);
        Turtle turtle = new Turtle(300.0f, 200.0f, arena, 20.0f);

        turtle.move();
        turtle.turnLeft();
        turtle.move();
        turtle.turnRight();
        turtle.move();
        turtle.reset();

        double x1 = turtle.getX();
        double y1 = turtle.getY();
        assertEquals(300, x1);
        assertEquals(200, y1);
    }


    @Test
    public void testGoForwardTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);
        Turtle turtle = new Turtle(300.0f, 200.0f, arena, 10.0f);

        turtle.move();

        double x1 = turtle.getX();
        double y1 = turtle.getY();
        assertEquals(160, x1);
        assertEquals(100, y1);
    }

    @Test
    public void testGoLeftTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);
        Turtle turtle = new Turtle(300.0f, 200.0f, arena, 20.0f);

        turtle.turnLeft();
        turtle.move();

        double x1 = turtle.getX();
        double y1 = turtle.getY();
        assertEquals(150, x1);
        assertEquals(80, y1);
    }

    @Test
    public void testGoRightTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);
        Turtle turtle = new Turtle(300.0f, 200.0f, arena, 30.0f);

        turtle.turnRight();
        turtle.move();

        double x1 = turtle.getX();
        double y1 = turtle.getY();
        assertEquals(150, x1);
        assertEquals(130, y1);
    }

    @Test
    public void testRotateTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);
        Turtle turtle = new Turtle(300.0f, 200.0f, arena, 10.0f);

        turtle.rotateLeft(60);
        turtle.move();
        turtle.rotateLeft(120);
        turtle.move();
        turtle.rotateLeft(120);
        turtle.move();

        double x1 = turtle.getX();
        double y1 = turtle.getY();
        assertEquals(150, x1);
        assertEquals(100, y1);
    }

}
