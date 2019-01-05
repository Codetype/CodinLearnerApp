package pl.edu.agh.to2.kitkats.codinlearner.canvas;

import javafx.scene.canvas.GraphicsContext;
import pl.edu.agh.to2.kitkats.codinlearner.command.CommandRegistry;
import pl.edu.agh.to2.kitkats.codinlearner.command.MoveCommand;
import pl.edu.agh.to2.kitkats.codinlearner.level.Level;
import pl.edu.agh.to2.kitkats.codinlearner.model.Arena;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;

import java.util.List;

public class CanvasManager {

    private Arena arena;
    private GraphicsContext cursorGc;
    private GraphicsContext lineGc;
    private GraphicsContext shadowGc;
    private CommandRegistry commandRegistry;

    public CanvasManager(Arena arena, GraphicsContext cursorGc, GraphicsContext lineGc,
                         GraphicsContext shadowGc, CommandRegistry commandRegistry) {
        this.arena = arena;
        this.cursorGc = cursorGc;
        this.lineGc = lineGc;
        this.shadowGc = shadowGc;
        this.commandRegistry = commandRegistry;
    }


    public void undo(ParameterizedInstruction command) {
        clearCursor();
        this.arena.getCursor().moveBack(command);
        lineGc.clearRect(0, 0, this.arena.getWidth(), this.arena.getHeight());
        this.arena.getCursor().reset();
    }


    public void drawShadow(List<ParameterizedInstruction> commands) {
        this.arena.getCursor().reset();
        for(ParameterizedInstruction command : commands){
            double startX = arena.getCursor().getX();
            double startY = arena.getCursor().getY();

            this.arena.getCursor().move(0, command);

            double endX = arena.getCursor().getX();
            double endY = arena.getCursor().getY();

            shadowGc.strokeLine( startX,  startY, endX, endY);
        }
        this.arena.getCursor().reset();

    }

    public void move(ParameterizedInstruction command, int mode){

        clearCursor();

        double startX = arena.getCursor().getX();
        double startY = arena.getCursor().getY();

        this.arena.getCursor().move(mode, command);

        double endX = arena.getCursor().getX();
        double endY = arena.getCursor().getY();

        lineGc.strokeLine( startX,  startY, endX, endY);

        drawCursor();
    }

    public void resetDrawing() {
        clearCursor();
        clearLine();
        clearShadow();
        arena.getCursor().reset();
        arena.clearMoveGraph();
        drawCursor();
    }

    private void clearLine() {
        lineGc.clearRect(0, 0, this.arena.getWidth(), this.arena.getHeight());
    }

    private void clearCursor(){
        cursorGc.clearRect(0, 0, this.arena.getWidth(), this.arena.getHeight());
    }

    private void clearShadow() {
        shadowGc.clearRect(0, 0, this.arena.getWidth(), this.arena.getHeight());
    }
    public void drawCursor() {
        shadowGc.clearRect(arena.getCursor().getX(), arena.getCursor().getY(),
                arena.getCursor().getAlongVector().getX(), 1);
        cursorGc.fillPolygon(arena.getCursor().getShapePointsX(),
                arena.getCursor().getShapePointsY(), 3);

    }

    public void resetCommandRegistry(){
        this.commandRegistry.reset();
    }
}
