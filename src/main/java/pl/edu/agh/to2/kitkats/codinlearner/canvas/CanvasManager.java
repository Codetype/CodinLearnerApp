package pl.edu.agh.to2.kitkats.codinlearner.canvas;

import com.vividsolutions.jts.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import pl.edu.agh.to2.kitkats.codinlearner.command.CommandRegistry;
import pl.edu.agh.to2.kitkats.codinlearner.command.MoveCommand;
import pl.edu.agh.to2.kitkats.codinlearner.level.Level;
import pl.edu.agh.to2.kitkats.codinlearner.model.Arena;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.abs;

public class CanvasManager {

    private Arena arena;
    private GraphicsContext cursorGc;
    private GraphicsContext lineGc;
    private GraphicsContext shadowGc;
    private CommandRegistry commandRegistry;
    private List<ParameterizedInstruction> currentLevelCommands;

    public CanvasManager(Arena arena, GraphicsContext cursorGc, GraphicsContext lineGc,
                         GraphicsContext shadowGc, CommandRegistry commandRegistry) {
        this.arena = arena;
        this.cursorGc = cursorGc;
        this.lineGc = lineGc;
        this.shadowGc = shadowGc;
        this.commandRegistry = commandRegistry;
    }


    public void undo(ParameterizedInstruction command) {
        this.arena.getCursor().moveBack(command);
        this.arena.getCursor().reset();
    }

    public void clearAll(){
        clearLine();
        clearShadow();
        clearCursor();
    }


    public void drawShadow(List<ParameterizedInstruction> commands) {
        this.currentLevelCommands = commands;
        this.arena.getCursor().reset();
        for(ParameterizedInstruction command : commands){
            drawLine(command);
        }
        this.arena.getCursor().reset();
    }

    private void drawShadow() {
        clearShadow();
        this.arena.getCursor().saveStateAndReset();
        for(ParameterizedInstruction command : this.currentLevelCommands){
            drawLine(command);
        }
        this.arena.getCursor().loadState();
        System.out.println("X : " + this.arena.getCursor().getX());
    }

    private void drawLine(ParameterizedInstruction command){
            double startX = arena.getCursor().getX();
            double startY = arena.getCursor().getY();

            this.arena.getCursor().move(0, command);

            double endX = arena.getCursor().getX();
            double endY = arena.getCursor().getY();

            shadowGc.strokeLine( startX,  startY, endX, endY);
    }

    public void move(ParameterizedInstruction command, int mode){
        double startX = arena.getCursor().getX();
        double startY = arena.getCursor().getY();

        this.arena.getCursor().move(mode, command);

        double endX = arena.getCursor().getX();
        double endY = arena.getCursor().getY();

        lineGc.strokeLine( startX,  startY, endX, endY);

    }

    public void resetCursor(){
        this.arena.getCursor().reset();
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
        drawShadow();
        clearCursor();
        double fromX = min(min(arena.getCursor().getX() + arena.getCursor().getAlongVector().getX(),
                arena.getCursor().getX() + arena.getCursor().getAcrossVector().getX()),
                arena.getCursor().getX() - arena.getCursor().getAcrossVector().getX());

        double fromY = min(min(arena.getCursor().getY() + arena.getCursor().getAlongVector().getY(),
                arena.getCursor().getY() + arena.getCursor().getAcrossVector().getY()),
                arena.getCursor().getY() - arena.getCursor().getAcrossVector().getY());

        double wid = abs(fromX - max(max(arena.getCursor().getX() + arena.getCursor().getAlongVector().getX(),
                arena.getCursor().getX() + arena.getCursor().getAcrossVector().getX()),
                arena.getCursor().getX() - arena.getCursor().getAcrossVector().getX()));

        double hei = abs(fromY - max(max(arena.getCursor().getY() + arena.getCursor().getAlongVector().getY(),
                arena.getCursor().getY() + arena.getCursor().getAcrossVector().getY()),
                arena.getCursor().getY() - arena.getCursor().getAcrossVector().getY()));

        shadowGc.clearRect(fromX, fromY, wid, hei);
        System.out.println(arena.getCursor().getShapePointsX()[0]);
        System.out.println(arena.getCursor().getShapePointsX()[1]);
        System.out.println(arena.getCursor().getShapePointsX()[2]);

        System.out.println(arena.getCursor().getShapePointsY()[0]);
        System.out.println(arena.getCursor().getShapePointsY()[1]);
        System.out.println(arena.getCursor().getShapePointsY()[2]);


        cursorGc.fillPolygon(arena.getCursor().getShapePointsX(),
                arena.getCursor().getShapePointsY(), 3);

    }

    public void resetCommandRegistry(){
        this.commandRegistry.reset();
    }
}
