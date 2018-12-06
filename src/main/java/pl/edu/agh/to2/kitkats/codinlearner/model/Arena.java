package pl.edu.agh.to2.kitkats.codinlearner.model;

import javafx.beans.property.*;

import static java.lang.Math.max;

public class Arena {

    private ObjectProperty<Cursor> cursor;
    private FloatProperty width;
    private FloatProperty height;
    private MoveGraph moveGraph;

    public Arena(float width, float height, float cursorLength, float cursotWidth) {
        this.cursor = new SimpleObjectProperty<Cursor>(
                new Cursor(width, height, cursorLength, cursotWidth, this, 50.0f));
        this.width = new SimpleFloatProperty(width);
        this.height = new SimpleFloatProperty(height);
        moveGraph = new MoveGraph();

    }

    public void clearMoveGraph(){
        moveGraph = new MoveGraph();
    }

    public boolean canMove(double newX, double newY){
        double maxSize = max(this.cursor.get().length, this.cursor.get().width);
        return (newX >= 0.0 + maxSize && newX <= this.width.get() - maxSize &&
                newY >= 0.0 + maxSize && newY <= this.height.get() - maxSize);
    }

    public Cursor getCursor() {
        return cursor.get();
    }

    public ObjectProperty<Cursor> cursorProperty() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor.set(cursor);
    }

    public float getWidth() {
        return width.get();
    }

    public FloatProperty widthProperty() {
        return width;
    }

    public void setWidth(float width) {
        this.width.set(width);
    }

    public float getHeight() {
        return height.get();
    }

    public FloatProperty heightProperty() {
        return height;
    }

    public void setHeight(float height) {
        this.height.set(height);
    }

    public MoveGraph getMoveGraph() {
        return moveGraph;
    }
}
