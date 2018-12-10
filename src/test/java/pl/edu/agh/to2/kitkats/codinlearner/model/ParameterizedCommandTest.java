package pl.edu.agh.to2.kitkats.codinlearner.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class ParameterizedCommandTest {
    private ParameterizedCommand parameterizedCommand = null;

    @Before
    public void beforeEachTest(){
        parameterizedCommand = Mockito.mock(ParameterizedCommand.class);
        parameterizedCommand = new ParameterizedCommand(Command.WRONG, 0);
    }

    @Test
    public void gettersTest(){
        parameterizedCommand.setCommand(Command.FORWARD);
        parameterizedCommand.setParameter(2);

        Command com = parameterizedCommand.getCommand();
        int param = parameterizedCommand.getParameter();

        assertEquals(Command.FORWARD, com);
        assertEquals(2, param);
    }
}
