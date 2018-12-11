package pl.edu.agh.to2.kitkats.codinlearner.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import pl.edu.agh.to2.kitkats.codinlearner.command.CommandRegistry;
import pl.edu.agh.to2.kitkats.codinlearner.command.MoveCommand;
import pl.edu.agh.to2.kitkats.codinlearner.controller.CodinAppController;

import java.util.List;

public class CanvasManager {

    private Arena arena;
    private GraphicsContext cursorGc;
    private GraphicsContext lineGc;
    private CommandRegistry commandRegistry;

    public CanvasManager(Arena arena, GraphicsContext cursorGc, GraphicsContext lineGc, CommandRegistry commandRegistry) {
        this.arena = arena;
        this.cursorGc = cursorGc;
        this.lineGc = lineGc;
        this.commandRegistry = commandRegistry;
    }


    public void undo() {
        clearCursor();
        commandRegistry.undo();
        this.arena.getCursor().reset();
        commandRegistry.redraw();
        drawCursor();
    }


    public void redo() {
        clearCursor();
        commandRegistry.redo();
        drawCursor();
    }

    public void move(List<ParameterizedCommand> commands){

        clearCursor();

        commandRegistry.executeCommand(
                new MoveCommand(
                        this.lineGc,
                        commands,
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
}
