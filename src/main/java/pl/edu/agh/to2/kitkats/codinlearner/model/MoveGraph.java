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
        Vertex newVertex = new Vertex(toX, toY);
        Vertex oldVertex = new Vertex(fromX, fromY);

        if(!this.vertexList.contains(newVertex)){
            this.vertexList.add(newVertex);
        }

        Vertex newEdgeVertex = new Vertex(toX, toY);
        Edge newEdge = new Edge(oldVertex, newEdgeVertex);
        if(!this.edgeList.contains(newEdge) && ( fromX != toX || fromY != toY))
            this.edgeList.add(newEdge);
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
        printGraph();

        DoublePoint minPoint = moveGraph.getTheMostLeftBottomPoint();
        double minX = minPoint.x;
        double minY = minPoint.y;

        DoublePoint thisMinPoint = this.getTheMostLeftBottomPoint();
        double thisMinX = thisMinPoint.x;
        double thisMinY = thisMinPoint.y;

        double difX = minX - thisMinX;
        double difY = minY - thisMinY;
        System.out.println(difX);
        System.out.println(difY);

        transpose(difX, difY);
        printGraph();

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

        return true;
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
