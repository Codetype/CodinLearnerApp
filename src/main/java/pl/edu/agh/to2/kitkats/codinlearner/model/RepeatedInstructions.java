package pl.edu.agh.to2.kitkats.codinlearner.model;

import java.util.LinkedList;
import java.util.List;

public class RepeatedInstructions {

    private List<ParameterizedInstruction> instructions;
    private final int repeat;

    public void add(Instruction instruction, int commandValue) {
        instructions.add(new ParameterizedInstruction(instruction, commandValue));
    }

    public List<ParameterizedInstruction> getAll() {
        List<ParameterizedInstruction> result = new LinkedList<>();
        for (int i = 0; i < repeat; i++) {
            result.addAll(instructions);
        }
        return result;
    }

    public RepeatedInstructions() {
        instructions = new LinkedList<>();
        this.repeat = 1;
    }

    public RepeatedInstructions(int repeat) {
        instructions = new LinkedList<>();
        this.repeat = repeat;
    }

    public List<ParameterizedInstruction> getInstructions() {
        return instructions;
    }

    public int getRepeat() {
        return repeat;
    }
}
