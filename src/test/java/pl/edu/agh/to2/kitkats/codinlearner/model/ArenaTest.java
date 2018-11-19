package pl.edu.agh.to2.kitkats.codinlearner.model;
;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ArenaTest {

    @Test
    public void testArenaWidth() {
        Arena arena = new Arena(600.0f, 400.0f);
        float w = arena.getWidth();

        assertEquals(w, 600);
    }

    @Test
    public void testArenaHeight() {
        Arena arena = new Arena(600.0f, 400.0f);
        float h = arena.getHeight();

        assertEquals(h, 400);
    }

    @Test
    public void testArenaTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);

        Turtle t1 = arena.getTurtle();
        ObjectProperty<Turtle> turtle = new SimpleObjectProperty<Turtle>(new Turtle(600.0f, 400.0f, arena, 50.0f));

        assertNotEquals(turtle, t1);
    }

}
