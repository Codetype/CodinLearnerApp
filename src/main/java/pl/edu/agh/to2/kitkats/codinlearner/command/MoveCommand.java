package pl.edu.agh.to2.kitkats.codinlearner.command;

import javafx.scene.canvas.GraphicsContext;
import pl.edu.agh.to2.kitkats.codinlearner.canvas.CanvasManager;
import pl.edu.agh.to2.kitkats.codinlearner.model.Arena;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;


public class MoveCommand implements pl.edu.agh.to2.kitkats.codinlearner.command.Command{

    private ParameterizedInstruction command;
    private CanvasManager canvasManager;

    public MoveCommand(ParameterizedInstruction command, CanvasManager canvasManager) {
        this.command = command;
        this.canvasManager = canvasManager;
    }

    @Override
    public void execute() {
        this.canvasManager.move(this.command, 1);
    }

    @Override
    public void undo() {
        this.canvasManager.undo(this.command);
        this.canvasManager.clearAll();
    }

    @Override
    public void redraw(){
        this.canvasManager.move(this.command, 0);
    }

    @Override
    public void redo() {
        execute();
        this.canvasManager.resetCursor();
        this.canvasManager.clearAll();
    }


    @Override
    public String getName() {
        return "New line: ";
    }
}
