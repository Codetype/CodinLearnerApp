package pl.edu.agh.to2.kitkats.codinlearner.parser;

import pl.edu.agh.to2.kitkats.codinlearner.model.Command;

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

    public List<List<Command>> parseCommand(String commandAsString){
        List<List<Command>> commands = new ArrayList<>();
        List<String> parts = Arrays.asList(commandAsString.split(" "));

        for(int i=0; i<parts.size(); i++){
            String currentCommand = parts.get(i);

            if(currentCommand.equals("repeat")){
                List<Command> lineCommands = new ArrayList<>();
                i++;
                try {
                    Integer repeats = 0;
                    if(i < parts.size()){ repeats = Integer.parseInt(parts.get(i)); i++; }
                    String loopContent = commandAsString.substring(commandAsString.indexOf("[")+1,commandAsString.indexOf("]"));
                    for(int r=0; r<repeats; r++) {
                        commands.addAll(parseLineCommand(loopContent));
                    }
                    i = commandAsString.indexOf("]");
                } catch(NumberFormatException e){
                    lineCommands.add(Command.WRONG);
                    commands.add(lineCommands);
                    //return if we have loop
                    return commands;
                }
            }
            else{
                commands.addAll(parseLineCommand(commandAsString));
                return commands;
            }

        }
        //return if empty
        return commands;
    }

    public Command parseSimpleCommand(String currentCommand){
        return commandMap.getOrDefault(currentCommand, Command.WRONG);
    }

    public Command parseComplexCommand(String currentCommand, String nextCommand){
        Command comm = commandMap.getOrDefault(currentCommand, Command.WRONG);
        try {
            Integer arg = Integer.parseInt(nextCommand);
            comm.setValue(arg);
        } catch(NumberFormatException e){
            return Command.WRONG;
        }

        return comm;
    }

    public List<List<Command>> parseLineCommand(String commandAsString){
        List<List<Command>> commands = new ArrayList<>();
        List<String> parts = Arrays.asList(commandAsString.split(" "));


        for(int i=0; i<parts.size(); i++){
            List<Command> lineCommands = new ArrayList<>();
            if(commandMap.containsKey(parts.get(i))){
                String currentCommand = parts.get(i);
                Command comm;
                //check next string
                if(i+1 < parts.size() && !commandMap.containsKey(parts.get(i+1))){
                    comm = parseComplexCommand(currentCommand, parts.get(i+1));
                    lineCommands.add(comm);
                    commands.add(lineCommands);
                    i++;
                } else {
                    comm = parseSimpleCommand(currentCommand);
                    if(currentCommand.equals("go")) comm.setDefaultsValues();
                    lineCommands.add(comm);
                    commands.add(lineCommands);
                }
            } else {
                lineCommands.add(Command.WRONG);
                commands.add(lineCommands);
            }
        }

        return commands;
    }

}
