package pl.edu.agh.to2.kitkats.codinlearner.model;

import java.util.Objects;

public class Vertex {

    private double x;
    private double y;
    public final double eps = 0.0001;

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void transpose(double x, double y){
        this.x = this.x + x;
        this.y = this.y + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Math.abs(vertex.x - x) < eps &&
                Math.abs(vertex.y - y) < eps;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
