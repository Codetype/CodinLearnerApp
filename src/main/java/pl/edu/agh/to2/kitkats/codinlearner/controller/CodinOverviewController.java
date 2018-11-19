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
import pl.edu.agh.to2.kitkats.codinlearner.level.Level;
import pl.edu.agh.to2.kitkats.codinlearner.level.LevelManager;
import pl.edu.agh.to2.kitkats.codinlearner.model.Arena;
import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import pl.edu.agh.to2.kitkats.codinlearner.parser.CommandParser;

import java.util.Collections;
import java.util.List;
import java.util.HashMap;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CodinOverviewController {

    private LevelManager levelManager;
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
    private void initialize() {
        levelManager = new LevelManager(0);
        //TODO commands map parsed from JSON or level - for every level some available commands
        HashMap<String, Command> movesMap = new HashMap<>();
        movesMap.put("go", Command.FORWARD);     // movesMap.put("GO", Command.FORWARD);
        movesMap.put("left", Command.LEFT);      // movesMap.put("LEFT", Command.LEFT);
        movesMap.put("right", Command.RIGHT);    // movesMap.put("RIGHT", Command.RIGHT);
        movesMap.put("", Command.EMPTY);
        commandParser = new CommandParser(movesMap);

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

                    List<Command> commands = commandParser.parseCommand(commandLine.getText());

                    prevCommands.setMinHeight(max(170,Region.USE_PREF_SIZE));
                    if(handleOperation(commands)){
                        prevCommands.setText(prevCommands.getText() + "\n>>> " + commandLine.getText());
                    } else {
                        prevCommands.setText(prevCommands.getText() + "\n>>> '" + commandLine.getText() + "' is incorrect operation!");
                    }
                    levelManager.addCommands(commands);
                    move(commands);
                    commandLine.clear();
                }
            }
        });
    }

    public void initializeLevels() {
        // level 1
        int commandNumber = 2;
        float lineLength = commandNumber * arena.getCursor().getMoveStep();
        List<Command> task = Collections.nCopies(commandNumber, Command.FORWARD);
        Level l1 = new Level(task, "Draw a line (length: " + commandNumber + ")");
        levelManager.addLevel(l1);
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
        drawCursor();
    }


    @FXML
    private void handleCheckAction(ActionEvent event) {
        boolean passed = levelManager.checkCurrentLevel();
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

        double startX = arena.getCursor().getX();
        double startY = arena.getCursor().getY();

        this.arena.getCursor().move(commands);

        double endX = arena.getCursor().getX();
        double endY = arena.getCursor().getY();

        lineGc.strokeLine( startX,  startY, endX, endY);

        drawCursor();
    }


    public void setArena(Arena arena) {
        this.arena = arena;
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

