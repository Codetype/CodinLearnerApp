package pl.edu.agh.to2.kitkats.codinlearner.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import pl.edu.agh.to2.kitkats.codinlearner.canvas.CanvasManager;
import pl.edu.agh.to2.kitkats.codinlearner.command.Command;
import pl.edu.agh.to2.kitkats.codinlearner.command.CommandRegistry;
import pl.edu.agh.to2.kitkats.codinlearner.command.MoveCommand;
import pl.edu.agh.to2.kitkats.codinlearner.level.Level;
import pl.edu.agh.to2.kitkats.codinlearner.level.LevelManager;
import pl.edu.agh.to2.kitkats.codinlearner.level.LevelProvider;
import pl.edu.agh.to2.kitkats.codinlearner.model.*;
import pl.edu.agh.to2.kitkats.codinlearner.parser.InstructionParser;

import java.util.List;
import java.util.HashMap;
import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CodinOverviewController {

    private CommandRegistry commandRegistry;
    private LevelManager levelManager;
    private LevelProvider levelProvider;
    private InstructionParser instructionParser;
    private CanvasManager canvasManager;

    public Arena arena;

    private CodinAppController appController;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label prevCommands;

    @FXML
    private TextArea commandLine;

    @FXML
    private Canvas cursorCanvas;

    private GraphicsContext cursorGc;

    @FXML
    private Canvas lineCanvas;

    private GraphicsContext lineGc;

    @FXML
    private Canvas shadowCanvas;

    private GraphicsContext shadowGc;

    @FXML
    private Button checkButton;

    @FXML
    private TextArea levelInfo;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private Button addLevelsButton;

    @FXML
    private HBox hbox;

    @FXML
    private void initialize() {
        levelManager = new LevelManager(0);
        //TODO commands map parsed from JSON or level - for every level some available commands
        HashMap<String, Instruction> movesMap = new HashMap<>();
        movesMap.put(InstructionParser.GO, Instruction.FORWARD);
        movesMap.put(InstructionParser.LEFT, Instruction.LEFT);
        movesMap.put(InstructionParser.RIGHT, Instruction.RIGHT);
        movesMap.put(InstructionParser.EMPTY, Instruction.EMPTY);
        instructionParser = new InstructionParser(movesMap);

        commandRegistry = new CommandRegistry();

        prevCommands.setMinHeight(170);
        cursorGc = cursorCanvas.getGraphicsContext2D();
        prevCommands.heightProperty().addListener(observer -> scrollPane.setVvalue(1.0));
        cursorGc.setFill(Color.BLACK);

        lineGc = lineCanvas.getGraphicsContext2D();
        lineGc.setStroke(Color.RED);

        shadowGc = shadowCanvas.getGraphicsContext2D();
        shadowGc.setStroke(Color.LIGHTGRAY);
    }



    public void initializeLevels() {
        levelProvider = new LevelProvider(arena.getCursor().getMoveStep());
        for (Level level : levelProvider.getLevels()) {
            levelManager.addLevel(level);
        }
    }

    public void initializeProperties() {
        checkButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> !levelManager.currentLevelExists(), levelManager.currentLevelNumberProperty())
        );
    }

    public void initializeDrawing() {
        this.canvasManager.drawShadow(this.levelManager.getCurrentLevel().commands);
        this.canvasManager.drawCursor();


    }

    public void initializeCanvasManager(){
        this.canvasManager = new CanvasManager(this.arena, this.cursorCanvas.getGraphicsContext2D(),
                this.lineCanvas.getGraphicsContext2D(), this.shadowCanvas.getGraphicsContext2D()
                , this.commandRegistry);
    }

    public void showLevelInfo() {
        Level currentLevel = levelManager.getCurrentLevel();
        if (currentLevel != null) {
            levelInfo.setText(currentLevel.taskDescription);
        } else {
            levelInfo.setText("All levels completed. Congratulations!");
        }
    }

    @FXML
    private void handleAddLevelsAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "About to reset current level progress. Continue?",
                ButtonType.OK,
                ButtonType.CANCEL
        );
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("ok");
            levelManager.resetLevel();
            canvasManager.resetCommandRegistry();
            resetDrawing();
//            addLevelsButton.setText("");
//            hbox.getChildren().
        }

    }

    @FXML
    private void handleUndoAction(ActionEvent event) {
        commandRegistry.undo();
        canvasManager.drawCursor();
    }

    @FXML
    private void handleRedoAction(ActionEvent event) {
        commandRegistry.redo();
    }

    @FXML
    private void handleExecuteAction(ActionEvent event) {
        List<ParameterizedInstruction> commands = instructionParser.parseInstruction(commandLine.getText());
        prevCommands.setMinHeight(max(170,Region.USE_PREF_SIZE));
        prevCommands.setText(prevCommands.getText() + "\n>>> " + commandLine.getText());

        for(ParameterizedInstruction lineCommand : commands) {
            if (handleOperation(lineCommand)) {
                levelManager.addCommand(lineCommand);
                commandRegistry.executeCommand(new MoveCommand(lineGc, lineCommand, arena, canvasManager));
                //canvasManager.move(lineCommand);
                commandLine.clear();
            } else {
                prevCommands.setText("TypeException: '" + commandLine.getText() + "' is incorrect operation!");
            }

        }
    }

    @FXML
    private void handleCheckAction(ActionEvent event) {
        boolean passed = levelManager.checkCurrentLevel(this.arena.getMoveGraph());
        Alert alert;

        if (passed) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Level passed!");

            levelManager.nextLevel();
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("There were some errors in your solution. Try again...");

            levelManager.resetLevel();
        }

        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.showAndWait();
        canvasManager.resetCommandRegistry();
        resetDrawing();
        this.canvasManager.drawShadow(this.levelManager.getCurrentLevel().commands);
        this.canvasManager.drawCursor();
        showLevelInfo();
    }


    private void resetDrawing() {
        this.canvasManager.resetDrawing();
        this.prevCommands.setText("");
    }

    private boolean handleOperation(ParameterizedInstruction command){
        return !command.getInstruction().equals(Instruction.WRONG);
    }

    public void setArena(Arena arena) {
        this.arena = arena;
        this.arena.getCursor().setArenaStartPoints();
    }

    public void setAppController(CodinAppController appController) {
        this.appController = appController;
    }

}

