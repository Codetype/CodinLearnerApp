package pl.edu.agh.to2.kitkats.codinlearner.model;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class RepeatedCommands {

    private List<ParameterizedCommand> commands;
    private final int repeat;

    public void add(Command command, int commandValue) {
        commands.add(new ParameterizedCommand(command, commandValue));
    }

    public List<ParameterizedCommand> getAll() {
        List<ParameterizedCommand> result = new LinkedList<>();
        for (int i = 0; i < repeat; i++) {
            result.addAll(commands);
        }
        return result;
    }

    public RepeatedCommands() {
        commands = new LinkedList<>();
        this.repeat = 1;
    }

    public RepeatedCommands(int repeat) {
        commands = new LinkedList<>();
        this.repeat = repeat;
    }

    public List<ParameterizedCommand> getCommands() {
        return commands;
    }

    public int getRepeat() {
        return repeat;
    }
}
