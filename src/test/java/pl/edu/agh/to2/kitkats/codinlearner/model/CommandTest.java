package pl.edu.agh.to2.kitkats.codinlearner.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandTest {
//    @Test
//    public void testGetters() {
//        Command command = Command.FORWARD;
//        command.setValue(3);
//        int v1 = command.getValue();
//
//        assertEquals(3, v1, 0.0);
//    }

    @Test
    public void testOpositeCommand() {
        Command command = Command.LEFT.oppositeCommand();

        assertEquals(Command.RIGHT, command);
    }
}
