package pl.edu.agh.to2.kitkats.codinlearner.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertTrue;

public class EdgeTest {
    private Edge edge = null;
    private Vertex vertex1 = null;
    private Vertex vertex2 = null;

    @Before
    public void beforeEachTest(){
        vertex1 = Mockito.mock(Vertex.class);
        vertex1 = new Vertex(10.0, 10.0);
        vertex2 = Mockito.mock(Vertex.class);
        vertex2 = new Vertex(20.0, 20.0);
        edge = Mockito.mock(Edge.class);
        edge = new Edge(vertex1, vertex2);
    }

    @Test
    public void testEqualsMethod(){
        Vertex v1 = edge.getVertex1();
        Vertex v2 = edge.getVertex2();

        Edge newEdge = new Edge(v1, v2);

        assertTrue(edge.equals(newEdge));
    }

}
