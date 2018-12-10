package pl.edu.agh.to2.kitkats.codinlearner.model;

public enum Command {
    RIGHT(90), LEFT(90), FORWARD(1), BACK(1), WRONG(0), EMPTY(0), REPEAT(0);

    private int value;
    Command(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Command oppositeCommand(){
        switch (this){
            case FORWARD: return BACK;
            case RIGHT: return LEFT;
            case LEFT: return RIGHT;
        }
        return EMPTY;
    }
}
