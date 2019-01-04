package pl.edu.agh.to2.kitkats.codinlearner.level;

import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;

public class Level {

    public final MoveGraph task;
    public final String taskDescription;
    private int bestSolution;

    public Level(MoveGraph task, String taskDescription) {
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

    public boolean check( MoveGraph graph){
        return graph.isTheSame(this.task);
    }
}
