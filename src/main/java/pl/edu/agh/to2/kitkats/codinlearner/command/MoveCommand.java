package pl.edu.agh.to2.kitkats.codinlearner.command;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pl.edu.agh.to2.kitkats.codinlearner.model.Arena;
import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedCommand;

import java.util.ArrayList;
import java.util.List;


public class MoveCommand implements pl.edu.agh.to2.kitkats.codinlearner.command.Command{

    private GraphicsContext lineGc;
    private List<ParameterizedCommand> commandList;
    private Arena arena;

    public MoveCommand(GraphicsContext graphicsContext, List<ParameterizedCommand> commandList, Arena arena) {
        this.commandList = commandList;
        this.arena = arena;
        this.lineGc = graphicsContext;
    }

    @Override
    public void execute() {
        double startX = arena.getCursor().getX();
        double startY = arena.getCursor().getY();

        this.arena.getCursor().move(1, commandList);

        double endX = arena.getCursor().getX();
        double endY = arena.getCursor().getY();

        lineGc.strokeLine( startX,  startY, endX, endY);
    }

    @Override
    public void undo() {

        this.arena.getCursor().moveBack(commandList);

        lineGc.clearRect(0, 0, this.arena.getWidth(), this.arena.getHeight());

    }

    @Override
    public void redraw(){
        double startX = arena.getCursor().getX();
        double startY = arena.getCursor().getY();

        this.arena.getCursor().move(0, this.commandList);

        double endX = arena.getCursor().getX();
        double endY = arena.getCursor().getY();

        lineGc.strokeLine( startX,  startY, endX, endY);
    }

    private void clearLine(double startX, double startY, double endX,double endY){
        System.out.println("in clear");
        double tmp;
        if(startX > endX){
            tmp = startX;
            startX = endX;
            endX = tmp;
        }
        if(startY > endY){
            tmp = startY;
            startY = endY;
            endY = tmp;
        }
        double xStep = Math.abs(startX - endX) / 100;
        double yStep = Math.abs(startY - endY) / 100;
        if(startX == endX){
            lineGc.clearRect(startX - 0.001, startY, startX + 0.001, endY);
        } else if(startY == endY){
            lineGc.clearRect(startX, startY - 0.001, endX, startY + 0.001);
        } else {
            for (int i = 0; i < 100; i++) {
                lineGc.clearRect(startX + i * xStep, startY + i * yStep,
                        startX + (i + 1) * xStep, startY + (i + 1) * yStep);
            }
        }
    }

    @Override
    public void redo() {
        this.execute();
    }

    @Override
    public String getName() {
        return "New line: ";
    }
}
