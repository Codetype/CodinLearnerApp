package pl.edu.agh.to2.kitkats.codinlearner.parser;

import pl.edu.agh.to2.kitkats.codinlearner.model.Instruction;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class CommandParser {

    private HashMap<String, Instruction> commandMap;

    public static final String GO = "go";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String REPEAT = "repeat";
    public static final String EMPTY = "";

    public CommandParser(HashMap<String, Instruction> commandMap) {
        this.commandMap = commandMap;
    }

    public List<ParameterizedInstruction> parseCommand(String commandAsString) {
        List<ParameterizedInstruction> commands = new ArrayList<>();
        List<String> parts = Arrays.asList(commandAsString.split("\\s"));

        for (int i = 0; i < parts.size(); i++) {
            String currentCommand = parts.get(i);

            if (currentCommand.equals(REPEAT)) {
                i++;
                try {
                    int repeats = 0;
                    if (i < parts.size()) {
                        repeats = Integer.parseInt(parts.get(i));
                        i++;
                    }
                    String loopContent = commandAsString.substring(commandAsString.indexOf("[") + 1, commandAsString.lastIndexOf("]"));
                    for (int r = 0; r < repeats; r++) {
                        commands.addAll(parseCommand(loopContent));
                    }
                    i = commandAsString.indexOf("]");
                } catch (NumberFormatException e) {
                    commands.add(parseWrongCommand());
                    return commands;
                }
            } else {
                if (commandMap.containsKey(parts.get(i))) {
                    currentCommand = parts.get(i);
                    //check next string
                    if (i + 1 < parts.size() && !commandMap.containsKey(parts.get(i + 1))) {
                        commands.add(parseComplexCommand(currentCommand, parts.get(i + 1)));

                        i++;
                    } else {
                        commands.add(parseSimpleCommand(currentCommand));
                    }
                } else {
                    commands.add(parseWrongCommand());
                }
            }
        }
        return commands;
    }

    private ParameterizedInstruction parseWrongCommand() {
        return (new ParameterizedInstruction(Instruction.WRONG));
    }


    private ParameterizedInstruction parseSimpleCommand(String currentCommand) {
        Instruction instruction = commandMap.getOrDefault(currentCommand, Instruction.WRONG);
        return new ParameterizedInstruction(instruction);
    }

    private ParameterizedInstruction parseComplexCommand(String currentCommand, String nextCommand) {
        Instruction instruction = commandMap.getOrDefault(currentCommand, Instruction.WRONG);
        try {
            int parameter = Integer.parseInt(nextCommand);
            return new ParameterizedInstruction(instruction, parameter);
        } catch (NumberFormatException e) {
            return new ParameterizedInstruction(Instruction.WRONG);
        }
    }
}