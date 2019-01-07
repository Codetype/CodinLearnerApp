package pl.edu.agh.to2.kitkats.codinlearner.level;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;

import java.util.*;

public class LevelManager {

    // TODO: Maybe replace IntegerProperty with ObjectProperty<Level>
    private IntegerProperty levelNumber;
    private List<Level> levels;
    private List<ParameterizedInstruction> parameterizedInstructions;
//    private int currentLevelCommandNumber; // old
    private int commandNumber;
    private List<Integer> moves;
    private int moveIndex;

    public int getCommandNumber() {
        return commandNumber;
    }

    public void nextCommand() {
        commandNumber++;
    }

    public void previousCommand() {
        commandNumber--;
    }

    public LevelManager(int levelNumber) {
        this.levels = new ArrayList<>();
        this.levelNumber = new SimpleIntegerProperty(levelNumber);
        this.parameterizedInstructions = new ArrayList<>();
//        this.currentLevelCommandNumber = 0;
        this.moves = new LinkedList<>();
        this.moveIndex = -1;
    }

    public boolean checkCurrentLevel(MoveGraph graph){
        boolean passed = levels.get(getLevelNumber()).check(graph);
        if(passed) {
//            levels.get(getLevelNumber()).addSolution(currentLevelCommandNumber);
            return true;
        }
        else return false;
    }

    public boolean currentLevelExists() {
        return (getLevelNumber() < levels.size());
    }

    public void nextLevel() {
        if (currentLevelExists()) {
            resetLevel();
            levelNumber.set(getLevelNumber() + 1);
        }
    }

    public void resetLevel() {
        parameterizedInstructions.clear();
//        currentLevelCommandNumber = 0;
        commandNumber = 0;
        moves.clear();
        moveIndex = -1;
    }

    public void addInstruction(ParameterizedInstruction instruction){
        parameterizedInstructions.add(instruction);
//        currentLevelCommandNumber++;
    }

    public void addMove(Integer commandNumber) {
        if (moveIndex > -1) {
            moves.subList(moveIndex, moves.size()).clear();
            moves.add(moveIndex, this.commandNumber);
        }
        moveIndex++;
        moves.add(moveIndex, commandNumber);
        this.commandNumber = commandNumber;
    }

    public void nextMove() {
        if (moveIndex < moves.size() - 1) {
            moveIndex++;
            commandNumber = 0;
        }
    }

    public void previousMove() {
        if (moveIndex > -1) {
            moveIndex--;
        }
        if (moveIndex > -1) {
            commandNumber = moves.get(moveIndex);
        }
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public Level getCurrentLevel() {
        if (currentLevelExists()) {
            return levels.get(getLevelNumber());
        } else {
            return null;
        }
    }

    public int getMoveNumber() {
        return moveIndex + 1;
    }

    public int getInitialCommandNumber() {
        return moves.get(moveIndex);
    }

    private int getLevelNumber() {
        return levelNumber.get();
    }

    public IntegerProperty levelNumberProperty() {
        return levelNumber;
    }
}
