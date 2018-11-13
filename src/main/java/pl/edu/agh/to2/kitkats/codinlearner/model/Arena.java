package pl.edu.agh.to2.kitkats.codinlearner.model;

import javafx.beans.property.*;

public class Arena {

    private ObjectProperty<Turtle> turtle;
    private FloatProperty width;
    private FloatProperty height;

    public Arena(float width, float height) {
        this.turtle = new SimpleObjectProperty<Turtle>(new Turtle(width, height));
        this.width = new SimpleFloatProperty(width);
        this.height = new SimpleFloatProperty(height);
    }

    public Turtle getTurtle() {
        return turtle.get();
    }

    public ObjectProperty<Turtle> turtleProperty() {
        return turtle;
    }

    public void setTurtle(Turtle turtle) {
        this.turtle.set(turtle);
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
}
