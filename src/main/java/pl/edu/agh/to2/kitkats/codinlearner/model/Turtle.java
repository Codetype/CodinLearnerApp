package pl.edu.agh.to2.kitkats.codinlearner.model;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.math.Vector2D;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.util.Arrays;

public class Turtle {

    private double x;
    private double y;
    private Arena arena;
    private Vector2D alongVector;
    private Vector2D acrossVector;
    private Vector2D moveVector;

    public float getMoveStep() {
        return moveStep;
    }

    private float moveStep;

    private List<Double> shapePointsX;
    private List<Double> shapePointsY;

    public Turtle(float x, float y, Arena arena, float moveStep) {
        //start coordinates, the middle point of the arena
        this.x = x/2;
        this.y = y/2;
        //initialization of vectors
        alongVector = new Vector2D(21,0);
        acrossVector = new Vector2D(0, 8);
        moveVector = new Vector2D(moveStep, 0);
       //three points of triangular shape
        shapePointsX = new ArrayList<>();
        shapePointsY = new ArrayList<>();
        setShapePoints();

        this.moveStep = moveStep;
        this.arena = arena;
    }

    public void turnLeft(){
        rotateLeft(90.0);
        setShapePoints();
    }

    public void turnRight(){
        rotateRight(90.0);
        setShapePoints();
    }

    public void rotateLeft(double angle){
        this.alongVector = this.alongVector.rotate(Angle.toRadians(-angle));
        this.acrossVector = this.acrossVector.rotate(Angle.toRadians(-angle));
        this.moveVector = this.moveVector.rotate(Angle.toRadians(-angle));
    }

    public void rotateRight(double angle){
        this.alongVector = this.alongVector.rotate(Angle.toRadians(angle));
        this.acrossVector = this.acrossVector.rotate(Angle.toRadians(angle));
        this.moveVector = this.moveVector.rotate(Angle.toRadians(angle));
    }

    private void setShapePoints(){
            shapePointsX.clear();
            shapePointsX.add((double)this.x + acrossVector.getX());
            shapePointsX.add((double)this.x - acrossVector.getX());
            shapePointsX.add(this.x + alongVector.getX());
            shapePointsY.clear();
            shapePointsY.add(this.y + acrossVector.getY());
            shapePointsY.add(this.y - acrossVector.getY());
            shapePointsY.add((double)this.y + alongVector.getY());
    }

    public void move(List<Command> commands){
        for(Command command : commands){
            switch (command){
                case LEFT: turnLeft(); break;
                case RIGHT: turnRight(); break;
                case FORWARD: move(); break;
                default: break;
            }
        }
    }

    public void move(){
        double xMove = this.moveVector.getX();
        double yMove = this.moveVector.getY();

        if((this.x + xMove < 21.0f || this.x + xMove > this.arena.getWidth() - 21.0f) ||
                (this.y + yMove < 21.0f || this.y + yMove >this.arena.getHeight() - 21.0f)) return;
        this.x = this.x + xMove;
        this.y = this.y + yMove;
        setShapePoints();
    }

    public void reset() {
        x = arena.getWidth() / 2;
        y = arena.getHeight() / 2;
        alongVector = new Vector2D(21,0);
        acrossVector = new Vector2D(0, 8);
        moveVector = new Vector2D(moveStep, 0);

        setShapePoints();
    }

    public double[] getShapePointsX() {
        return shapePointsX.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public double[] getShapePointsY() {
        return shapePointsY.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public final double getX() {
        return this.x;
    }

    public final void setX(double x) {
        this.x = x;
    }

    public final double getY() {
        return this.y;
    }

    public final void setY(double y) {
        this.y = y;
    }

}
