package pl.edu.agh.to2.kitkats.codinlearner.model;

import java.util.Objects;

public class Edge {

    private Vertex vertex1;
    private Vertex vertex2;

    public Edge(Vertex vertex1, Vertex vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public void setVertex1(Vertex vertex1) {
        this.vertex1 = vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public void setVertex2(Vertex vertex2) {
        this.vertex2 = vertex2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return (vertex1.equals(edge.vertex1) &&
                    vertex2.equals(edge.vertex2)) ||
                ((vertex1.equals(edge.vertex2)) &&
                    (vertex2.equals(edge.vertex1)));
    }

    @Override
    public int hashCode() {

        return Objects.hash(vertex1, vertex2);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "vertex1=" + vertex1 +
                ", vertex2=" + vertex2 +
                '}';
    }
}
