package pl.edu.agh.to2.kitkats.codinlearner.level;

import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import java.util.List;
public class Level {

    public final List<Command> task;
    public final String taskDescription;
    private int bestSollution;

    public Level(List<Command> task, String taskDescription) {
        this.task = task;
        this.taskDescription = taskDescription;
        this.bestSollution = 999;
    }

    public int getBestSollution() {
        return bestSollution;
    }

    public void setBestSollution(int bestSollution) {
        this.bestSollution = bestSollution;
    }
}
