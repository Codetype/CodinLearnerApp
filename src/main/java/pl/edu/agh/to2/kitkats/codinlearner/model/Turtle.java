package pl.edu.agh.to2.kitkats.codinlearner.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.util.Arrays;

public class Turtle {

    private float x;
    private float y;

    private List<Double> shapePointsX;
    private List<Double> shapePointsY;
    private Direction currentDirection = Direction.EAST;

    public Turtle(float x, float y) {
        //start coordinates, the middle point of the arena
        this.x = x/2;
        this.y = y/2;
       //three points of triangular shape
        shapePointsX = new ArrayList<>();
        shapePointsY = new ArrayList<>();
        setShapePoints();
    }

    public void turnLeft(){
        currentDirection = currentDirection.onTheLeft();
        setShapePoints();
    }

    public void turnRight(){
        currentDirection = currentDirection.onTheRight();
        setShapePoints();
    }

    private void setShapePoints(){
        switch (currentDirection){
            case EAST:
                shapePointsX.clear();
                shapePointsX.add((double)this.x);
                shapePointsX.add((double)this.x);
                shapePointsX.add(this.x + 21.0);
                shapePointsY.clear();
                shapePointsY.add(this.y + 8.0);
                shapePointsY.add(this.y - 8.0);
                shapePointsY.add((double)this.y);
                break;
            case NORTH:
                shapePointsX.clear();
                shapePointsX.add(this.x - 8.0);
                shapePointsX.add(this.x + 8.0);
                shapePointsX.add((double)this.x);
                shapePointsY.clear();
                shapePointsY.add((double)this.y);
                shapePointsY.add((double)this.y);
                shapePointsY.add(this.y - 21.0);
                break;
            case SOUTH:
                shapePointsX.clear();
                shapePointsX.add(this.x - 8.0);
                shapePointsX.add(this.x + 8.0);
                shapePointsX.add((double)this.x);
                shapePointsY.clear();
                shapePointsY.add((double)this.y);
                shapePointsY.add((double)this.y);
                shapePointsY.add(this.y + 21.0);
                break;
            case WEST:
                shapePointsX.clear();
                shapePointsX.add((double)this.x);
                shapePointsX.add((double)this.x);
                shapePointsX.add(this.x - 21.0);
                shapePointsY.clear();
                shapePointsY.add(this.y + 8.0);
                shapePointsY.add(this.y - 8.0);
                shapePointsY.add((double)this.y);
                break;
        }

    }

    public void move(){
        float moveStep = 50;
        float xMove = 0;
        float yMove = 0;
        switch (currentDirection){
            case EAST:
                xMove = moveStep;
                yMove = 0;
                break;
            case NORTH:
                xMove = 0;
                yMove = -moveStep;
                break;
            case SOUTH:
                xMove = 0;
                yMove = moveStep;
                break;
            case WEST:
                xMove = -moveStep;
                yMove = 0;
                break;

        }
        if((this.x + xMove < 21.0f || this.x + xMove >379.0f) || (this.y + yMove < 21.0f || this.y + yMove >379.0f)) return;
        this.x = this.x + xMove;
        this.y = this.y + yMove;
        setShapePoints();
    }

    public double[] getShapePointsX() {
        return shapePointsX.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public double[] getShapePointsY() {
        return shapePointsY.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public final float getX() {
        return this.x;
    }

    public final void setX(float x) {
        this.x = x;
    }

    public final float getY() {
        return this.y;
    }

    public final void setY(float y) {
        this.y = y;
    }

}
