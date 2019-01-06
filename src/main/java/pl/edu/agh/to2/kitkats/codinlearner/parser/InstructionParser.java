package pl.edu.agh.to2.kitkats.codinlearner.parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import pl.edu.agh.to2.kitkats.codinlearner.level.LevelManager;
import pl.edu.agh.to2.kitkats.codinlearner.model.Instruction;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;

import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionParser {
    private HashMap<String, JSONObject> procedureHashMap = new HashMap<>();
    private HashMap<String, Instruction> instructionHashMap;
    public static final String GO = "go";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String REPEAT = "repeat";
    public static final String BEGIN = "begin";
    public static final String END = "end";
    public static final String EMPTY = "";
    private int moveNumber;

    public int getMoveNumber() {
        return moveNumber;
    }

    public InstructionParser(HashMap<String, Instruction> instructionMap) {
        this.procedureHashMap = getProcedures();
        this.instructionHashMap = instructionMap;
    }


    public static boolean isInputWhitespace(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isWhitespace(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public List<ParameterizedInstruction> parseInstruction(String instructionAsString, boolean count) {
        if (count) moveNumber = 0;
        List<ParameterizedInstruction> instructions = new ArrayList<>();
        List<String> parts = Arrays.asList(instructionAsString.split("\\s"));

        for (int i = 0; i < parts.size(); i++) {
            String currentInstruction = parts.get(i);

            if(currentInstruction.equals(BEGIN)){
                JSONObject obj = new JSONObject();
                //get name of proceedure
                i++;
                String name = parts.get(i);
                obj.put("name", name);

                //check if we have some arguments
                Long numberOfArguments = Long.valueOf(0);
                JSONArray arguments = new JSONArray();
                for(int j=i+=1; j<parts.size(); j++) {
                    Pattern pattern = Pattern.compile(":.*");
                    Matcher matcher = pattern.matcher(parts.get(j));
                    if (matcher.find()) {
                        String m = matcher.group();
                        m = m.substring(1);
                        obj.put(m, "");
                        numberOfArguments++;
                        arguments.add(m);
                    } else {
                        obj.put("numberOfArguments", numberOfArguments);
                        i = j;
                        break;
                    }
                }
                obj.put("arguments", arguments);
                parseProcedureContent(obj, instructionAsString.substring(instructionAsString.indexOf(parts.get(i++)), instructionAsString.indexOf(END)));
                if(!obj.keySet().contains("wrong")) {
                    procedureHashMap.put(name, obj);
                }
                else{
                    instructions.add(parseWrongInstruction());
                    return instructions;
                }
                i = parts.indexOf(END);
            }
            else if (currentInstruction.equals(REPEAT)) {
                if (count) moveNumber++;

                i++;
                try {
                    int repeats = 0;
                    if (i < parts.size()) {
                        repeats = Integer.parseInt(parts.get(i));
                        i++;
                    }
                    String loopContent = instructionAsString.substring(instructionAsString.indexOf("[") + 1, instructionAsString.lastIndexOf("]"));
                    for (int r = 0; r < repeats; r++) {
                        instructions.addAll(parseInstruction(loopContent, false));
                    }
                    i = instructionAsString.indexOf("]");
                } catch (NumberFormatException e) {
                    instructions.add(parseWrongInstruction());
                    return instructions;
                }
            } else {
                if (instructionHashMap.containsKey(parts.get(i))) {
                    if (count) moveNumber++;

                    currentInstruction = parts.get(i);
                    //check next string
                    if (i + 1 < parts.size() && !instructionHashMap.containsKey(parts.get(i + 1))) {
                        instructions.add(parseComplexInstruction(currentInstruction, parts.get(i + 1)));
                        i++;
                    } else {
                        instructions.add(parseSimpleInstruction(currentInstruction));
                    }
                } else if (procedureHashMap.containsKey(currentInstruction)){
                    if (count) moveNumber++;

                    JSONObject obj = procedureHashMap.get(currentInstruction);
                    final Long noa = (Long) obj.get("numberOfArguments");
                    if(noa != 0) {
                        if(noa > parts.size()-1){
                            System.out.println("Bad number of arguments");
                            instructions.add(parseWrongInstruction());
                            return instructions;
                        }
                        else {
                            JSONArray JSONarguments = (JSONArray) obj.get("arguments");
                            Iterator<String> argumentsIterator = JSONarguments.iterator();
                            while(argumentsIterator.hasNext()) {
                                i++;
                                try {
                                    obj.replace(argumentsIterator.next(), Long.parseLong(parts.get(i)));
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    instructions.add(parseWrongInstruction());
                                    return instructions;
                                }
                            }
                            String instruction = (String) obj.get("instruction");
                            argumentsIterator = JSONarguments.iterator();
                            while(argumentsIterator.hasNext()){
                                String argument = argumentsIterator.next();
                                Long val = (Long) obj.get(argument);
                                instruction = instruction.replaceAll(argument, val.toString());
                            }
                            instructions.addAll(parseInstruction(instruction, false));
                        }
                    }
                } else {
                    instructions.add(parseWrongInstruction());
                }
            }
        }
        return instructions;
    }

    private ParameterizedInstruction parseWrongInstruction() {
        return new ParameterizedInstruction(Instruction.WRONG);
    }

    private ParameterizedInstruction parseSimpleInstruction(String currentInstruction) {
        Instruction instruction = instructionHashMap.getOrDefault(currentInstruction, Instruction.WRONG);
        return new ParameterizedInstruction(instruction);
    }

    private ParameterizedInstruction parseComplexInstruction(String currentInstruction, String nextInstruction) {
        Instruction instruction = instructionHashMap.getOrDefault(currentInstruction, Instruction.WRONG);
        try {
            int parameter = Integer.parseInt(nextInstruction);
            return new ParameterizedInstruction(instruction, parameter);
        } catch (NumberFormatException e) {
            return new ParameterizedInstruction(Instruction.WRONG);
        }
    }

    private void parseProcedureContent(JSONObject obj, String content){
        List<String> parts = Arrays.asList(content.split("\\s"));

        for(int j=0; j<parts.size(); j++) {
            String currentInstruction = parts.get(j);
            if (currentInstruction.equals(REPEAT)) {
                String loopContent = content.substring(content.indexOf("[") + 1, content.lastIndexOf("]"));
                int k = j + 1;
                if (k < parts.size() - 1 && !instructionHashMap.containsKey(parts.get(k))) {
                    try {
                        currentInstruction = parts.get(k);
                        Long.parseLong(currentInstruction);
                        parseProcedureContent(obj, loopContent);
                    } catch (NumberFormatException e) {
                        if (obj.keySet().contains(currentInstruction)) {
                            parseProcedureContent(obj, loopContent);
                        } else {
                            obj.put("wrong", true);
                        }
                    }
                }
                j = content.indexOf("]");
            } else if (instructionHashMap.containsKey(currentInstruction)) {
                int k = j + 1;
                if (k < parts.size() && !instructionHashMap.containsKey(parts.get(k))) {
                    try {
                        currentInstruction = parts.get(k);
                        Long.parseLong(currentInstruction);
                    } catch (NumberFormatException e) {
                        if (!obj.keySet().contains(currentInstruction)) {
                            obj.put("wrong", true);
                        }
                    }
                    j++;
                }
            } else if(procedureHashMap.containsKey(currentInstruction)){
                JSONObject calledProcedure = (JSONObject) procedureHashMap.get(currentInstruction);
                final Long noa = (Long) calledProcedure.get("numberOfArguments");

                for(int k=0; k<noa.intValue(); k++){
                    j++;
                    try {
                        Long.parseLong(parts.get(j));
                    } catch(NumberFormatException e){
                        if(!calledProcedure.keySet().contains(parts.get(j))){
                            obj.put("wrong", true);
                        }
                    }
                }
            } else {
                obj.put("wrong", true);
            }
        }
        obj.put("instruction", content);
    }

    private HashMap<String, JSONObject> getProcedures(){
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(
                    "./src/main/resources/MoveConfiguration/Procedures.json"));
            JSONObject jsonObj = (JSONObject) obj;
            JSONArray jsonProcedures = (JSONArray) jsonObj.get("procedures");

            for (Object procedure : jsonProcedures) {
                JSONObject jsonProcedure = (JSONObject) procedure;
                final String name = (String) jsonProcedure.get("name");

                procedureHashMap.put(name, jsonProcedure);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.procedureHashMap;
    }
}