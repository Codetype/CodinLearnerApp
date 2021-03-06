package pl.edu.agh.to2.kitkats.codinlearner.level;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;

import java.io.FileWriter;
import java.util.*;

public class LevelManager {

    // TODO: Maybe replace IntegerProperty with ObjectProperty<Level>
    private IntegerProperty levelNumber;
    private List<Level> levels;
    private List<ParameterizedInstruction> parameterizedInstructions;
    private int commandNumber;
    private List<Integer> moves;
    private int moveIndex;

    public boolean isAllLevelsCompleted() {
        return allLevelsCompleted;
    }

    private boolean allLevelsCompleted;

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
        this.moves = new LinkedList<>();
        this.moveIndex = -1;
        this.allLevelsCompleted = false;
    }

    public boolean checkCurrentLevel(MoveGraph graph){
        boolean passed = levels.get(getLevelNumber()).check(graph);
        if (passed) {
            levels.get(getLevelNumber()).setAccomplished(true);

            return true;
        }
        else {
            return false;
        }
    }

    public boolean currentLevelExists() {
        return (getLevelNumber() < levels.size());
    }

    public boolean nextLevelExists() {
        return (getLevelNumber() <= levels.size() - 1);
    }

    public boolean prevLevelExists() {
        return (getLevelNumber() > 0);
    }

    public boolean nextLevel() {
        if (getLevelNumber() < levels.size() - 1 && levels.get(getLevelNumber()).getAccomplished()) {
            resetLevel();
            levelNumber.set(getLevelNumber() + 1);

            return true;
        } else if (getLevelNumber() == levels.size() - 1 && levels.get(getLevelNumber()).getAccomplished()) {
            levelNumber.set(getLevelNumber() + 1);
            allLevelsCompleted = true;

            return true;
        } else {
            return false;
        }
    }

    public boolean prevLevel() {
        if (allLevelsCompleted) {
            allLevelsCompleted = false;
        }
        if (getLevelNumber() > 0) {
            resetLevel();
            levelNumber.set(getLevelNumber() - 1);

            return true;
        } else if (getLevelNumber() == 0) {
            return false;
        } else {
            return false;
        }
    }

    public void resetLevel() {
        parameterizedInstructions.clear();
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

    public void setAccomplishment(){
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Level level : levels){
            jsonArray.add(level.getAccomplished());
        }

        jsonObj.put("accomplished", jsonArray);

        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/MoveConfiguration/LevelProgress.json");
            System.out.println("Zapisuje: " + jsonObj.toJSONString());
            fileWriter.write(jsonObj.toJSONString());
            fileWriter.close();
        } catch(Exception e){
            e.printStackTrace();
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
