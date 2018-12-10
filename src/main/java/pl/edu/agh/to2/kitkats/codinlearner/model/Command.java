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

    public void setDefaultsValues(){
        RIGHT.setValue(90);
        LEFT.setValue(90);
        FORWARD.setValue(1);
        BACK.setValue(1);
        WRONG.setValue(0);
        EMPTY.setValue(0);
        REPEAT.setValue(0);
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
