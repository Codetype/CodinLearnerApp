package pl.edu.agh.to2.kitkats.codinlearner.model;

import javafx.beans.property.*;

public class Arena {

    private ObjectProperty<Cursor> cursor;
    private FloatProperty width;
    private FloatProperty height;

    public Arena(float width, float height) {
        this.cursor = new SimpleObjectProperty<Cursor>(new Cursor(width, height, this, 50.0f));
        this.width = new SimpleFloatProperty(width);
        this.height = new SimpleFloatProperty(height);
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
}
