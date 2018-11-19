package pl.edu.agh.to2.kitkats.codinlearner.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ArenaTest {
    private Arena arena = null;

    @Before
    public void beforeEachTest(){
        arena = Mockito.mock(Arena.class);
        arena = new Arena(600.0f, 400.0f);
    }

    @Test
    public void testArenaWidth() {
        float w = arena.getWidth();

        assertEquals(w, 600,  0.0);
    }

    @Test
    public void testArenaHeight() {
        float h = arena.getHeight();

        assertEquals(h, 400, 0.0);
    }

    @Test
    public void testArenaTurtle() {
        Cursor t1 = arena.getCursor();
        ObjectProperty<Cursor> cursor = new SimpleObjectProperty<Cursor>(new Cursor(600.0f, 400.0f, arena, 50.0f));

        assertNotEquals(cursor, t1);
    }
}
