package pl.edu.agh.to2.kitkats.codinlearner.model;

public class ParameterizedInstruction {

    private final Instruction instruction;
    private int parameter;

    public ParameterizedInstruction(Instruction instruction, int parameter) {
        this.instruction = instruction;
        this.parameter = parameter;
    }

    public ParameterizedInstruction(Instruction instruction) {
        this.instruction = instruction;
        this.parameter = getDefaultParameter(this.instruction);
    }

    private int getDefaultParameter(Instruction instruction) {
        int result;
        switch (instruction) {
            case RIGHT: result = 90; break;
            case LEFT: result = 90; break;
            case FORWARD: result = 1; break;
            case BACK: result = 1; break;
            case WRONG: result = 0; break;
            case EMPTY: result = 0; break;
            default: result = 0; break;
        }
        return result;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }
}
