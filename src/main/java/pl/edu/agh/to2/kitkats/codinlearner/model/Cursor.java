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

public class Cursor {

    private double x;
    private double y;
    private Arena arena;
    private Vector2D alongVector;
    private Vector2D acrossVector;
    private Vector2D moveVector;
    public final float length;
    public final float width;

    public float getMoveStep() {
        return moveStep;
    }

    private float moveStep;

    private List<Double> shapePointsX;
    private List<Double> shapePointsY;

    public Cursor(float x, float y, float length, float width, Arena arena, float moveStep) {
        //start coordinates, the middle point of the arena
        this.x = x/2;
        this.y = y/2;
        //initialization of vectors
        alongVector = new Vector2D(length,0);
        acrossVector = new Vector2D(0, width);
        moveVector = new Vector2D(moveStep, 0);
        //three points of triangular shape
        shapePointsX = new ArrayList<>();
        shapePointsY = new ArrayList<>();
        setShapePoints();

        this.length = length;
        this.width = width;
        this.moveStep = moveStep;
        this.arena = arena;
    }

    public void setArenaStartPoints(){
        this.arena.setStartX(this.x);
        this.arena.setStartY(this.y);
    }

    private void rotateLeft(double angle){
        this.alongVector = this.alongVector.rotate(Angle.toRadians(-angle));
        this.acrossVector = this.acrossVector.rotate(Angle.toRadians(-angle));
        this.moveVector = this.moveVector.rotate(Angle.toRadians(-angle));

        setShapePoints();
    }

    private void rotateRight(double angle){
        this.alongVector = this.alongVector.rotate(Angle.toRadians(angle));
        this.acrossVector = this.acrossVector.rotate(Angle.toRadians(angle));
        this.moveVector = this.moveVector.rotate(Angle.toRadians(angle));

        setShapePoints();
    }

    private void setShapePoints(){
            shapePointsX.clear();
            shapePointsX.add(this.x + acrossVector.getX());
            shapePointsX.add(this.x - acrossVector.getX());
            shapePointsX.add(this.x + alongVector.getX());
            shapePointsY.clear();
            shapePointsY.add(this.y + acrossVector.getY());
            shapePointsY.add(this.y - acrossVector.getY());
            shapePointsY.add(this.y + alongVector.getY());
    }

    public void move(int mode, ParameterizedCommand command){
        switch (command.getCommand()){
            case LEFT: rotateLeft(command.getParameter()); break;
            case RIGHT: rotateRight(command.getParameter()); break;
            case FORWARD:
                System.out.println("GO " + command.getParameter());
                for(int i=0; i<command.getParameter(); i++) {
                    double oldX = this.x;
                    double oldY = this.y;
                    move(false);
                    double newX = this.x;
                    double newY = this.y;
                    if(mode == 1)
                        this.arena.getMoveGraph().addVertex(oldX, oldY, newX, newY);
                    if(mode == -1)
                        this.arena.getMoveGraph().removeVertex(oldX, oldY, newX, newY);
                }
                break;
            case BACK:
                for(int i=0; i<command.getParameter(); i++){
                    double oldX = this.x;
                    double oldY = this.y;
                    move(true);
                    double newX = this.x;
                    double newY = this.y;
                    if(mode == 1)
                        this.arena.getMoveGraph().addVertex(oldX, oldY, newX, newY);
                    if(mode == -1)
                        this.arena.getMoveGraph().removeVertex(oldX, oldY, newX, newY);
                }
            break;
            default: break;
        }

        setShapePoints();
    }

    public void moveBack(ParameterizedCommand command){
        move(-1, reverseCommand(command));
    }

    private void move(boolean backward){
        double xMove = this.moveVector.getX();
        double yMove = this.moveVector.getY();

        if(backward){
            xMove = -xMove;
            yMove = -yMove;
        }

        if(!this.arena.canMove(this.x + xMove, this.y + yMove)) return;
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

    private ParameterizedCommand reverseCommand(ParameterizedCommand command){
        Command newCommand = command.getCommand().oppositeCommand();
        int parameter = command.getParameter();
        return new ParameterizedCommand(newCommand, parameter);
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
