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


    @Override
    public void redo() {
        this.execute();
    }

    @Override
    public String getName() {
        return "New line: ";
    }
}
