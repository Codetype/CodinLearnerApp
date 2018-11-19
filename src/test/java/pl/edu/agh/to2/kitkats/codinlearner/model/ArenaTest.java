package pl.edu.agh.to2.kitkats.codinlearner.model;
;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class ArenaTest {

    @Test
    public void testArenaWidth() {
        Arena arena = new Arena(600.0f, 400.0f);
        float w = arena.getWidth();

        assertEquals(w, 600,  0.0);
    }

    @Test
    public void testArenaHeight() {
        Arena arena = new Arena(600.0f, 400.0f);
        float h = arena.getHeight();

        assertEquals(h, 400, 0.0);
    }

    @Test
    public void testArenaTurtle() {
        Arena arena = new Arena(600.0f, 400.0f);

        Cursor t1 = arena.getTurtle();
        ObjectProperty<Cursor> turtle = new SimpleObjectProperty<Cursor>(new Cursor(600.0f, 400.0f, arena, 50.0f));

        assertNotEquals(turtle, t1);
    }

}
