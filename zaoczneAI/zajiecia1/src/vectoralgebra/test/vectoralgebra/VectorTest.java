package vectoralgebra.test.vectoralgebra;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import vectoralgebra.Vector;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Krzysztof Podlaski on 04.03.2018.
 */
public class VectorTest {
    Vector x,y;

    @Before
    public void setUp() throws Exception {
        x = Vector.buildVector(new double[] {1,0} );
        y = Vector.buildVector(new double[] {0,1} );
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void scalarProductOfBasisVectors() throws Exception {
        double result = x.scalarProduct(x);
        assertEquals("[1,0]*[1,0] ",1.0, result,0.001);
        result = y.scalarProduct(y);
        assertEquals("[0,1]*[0,1] ",1.0, result,0.001);
        result = x.scalarProduct(y);
        assertEquals("[1,0]*[0,1] ",0, result,0.001);
    }

    @Test
    public void scalarProductOfBasisVectorwithRandom() throws Exception {
        Random r = new Random();
        Vector v = Vector.buildVector(new double[] {r.nextDouble()*10,r.nextDouble()*10 });
        double result = v.scalarProduct(x);
        assertEquals("random * x",v.getV()[0],result,0.001);
        result = v.scalarProduct(y);
        assertEquals("random * y",v.getV()[1],result,0.001);
    }

    @Test//(expected = java.lang.ArrayIndexOutOfBoundsException.class)
    public void scalarProductOfDifferenSizeVectors() throws Exception {
        Vector v = Vector.buildVector(new double[] {1,0,0 });
        double result = x.scalarProduct(v);
        assertEquals("v * x",1,result,0.001);
        result = v.scalarProduct(x);
        assertEquals("x * v",1,result,0.001);
    }

    @Test(expected = NullPointerException.class,timeout = 2000)
    public void scalarProductWithNull() throws Exception {
        double result = x.scalarProduct(null);
        assertEquals("v * x",1,result,0.001);
    }

    @Test(timeout = 2000)
    public void vectorLength() throws Exception {
        double result = x.length();
        assertEquals("|x|",1,result,0.001);
        result = y.length();
        assertEquals("|y|",1,result,0.001);
        Vector v = Vector.buildVector(new double[] {1,3,4});
        result = v.length();
        assertEquals("|[1,3,4]|",5.099,result,0.001);
    }

    //TODO test metody fabrycznej

    @Ignore
    @Test(timeout = 2000)
    public void vectorProduct() throws Exception {
        Vector v = x.vectorProduct(y);
        Vector expected = Vector.buildVector(new double[] {0,0,1});
        assertArrayEquals("x cross y",expected.getV(),v.getV(),0.001);
    }

}