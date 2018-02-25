package vectoralgebra.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vectoralgebra.Vector;

public class VectorTest {

	Vector a, b;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		a = new Vector();
		a.v = new double[] {1,0};
		b= new Vector();
		b.v =new double[] {0,1};
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	//scalar product of basis vectors
	public void testScalarProduct() {
		double result = a.scalarProduct(b);
		assertEquals("a* b", 0,result,0.001);
		result = a.scalarProduct(a);
		assertEquals("a* a", 1,result,0.001);
		result = b.scalarProduct(b);
		assertEquals("b* b", 1,result,0.001);
	}

	@Test
	public void testScalarProductDifferentVectorSizes(){
		Vector x = new Vector();
		x.v = new double[] {1,2,3};
		Vector y = new Vector();
		y.v = new double[] {1,2};
		double result = y.scalarProduct(x);
		assertEquals("y*x", 5, result,0.001);
		result = x.scalarProduct(y);
		assertEquals("x*y", 5, result,0.001);
		
	}
}
