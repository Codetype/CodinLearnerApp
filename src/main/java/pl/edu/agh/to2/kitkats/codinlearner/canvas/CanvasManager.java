package pl.edu.agh.to2.kitkats.codinlearner.canvas;

import javafx.scene.canvas.GraphicsContext;
import pl.edu.agh.to2.kitkats.codinlearner.command.MoveRegistry;
import pl.edu.agh.to2.kitkats.codinlearner.command.DrawMove;
import pl.edu.agh.to2.kitkats.codinlearner.model.Arena;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedCommand;

public class CanvasManager {

    private Arena arena;
    private GraphicsContext cursorGc;
    private GraphicsContext lineGc;
    private MoveRegistry moveRegistry;

    public CanvasManager(Arena arena, GraphicsContext cursorGc, GraphicsContext lineGc, MoveRegistry moveRegistry) {
        this.arena = arena;
        this.cursorGc = cursorGc;
        this.lineGc = lineGc;
        this.moveRegistry = moveRegistry;
    }


    public void undo() {
        clearCursor();
        moveRegistry.undo();
        this.arena.getCursor().reset();
        moveRegistry.redraw();
        drawCursor();
    }


    public void redo() {
        clearCursor();
        moveRegistry.redo();
        drawCursor();
    }

    public void move(ParameterizedCommand command){

        clearCursor();

        moveRegistry.execute(
                new DrawMove(
                        this.lineGc,
                        command,
                        this.arena
                )
        );
        drawCursor();
    }

    public void resetDrawing() {
        clearCursor();
        clearLine();
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

    public void drawCursor() {
        cursorGc.fillPolygon(arena.getCursor().getShapePointsX(), arena.getCursor().getShapePointsY(), 3);
    }

    public void resetCommandRegistry(){
        this.moveRegistry.reset();
    }
}
