package pl.edu.agh.to2.kitkats.codinlearner.level;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertFalse;

public class LevelTest {
    private Level level = null;
    private MoveGraph movegraph = null;

    @Before
    public void beforeEachTest(){
        level = Mockito.mock(Level.class);
        movegraph = Mockito.mock(MoveGraph.class);
        List<ParameterizedInstruction> commands = new ArrayList<>();
        Long l = new Long("1");
        level = new Level(movegraph, "", commands, l, l, false);
    }

    @Test
    public void testLevel(){
        boolean isAccomplished = level.getAccomplished();
        assertFalse(isAccomplished);
    }
}
