package pl.edu.agh.to2.kitkats.codinlearner.parser;

import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedCommand;

import java.awt.event.ComponentAdapter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    private HashMap<String, Command> commandMap;

    public CommandParser(HashMap<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public List<List<ParameterizedCommand>> parseCommand(String commandAsString) {
        System.out.println(commandAsString);
        List<List<ParameterizedCommand>> commands = new ArrayList<>();
        List<String> parts = Arrays.asList(commandAsString.split("\\s"));

        for (int i = 0; i < parts.size(); i++) {
            String currentCommand = parts.get(i);

            if (currentCommand.equals("repeat")) {
                List<ParameterizedCommand> lineCommands = new ArrayList<>();
                i++;
                try {
                    Integer repeats = 0;
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
                List<ParameterizedCommand> lineCommands = new ArrayList<>();
                if (commandMap.containsKey(parts.get(i))) {
                    currentCommand = parts.get(i);
                    //check next string
                    if (i + 1 < parts.size() && !commandMap.containsKey(parts.get(i + 1))) {
                        lineCommands.add(parseComplexCommand(currentCommand, parts.get(i + 1)));
                        commands.add(lineCommands);
                        i++;
                    } else {
                        lineCommands.add(parseSimpleCommand(currentCommand));
                        commands.add(lineCommands);
                    }
                } else {
                    commands.add(parseWrongCommand());
                }
            }
        }
        //return if empty
        return commands;
    }

    public List<ParameterizedCommand> parseWrongCommand() {
        List<ParameterizedCommand> parComm = new ArrayList<>();
        parComm.add(new ParameterizedCommand(Command.WRONG, 0));
        return parComm;
    }


    public ParameterizedCommand parseSimpleCommand(String currentCommand) {
        Command com = commandMap.getOrDefault(currentCommand, Command.WRONG);
        ParameterizedCommand parComm = new ParameterizedCommand(com, 0);
        parComm.setDefaultsValues();
        return parComm;
    }

    public ParameterizedCommand parseComplexCommand(String currentCommand, String nextCommand) {
        Command comm = commandMap.getOrDefault(currentCommand, Command.WRONG);
        try {
            Integer arg = Integer.parseInt(nextCommand);
            ParameterizedCommand parComm = new ParameterizedCommand(comm, arg);
            return parComm;
        } catch (NumberFormatException e) {
            ParameterizedCommand parComm = new ParameterizedCommand(Command.WRONG, 0);
            return parComm;
        }
    }
}