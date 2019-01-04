package pl.edu.agh.to2.kitkats.codinlearner.parser;

import pl.edu.agh.to2.kitkats.codinlearner.model.Instruction;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class InstructionParser {

    private HashMap<String, Instruction> instructionHashMap;

    public static final String GO = "go";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String REPEAT = "repeat";
    public static final String EMPTY = "";

    public InstructionParser(HashMap<String, Instruction> instructionMap) {
        this.instructionHashMap = instructionMap;
    }

    public List<ParameterizedInstruction> parseInstruction(String instructionAsString) {
        List<ParameterizedInstruction> instructions = new ArrayList<>();
        List<String> parts = Arrays.asList(instructionAsString.split("\\s"));

        for (int i = 0; i < parts.size(); i++) {
            String currentInstruction = parts.get(i);

            if (currentInstruction.equals(REPEAT)) {
                i++;
                try {
                    int repeats = 0;
                    if (i < parts.size()) {
                        repeats = Integer.parseInt(parts.get(i));
                        i++;
                    }
                    String loopContent = instructionAsString.substring(instructionAsString.indexOf("[") + 1, instructionAsString.lastIndexOf("]"));
                    for (int r = 0; r < repeats; r++) {
                        instructions.addAll(parseInstruction(loopContent));
                    }
                    i = instructionAsString.indexOf("]");
                } catch (NumberFormatException e) {
                    instructions.add(parseWrongInstruction());
                    return instructions;
                }
            } else {
                if (instructionHashMap.containsKey(parts.get(i))) {
                    currentInstruction = parts.get(i);
                    //check next string
                    if (i + 1 < parts.size() && !instructionHashMap.containsKey(parts.get(i + 1))) {
                        instructions.add(parseComplexInstruction(currentInstruction, parts.get(i + 1)));

                        i++;
                    } else {
                        instructions.add(parseSimpleInstruction(currentInstruction));
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
}