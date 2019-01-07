package pl.edu.agh.to2.kitkats.codinlearner.level;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LevelManager {

    // TODO: Maybe replace IntegerProperty with ObjectProperty<Level>
    private IntegerProperty currentLevelNumber;
    private List<Level> levels;
    private List<ParameterizedInstruction> currentLevelCommands;
    private int currentLevelCommandNumber;
    private int currentMoveCommandNumber;
    private Stack<Integer> currentLevelMoves;

    public int getCurrentMoveCommandNumber() {
        return currentMoveCommandNumber;
    }

    public void setCurrentMoveCommandNumber(int currentMoveCommandNumber) {
        this.currentMoveCommandNumber = currentMoveCommandNumber;
    }

    public void incrementCurrentMoveCommandNumber() {
        this.currentMoveCommandNumber++;
    }

    public void decrementCurrentMoveCommandNumber() {
        this.currentMoveCommandNumber--;
    }

    public LevelManager (int currentLevelNumber) {
        this.levels = new ArrayList<>();
        this.currentLevelNumber = new SimpleIntegerProperty(currentLevelNumber);
        this.currentLevelCommands = new ArrayList<>();
        this.currentLevelCommandNumber = 0;
        this.currentLevelMoves = new Stack<>();
//        this.currentLevelMoveNumber = 0;
    }

    public boolean checkCurrentLevel(MoveGraph graph){
        boolean passed = levels.get(getCurrentLevelNumber()).check(graph);
        if(passed) {
            levels.get(this.getCurrentLevelNumber()).addSolution(this.currentLevelCommandNumber);
            return true;
        }
        else return false;
    }

    public boolean currentLevelExists() {
        return (getCurrentLevelNumber() < levels.size());
    }

    public void nextLevel() {
        if (currentLevelExists()) {
            resetLevel();
            this.currentLevelNumber.set(this.getCurrentLevelNumber() + 1);
        }
    }

    public void resetLevel() {
        this.currentLevelCommands.clear();
        this.currentLevelCommandNumber = 0;
        this.currentMoveCommandNumber = 0;
        this.currentLevelMoves.removeAllElements();
    }

    public void addCommand(ParameterizedInstruction command){
        this.currentLevelCommands.add(command);
        this.currentLevelCommandNumber++;
    }

    public void pushMove(Integer numberOfInstructions) {
        this.currentLevelMoves.push(numberOfInstructions);
//        this.currentLevelCommandNumber += numberOfInstructions;
    }

    public void popMove() {
        this.currentLevelMoves.pop();
        setCurrentMoveCommandNumber(this.currentLevelMoves.peek());
//        this.currentLevelCommandNumber -= numberOfInstructions;
    }

    public void addLevel(Level level) {
        this.levels.add(level);
    }

    public Level getCurrentLevel() {
        if (currentLevelExists()) {
            return levels.get(getCurrentLevelNumber());
        } else {
            return null;
        }
    }

    public int getCurrentLevelMoveNumber() {
        return currentLevelMoves.size();
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber.get();
    }

    public IntegerProperty currentLevelNumberProperty() {
        return currentLevelNumber;
    }
}
