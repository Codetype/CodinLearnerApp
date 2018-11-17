package pl.edu.agh.to2.kitkats.codinlearner.parser;

import pl.edu.agh.to2.kitkats.codinlearner.model.Command;

import java.util.Arrays;
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
        List<String> parts = Arrays.asList(commandAsString.split(" "));
        if(parts.size() == 1)
            commands.add(commandMap.getOrDefault(commandAsString, Command.WRONG));
        else if(parts.size() == 2) {
            //System.out.println(parts.get(0));
            for (int i = 0; i < Integer.valueOf(parts.get(1)); i++) {
                commands.add(commandMap.getOrDefault(parts.get(0), Command.WRONG));
            }
        }

        return commands;
    }
}
