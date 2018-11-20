package pl.edu.agh.to2.kitkats.codinlearner.level;

import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import java.util.List;
public class Level {

    public final List<Command> task;
    public final String taskDescription;
    private int bestSolution;

    public Level(List<Command> task, String taskDescription) {
        this.task = task;
        this.taskDescription = taskDescription;
        this.bestSolution = 999;
    }

    public int getBestSolution() {
        return bestSolution;
    }

    public void addSolution(int newSolution) {
        if(newSolution < this.bestSolution)
            this.bestSolution = bestSolution;
    }

    public boolean check( List<Command> commands){
        //TODO commands validation for level

        //simple check for now
        int index = 0;
        for(Command command : commands){
            if(this.task.get(index).equals(command)) index++;
            else index = 0;
            if(index == this.task.size()) return true;
        }
        return false;
    }
}
