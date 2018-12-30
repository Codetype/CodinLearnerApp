package pl.edu.agh.to2.kitkats.codinlearner.model;

public enum Instruction {
    RIGHT,
    LEFT,
    FORWARD,
    BACK,
    WRONG,
    EMPTY;

    public Instruction oppositeInstruction() {
        switch (this) {
            case FORWARD: return BACK;
            case RIGHT: return LEFT;
            case LEFT: return RIGHT;
        }
        return EMPTY;
    }
}
