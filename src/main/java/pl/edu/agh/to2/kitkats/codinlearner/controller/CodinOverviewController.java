package pl.edu.agh.to2.kitkats.codinlearner.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pl.edu.agh.to2.kitkats.codinlearner.canvas.CanvasManager;
import pl.edu.agh.to2.kitkats.codinlearner.command.CommandRegistry;
import pl.edu.agh.to2.kitkats.codinlearner.command.MoveCommand;
import pl.edu.agh.to2.kitkats.codinlearner.level.Level;
import pl.edu.agh.to2.kitkats.codinlearner.level.LevelManager;
import pl.edu.agh.to2.kitkats.codinlearner.level.LevelProvider;
import pl.edu.agh.to2.kitkats.codinlearner.model.*;
import pl.edu.agh.to2.kitkats.codinlearner.parser.InstructionHistory;
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
    private InstructionHistory instructionHistory;

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
    private Text infoText;

    @FXML
    private Text stepsText;

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
        instructionHistory = new InstructionHistory();

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
                () -> !levelManager.currentLevelExists(), levelManager.levelNumberProperty())
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

    public void initializeCommandLine() {
        commandLine.setOnKeyPressed(
                keyEvent -> {
                    KeyCode keyCode = keyEvent.getCode();
                    String text = null;

                    if (keyCode == KeyCode.UP) {
                        if (commandLine.getText().equals("") || instructionHistory.isIterated()) {
                            text = instructionHistory.previous();
                        }
                    } else if (keyCode == KeyCode.DOWN) {
                        if (commandLine.getText().equals("") || instructionHistory.isIterated()) {
                            text = instructionHistory.next();
                        }
                    } else if (keyCode == KeyCode.ENTER && keyEvent.isControlDown()) {
                        instructionHistory.add(commandLine.getText());
                        instructionHistory.resetIterator();
                        handleExecuteAction(null);
                    } else {
                        instructionHistory.resetIterator();
                    }

                    if (text != null) {
                        keyEvent.consume();
                        commandLine.setText(text);
                        commandLine.positionCaret(text.length());
                        instructionHistory.setLastKeyCode(keyCode);
                    }
                }
        );
    }

    public void initializeStepsText() {
        setStepsText();
    }

    public void showLevelInfo() {
        Level currentLevel = levelManager.getCurrentLevel();
        if (currentLevel != null) {
            levelInfo.setText(currentLevel.taskDescription);
            setStepsText();
        } else {
            levelInfo.setText("All levels completed. Congratulations!");
        }
    }

    private void setStepsText() {
        Integer a = levelManager.getMoveNumber();
        Long b = levelManager.getCurrentLevel().minNumberOfMoves;
        String text = a.toString() + " / " + b.toString();
        stepsText.setText(text);
    }

    @FXML
    private void handlePrevLevelAction(ActionEvent event){
        levelManager.prevLevel();
        setStepsText();

        canvasManager.resetCommandRegistry();
        resetDrawing();
        this.canvasManager.drawShadow(this.levelManager.getCurrentLevel().commands);
        this.canvasManager.drawCursor();
        showLevelInfo();
    }

    @FXML
    private void handleNextLevelAction(ActionEvent event){
        levelManager.nextLevel();
        setStepsText();

        canvasManager.resetCommandRegistry();
        resetDrawing();
        this.canvasManager.drawShadow(this.levelManager.getCurrentLevel().commands);
        this.canvasManager.drawCursor();
        showLevelInfo();
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
            setStepsText();
            canvasManager.resetCommandRegistry();
            resetDrawing();
//            addLevelsButton.setText("");
//            hbox.getChildren().
        }

    }

    @FXML
    private void handleUndoAction(ActionEvent event) {
        if (commandRegistry.undo()) {
            canvasManager.drawCursor();
            levelManager.previousCommand();
            if (levelManager.getCommandNumber() == 0) {
                levelManager.previousMove();
                setStepsText();
            }
        }
    }

    @FXML
    private void handleRedoAction(ActionEvent event) {
        commandRegistry.redo();
        canvasManager.drawCursor();
        if (commandRegistry.redo()) {
//            levelManager.nextCommand();
            if (levelManager.getMoveNumber() == 0) {
                levelManager.nextMove();
            }
            levelManager.nextCommand();
//                levelManager.nextCommand();
//                setStepsText();
            if (levelManager.getCommandNumber() == levelManager.getInitialCommandNumber()) {
                setStepsText();
                levelManager.nextMove();

//            } else {
//                levelManager.nextCommand();
            }
        }
    }

    @FXML
    private void handleExecuteAction(ActionEvent event) {
        String input = commandLine.getText();
        instructionParser.setMoveNumber(0);
        List<ParameterizedInstruction> instructions = instructionParser.parseInstruction(input, true);
        prevCommands.setMinHeight(max(170, Region.USE_PREF_SIZE));

        if (!InstructionParser.isInputWhitespace(input)) {
            prevCommands.setText(prevCommands.getText() + "\n>>> " + commandLine.getText());
        }

        if (instructions.isEmpty()) {
            commandLine.clear();
            infoText.setText("");
            return;
        }

        int commandNumber = 0;

        for (ParameterizedInstruction instruction : instructions) {
            if (handleOperation(instruction)) {
                levelManager.addInstruction(instruction);
                commandRegistry.executeCommand(new MoveCommand(instruction, canvasManager));
                commandNumber++;
                //canvasManager.move(lineCommand);
                commandLine.clear();
                infoText.setText("");
            } else {
                infoText.setText("TypeException: '" + commandLine.getText() + "' is incorrect operation!");
            }
        }

        // TODO: Currently InstructionParser handles only 1 loop or procedure
        if (commandNumber > 0) {
            int moveNumber = instructionParser.getMoveNumber();
            if (moveNumber > 1) {
                for (int i = 0; i < moveNumber; i++) {
                    levelManager.addMove(1);
//                    levelManager.setCurrentMoveCommandNumber(1);
                }
            } else if (moveNumber == 1) {
                levelManager.addMove(commandNumber);
//                levelManager.setCurrentMoveCommandNumber(commandNumber);
            }
            setStepsText();
        }

        this.canvasManager.resetCursor();
        this.canvasManager.clearAll();
        this.commandRegistry.redraw();
        this.canvasManager.drawCursor();

//        levelManager.pushMove(commandNumber);
//        levelManager.setCurrentMoveCommandNumber(commandNumber);
//        setStepsText();

    }

    @FXML
    private void handleCheckAction(ActionEvent event) {
        boolean passed = levelManager.checkCurrentLevel(this.arena.getMoveGraph());
        Alert alert;

        if (passed) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Level passed!");

            levelManager.nextLevel();
            setStepsText();
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("There were some errors in your solution. Try again...");

            levelManager.resetLevel();
            setStepsText();
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
        instructionHistory.resetIterator();
//        this.prevCommands.setText("");
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

