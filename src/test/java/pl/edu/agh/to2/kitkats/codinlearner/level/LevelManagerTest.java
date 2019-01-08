package pl.edu.agh.to2.kitkats.codinlearner.level;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LevelManagerTest {
    private LevelManager levelManager = null;
    private Level level = null;
    private MoveGraph movegraph = null;

    @Before
    public void beforeEachTest(){
        levelManager = Mockito.mock(LevelManager.class);
        level = Mockito.mock(Level.class);
        movegraph = Mockito.mock(MoveGraph.class);
        List<ParameterizedInstruction> commands = new ArrayList<>();
        Long l = new Long("1");
        level = new Level(movegraph, "", commands, l, l, false);
        levelManager = new LevelManager(0);
        levelManager.addLevel(level);
    }

    @Test
    public void testCurrentLevel(){
        Level currLevel = levelManager.getCurrentLevel();

        assertEquals(currLevel, level);
    }
    @Test
    public void testAccommplishment() {
        levelManager.getCurrentLevel().setAccomplished(true);

        assertTrue(levelManager.getCurrentLevel().getAccomplished());
    }

    @Test
    public void testIsLevelExists() {
        assertTrue(levelManager.currentLevelExists());
    }

    @Test
    public void testNextLevel(){
        Long l2 = new Long("2");
        List<ParameterizedInstruction> commandList = new ArrayList<>();
        Level newLevel = new Level(movegraph, "", commandList, l2, l2, false);

        levelManager.getCurrentLevel().setAccomplished(true);
        levelManager.addLevel(newLevel);
        levelManager.nextLevel();
        assertEquals(newLevel, levelManager.getCurrentLevel());
    }
}
