package pl.edu.agh.to2.kitkats.codinlearner.level;

import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;
import java.util.List;

public class Level {

    public final MoveGraph task;
    public final List<ParameterizedInstruction> commands;
    public final String taskDescription;
    public final Long repeats;
    public final Long minNumberOfMoves;
//    private int bestSolution;
    private boolean accomplished;

    public Level(MoveGraph task, String taskDescription, List<ParameterizedInstruction> commands, Long repeats, Long minNumberOfMoves) {
        this.commands = commands;
        this.task = task;
        this.taskDescription = taskDescription;
        this.repeats = repeats;
        this.minNumberOfMoves = minNumberOfMoves;
        this.accomplished = false;
//        this.bestSolution = 999;
    }

//    public int getBestSolution() {
//        return bestSolution;
//    }
//
//    public void addSolution(int newSolution) {
//        if(newSolution < this.bestSolution)
//            this.bestSolution = bestSolution;
//    }

    public boolean getAccomplished(){
        return this.accomplished;
    }

    public void setAccomplished(boolean accomplished) {
        this.accomplished = accomplished;
    }

    public boolean check(MoveGraph graph){
        return graph.isTheSame(this.task);
    }
}
