package pl.edu.agh.to2.kitkats.codinlearner.parser;

import pl.edu.agh.to2.kitkats.codinlearner.model.Command;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class CommandParser {


    private HashMap<String, Command> commandMap;

    public CommandParser(HashMap<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public List<Command> parseCommand(String commandAsString){
        List<Command> commands = new ArrayList<>();
        commands.add(commandMap.getOrDefault(commandAsString, Command.WRONG));
        return commands;
    }
}
