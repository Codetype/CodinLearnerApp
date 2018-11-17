package pl.edu.agh.to2.kitkats.codinlearner.level;

import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import pl.edu.agh.to2.kitkats.codinlearner.parser.CommandParser;

import java.util.ArrayList;
import java.util.List;
public class LevelManager {

    private List<Level> levels;
    private int currentLevel;
    private List<Command> currentLevelCommands;
    private int currentLevelCommandNumber;


    public LevelManager( int currentLevel) {
        this.levels = new ArrayList<>();
        this.currentLevel = currentLevel;
        this.currentLevelCommands = new ArrayList<>();
        this.currentLevelCommandNumber = 0;

    }

    public boolean checkCurrentLevel(String stringCommands){
        boolean passed = LevelCheck.check(levels.get(currentLevel), this.currentLevelCommands);
        if(passed) {
            levels.get(this.currentLevel).addSollution(this.currentLevelCommandNumber);
            return true;
        }
        else return false;
    }

    public void nextLevel(){
        this.currentLevelCommands.clear();
        this.currentLevel += this.currentLevel;
        this.currentLevelCommandNumber = 0;
    }

    public void addCommands(List<Command> commands){
        this.currentLevelCommands.addAll(commands);
        this.currentLevelCommandNumber++;
    }

}
