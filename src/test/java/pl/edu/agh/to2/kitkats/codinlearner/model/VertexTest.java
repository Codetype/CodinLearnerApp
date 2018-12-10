package pl.edu.agh.to2.kitkats.codinlearner.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertEquals;

public class VertexTest {
    private Vertex vertex = null;

    @Before
    public void beforeEachTest(){
        vertex = Mockito.mock(Vertex.class);
        vertex = new Vertex(1.0, 2.0);
    }

    @Test
    public void testTransposeMethod(){
        vertex.transpose(1.0,2.0);
        double x1 = vertex.getX();
        double y1 = vertex.getY();
        assertEquals(2.0, x1);
        assertEquals(4.0, y1);
    }

}
