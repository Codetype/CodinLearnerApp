package pl.edu.agh.to2.kitkats.codinlearner.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class DoublePointTest {
    private DoublePoint doublePoint = null;

    @Before
    public void beforeEachTest(){
        doublePoint = Mockito.mock(DoublePoint.class);
        doublePoint = new DoublePoint(0.0, 0.0);
    }

    @Test
    public void testGetters() {
        doublePoint.setX(10.0);
        doublePoint.setY(20.0);

        double x1 = doublePoint.getX();
        double y1 = doublePoint.getY();

        assertEquals(x1, 10.0, 0.0);
        assertEquals(20.0, y1, 0.0);
    }

}
