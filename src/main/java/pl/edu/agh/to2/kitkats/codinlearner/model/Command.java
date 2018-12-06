package pl.edu.agh.to2.kitkats.codinlearner.model;

public enum Command {

    RIGHT, LEFT, FORWARD, BACK, WRONG, EMPTY;

    public Command oppositeCommand(){
        switch (this){
            case FORWARD: return BACK;
            case RIGHT: return LEFT;
            case LEFT: return RIGHT;
        }
        return EMPTY;
    }
}
