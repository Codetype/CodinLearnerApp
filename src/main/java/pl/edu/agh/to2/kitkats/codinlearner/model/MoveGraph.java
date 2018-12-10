package pl.edu.agh.to2.kitkats.codinlearner.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MoveGraph {

    private List<Vertex> vertexList;
    private List<Edge> edgeList;
    public final double eps = 0.0001;

    public MoveGraph() {
        this.vertexList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void addVertex(double fromX, double fromY, double toX, double toY){
        System.out.println("In add vertex");
        Vertex newVertex = new Vertex(toX, toY);
        Vertex oldVertex = new Vertex(fromX, fromY);

        if(!this.vertexList.contains(newVertex)){
            this.vertexList.add(newVertex);
        }

        if(!this.vertexList.contains(oldVertex)){
            this.vertexList.add(oldVertex);
        }

        Vertex oldEdgeVertex = new Vertex(fromX, fromY);
        Vertex newEdgeVertex = new Vertex(toX, toY);
        Edge newEdge = new Edge(oldEdgeVertex, newEdgeVertex);
        System.out.println(!this.edgeList.contains(newEdge));
        System.out.println("From x : " + fromX);
        System.out.println("To x : " + toX);
        System.out.println("From y : " + fromY);
        System.out.println("To y : " + toY);
        if(!this.edgeList.contains(newEdge) && ( fromX != toX || fromY != toY)) {
            System.out.println("Adding new edge");
            this.edgeList.add(newEdge);
        }
    }


    public void removeVertex(double fromX, double fromY, double toX, double toY){
        Vertex vertexToDelete = new Vertex(toX, toY);
        this.vertexList.remove(vertexToDelete);
        Vertex vertexInEdge = new Vertex(fromX, fromY);
        Edge edge = new Edge(vertexInEdge, vertexToDelete);
        this.edgeList.remove(edge);
    }



    public DoublePoint getTheMostLeftBottomPoint(){
        double minX = 1000, minY = 1000;
        for(Vertex vert : this.vertexList){
            if((vert.getX() < minX) || (Math.abs(vert.getX() - minX) < eps && vert.getY() < minY)){
                minX = vert.getX();
                minY = vert.getY();
            }
        }
        return new DoublePoint(minX, minY);
    }


    public boolean isTheSame(MoveGraph moveGraph){

        DoublePoint minPoint = moveGraph.getTheMostLeftBottomPoint();
        double minX = minPoint.x;
        double minY = minPoint.y;

        DoublePoint thisMinPoint = this.getTheMostLeftBottomPoint();
        double thisMinX = thisMinPoint.x;
        double thisMinY = thisMinPoint.y;

        double difX = minX - thisMinX;
        double difY = minY - thisMinY;


        transpose(difX, difY);
        System.out.println("THIS GRAPH");
        printGraph();

        System.out.println("\nTASK GRAPH");
        moveGraph.printGraph();

        Collection vertexCollection = this.vertexList;
        Collection edgeCollection = this.edgeList;

        for(Vertex vert : moveGraph.vertexList){
            if(this.vertexList.contains(vert)) this.vertexList.remove(vert);
            else return false;
        }

        for(Edge edge : moveGraph.edgeList){
            if(this.edgeList.contains(edge)) this.edgeList.remove(edge);
            else return false;
        }

        return this.vertexList.isEmpty() && this.edgeList.isEmpty();
    }


    public void transpose(double x, double y){
        for(Vertex vert : this.vertexList)
            vert.transpose(x, y);

        for(Edge edge : this.edgeList) {
            edge.getVertex1().transpose(x, y);
            edge.getVertex2().transpose(x, y);
        }

    }

    public void printGraph(){
        for(Vertex vert : this.vertexList){
            System.out.println(vert.toString());
        }

        for(Edge edge : this.edgeList){
            System.out.println(edge.toString());
        }
    }

}
