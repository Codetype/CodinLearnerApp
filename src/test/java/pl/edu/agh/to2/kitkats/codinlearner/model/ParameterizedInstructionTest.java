package pl.edu.agh.to2.kitkats.codinlearner.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class ParameterizedInstructionTest {
    private ParameterizedInstruction parameterizedInstruction = null;

    @Before
    public void beforeEachTest(){
        parameterizedInstruction = Mockito.mock(ParameterizedInstruction.class);
//        parameterizedCommand = new ParameterizedCommand(Command.WRONG, 0);
    }

    @Test
    public void gettersTest(){
//        parameterizedCommand.setCommand(Command.FORWARD);
//        parameterizedCommand.setParameter(2);
        parameterizedInstruction = new ParameterizedInstruction(Instruction.FORWARD, 2);

        Instruction com = parameterizedInstruction.getInstruction();
        int param = parameterizedInstruction.getParameter();

        assertEquals(Instruction.FORWARD, com);
        assertEquals(2, param);
    }
}
