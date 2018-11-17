package pl.edu.agh.to2.kitkats.codinlearner.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import pl.edu.agh.to2.kitkats.codinlearner.model.Arena;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CodinOverviewController {
    public Arena arena;

    private CodinAppController appController;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label prevCommands;

    @FXML
    private TextField commandLine;

    @FXML
    private Canvas turtleCanvas;

    private GraphicsContext turtleGc;

    @FXML
    private Canvas lineCanvas;

    private GraphicsContext lineGc;


    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    @FXML
    private Button moveForwardButton;

    @FXML
    private void initialize() {
        prevCommands.setMinHeight(170);
        turtleGc = turtleCanvas.getGraphicsContext2D();
        prevCommands.heightProperty().addListener(observer -> scrollPane.setVvalue(1.0));
        turtleGc.setFill(Color.BLACK);

        lineGc = lineCanvas.getGraphicsContext2D();
        lineGc.setStroke(Color.RED);

        commandLine.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke){
                if (ke.getCode().equals(KeyCode.ENTER)){

                    prevCommands.setMinHeight(max(170,Region.USE_PREF_SIZE));
                    prevCommands.setText(prevCommands.getText() + "\n>>> " + commandLine.getText());
                    commandLine.clear();
                }
            }
        });
    }

    @FXML
    private void handleLeftAction(ActionEvent event) {
        this.arena.getTurtle().turnLeft();
        clearArena();
        turtleGc.fillPolygon(
                this.arena.getTurtle().getShapePointsX(), this.arena.getTurtle().getShapePointsY(),3);

    }

    @FXML
    private void handleRightAction(ActionEvent event) {
        this.arena.getTurtle().turnRight();
        clearArena();
        turtleGc.fillPolygon(
                this.arena.getTurtle().getShapePointsX(), this.arena.getTurtle().getShapePointsY(),3);
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        move();
    }


    private void move(){

        clearArena();
        System.out.println(scrollPane.getVvalue());
        DoubleProperty x  = new SimpleDoubleProperty();
        DoubleProperty y  = new SimpleDoubleProperty();

        float startX = arena.getTurtle().getX();
        float startY = arena.getTurtle().getY();

        this.arena.getTurtle().move();

        float endX = arena.getTurtle().getX();
        float endY = arena.getTurtle().getY();

        lineGc.strokeLine( startX,  startY, endX, endY);

        turtleGc.fillPolygon(
                arena.getTurtle().getShapePointsX(), arena.getTurtle().getShapePointsY(),3);

    }


    public void setData(Arena arena) {
        this.arena = arena;
        turtleGc.fillPolygon(
                this.arena.getTurtle().getShapePointsX(), this.arena.getTurtle().getShapePointsY(),3);

    }

    public void setAppController(CodinAppController appController) {
        this.appController = appController;
    }

    private void clearArena(){
        turtleGc.clearRect(0, 0, this.arena.getWidth(), this.arena.getHeight());
    }
}

