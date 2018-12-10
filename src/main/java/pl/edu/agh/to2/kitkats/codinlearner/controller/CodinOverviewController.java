package pl.edu.agh.to2.kitkats.codinlearner.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import pl.edu.agh.to2.kitkats.codinlearner.command.CommandRegistry;
import pl.edu.agh.to2.kitkats.codinlearner.command.MoveCommand;
import pl.edu.agh.to2.kitkats.codinlearner.level.Level;
import pl.edu.agh.to2.kitkats.codinlearner.level.LevelManager;
import pl.edu.agh.to2.kitkats.codinlearner.level.LevelProvider;
import pl.edu.agh.to2.kitkats.codinlearner.model.Arena;
import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;
import pl.edu.agh.to2.kitkats.codinlearner.parser.CommandParser;

import java.util.Collections;
import java.util.List;
import java.util.HashMap;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CodinOverviewController {

    private CommandRegistry commandRegistry;
    private LevelManager levelManager;
    private LevelProvider levelProvider;
    private CommandParser commandParser;

    public Arena arena;

    private CodinAppController appController;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label prevCommands;

    @FXML
    private TextField commandLine;

    @FXML
    private Canvas cursorCanvas;

    private GraphicsContext cursorGc;

    @FXML
    private Canvas lineCanvas;

    private GraphicsContext lineGc;

    @FXML
    private Button checkButton;

    @FXML
    private TextArea levelInfo;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private void initialize() {
        levelManager = new LevelManager(0);
        //TODO commands map parsed from JSON or level - for every level some available commands
        HashMap<String, Command> movesMap = new HashMap<>();
        movesMap.put("go", Command.FORWARD);     // movesMap.put("GO", Command.FORWARD);
        movesMap.put("left", Command.LEFT);      // movesMap.put("LEFT", Command.LEFT);
        movesMap.put("right", Command.RIGHT);    // movesMap.put("RIGHT", Command.RIGHT);
        movesMap.put("repeat", Command.REPEAT);
        movesMap.put("", Command.EMPTY);
        commandParser = new CommandParser(movesMap);

        commandRegistry = new CommandRegistry();

        prevCommands.setMinHeight(170);
        cursorGc = cursorCanvas.getGraphicsContext2D();
        prevCommands.heightProperty().addListener(observer -> scrollPane.setVvalue(1.0));
        cursorGc.setFill(Color.BLACK);

        lineGc = lineCanvas.getGraphicsContext2D();
        lineGc.setStroke(Color.RED);

        commandLine.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke){
                if (ke.getCode().equals(KeyCode.ENTER)){

                    List<List<Command>> commands = commandParser.parseCommand(commandLine.getText());
                    prevCommands.setMinHeight(max(170,Region.USE_PREF_SIZE));
                    prevCommands.setText(prevCommands.getText() + "\n>>> " + commandLine.getText());

                    for(List<Command> lineCommands : commands) {
                        if (handleOperation(lineCommands)) {
                            levelManager.addCommands(lineCommands);
                            move(lineCommands);
                            commandLine.clear();
                        } else {
                            prevCommands.setText("TypeException: '" + commandLine.getText() + "' is incorrect operation!");
                        }

                    }
                }
            }
        });
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
        drawCursor();
    }

    public void showLevelInfo() {
        Level currentLevel = levelManager.getCurrentLevel();
        if (currentLevel != null) {
            levelInfo.setText(currentLevel.taskDescription);
        } else {
            levelInfo.setText("All levels completed. Congratulations!");
        }
    }

    public void resetDrawing() {
        clearCursor();
        clearLine();
        this.prevCommands.setText("");
        arena.getCursor().reset();
        arena.clearMoveGraph();
        drawCursor();
    }

    @FXML
    private void handleUndoAction(ActionEvent event) {
        clearCursor();
        commandRegistry.undo();
        this.arena.getCursor().reset();
        commandRegistry.redraw();
        drawCursor();
    }

    @FXML
    private void handleRedoAction(ActionEvent event) {
        clearCursor();
        commandRegistry.redo();
        drawCursor();
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

        resetDrawing();
        showLevelInfo();
    }

    public boolean handleOperation(List<Command> Commands){
        if(Commands.get(0).equals(Command.WRONG)) return false;
        return true;
    }

    private void move(List<Command> commands){

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


    public void setArena(Arena arena) {
        this.arena = arena;
        this.arena.getCursor().setArenaStartPoints();
    }

    public void setAppController(CodinAppController appController) {
        this.appController = appController;
    }

    private void clearLine() {
        lineGc.clearRect(0, 0, this.arena.getWidth(), this.arena.getHeight());
    }


    private void clearCursor(){
        cursorGc.clearRect(0, 0, this.arena.getWidth(), this.arena.getHeight());
    }

    private void drawCursor() {
        cursorGc.fillPolygon(arena.getCursor().getShapePointsX(), arena.getCursor().getShapePointsY(), 3);
    }
}

