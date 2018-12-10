package pl.edu.agh.to2.kitkats.codinlearner.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MoveGraphTest {
    private MoveGraph moveGraph = null;
    private Vertex vertex = null;

    @Before
    public void beforeEachTest(){
        moveGraph = Mockito.mock(MoveGraph.class);
        moveGraph = new MoveGraph();
        vertex = Mockito.mock(Vertex.class);
        vertex = new Vertex(0.0, 0.0);
    }

    @Test
    public void testVertexAdding(){
        moveGraph.addVertex(0.0, 0.0, 3.0, 4.0);

        List<Vertex> vertexList = moveGraph.getVertexList();
        vertex = vertexList.get(0);

        assertEquals(3.0 ,vertex.getX(), 0.0);
        assertEquals(4.0 , vertex.getY(), 0.0);
    }
}
