package pl.edu.agh.to2.kitkats.codinlearner.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import pl.edu.agh.to2.kitkats.codinlearner.model.Arena;

public class CodinOverviewController {
    public Arena arena;

    private CodinAppController appController;

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
        turtleGc = turtleCanvas.getGraphicsContext2D();
        turtleGc.setFill(Color.BLACK);

        lineGc = lineCanvas.getGraphicsContext2D();
        lineGc.setStroke(Color.RED);
    }

    @FXML
    private void handleLeftAction(ActionEvent event) {
        this.arena.getTurtle().turnLeft();
        turtleGc.clearRect(0, 0, 400, 400);
        turtleGc.fillPolygon(
                this.arena.getTurtle().getShapePointsX(), this.arena.getTurtle().getShapePointsY(),3);

    }

    @FXML
    private void handleRightAction(ActionEvent event) {
        this.arena.getTurtle().turnRight();
        turtleGc.clearRect(0, 0, 400, 400);
        turtleGc.fillPolygon(
                this.arena.getTurtle().getShapePointsX(), this.arena.getTurtle().getShapePointsY(),3);
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        move();
    }


    private void move(){

        turtleGc.clearRect(0, 0, 400, 400);
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
}

