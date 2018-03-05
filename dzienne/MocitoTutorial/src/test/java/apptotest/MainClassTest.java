package apptotest;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Krzysztof Podlaski on 05.03.2018.
 * based on tutorial http://www.vogella.com/tutorials/Mockito/article.html
 *
 */
public class MainClassTest {

    @Mock
    MyDatabase databaseMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void query() throws Exception {
        MainClass t  = new MainClass(databaseMock);
        when(t.query("SELECT * from Persons")).thenReturn(true);
        boolean check = t.query("SELECT * from Persons");
        assertTrue(check);
        verify(databaseMock).query("SELECT * from Persons");
    }

    @Test
    public void test1()  {
        //  create mock
        MyDatabase test = mock(MyDatabase.class);
        // define return value for method getUniqueId()
        when(test.getUniqueId()).thenReturn(43);
        // use mock in test....
        assertEquals(test.getUniqueId(), 43);
    }

    @Test
    public void testMoreThanOneReturnValue()  {
        Iterator<String> i= mock(Iterator.class);
        when(i.next()).thenReturn("Mockito")
                      .thenReturn("rocks")
                      .thenReturn(null);
        String result= i.next()+" "+i.next();
        //assert
        assertEquals("Mockito rocks", result);
        assertNull(i.next());
    }

    // this test demonstrates how to return values based on the input
    @Test
    public void testReturnValueDependentOnMethodParameter()  {
        Comparable<String> c= mock(Comparable.class);
        when(c.compareTo("Mockito")).thenReturn(1);
        when(c.compareTo("Eclipse")).thenReturn(2);
        //assert
        assertEquals(1, c.compareTo("Mockito"));
    }

    @Test
    public void testReturnValueInDependentOnMethodParameter()  {
        Comparable<Integer> c= mock(Comparable.class);
        when(c.compareTo(anyInt())).thenReturn(-1);
        //assert
        assertEquals(-1, c.compareTo(9));
        when(c.compareTo(any(Integer.class))).thenReturn(-1);
        //assert
        Integer I = 2;
        assertEquals(-1, c.compareTo(I));
    }

    @Test
    public void testReturnValueInDependentOnMethodParameter2()  {
        Comparable<List> c= mock(Comparable.class);
        when(c.compareTo(isA(List.class))).thenReturn(4);
        when(c.compareTo(isA(ArrayList.class))).thenReturn(0);
        when(c.compareTo(isA(Vector.class))).thenReturn(2);


        //assert
        Vector v = new Vector();
        assertEquals(2, c.compareTo(v));
        LinkedList v2 = new LinkedList();
        assertEquals(4, c.compareTo(v2));
    }
    @Test
    public void testLinkedListSpyCorrect() {
        // Lets mock a LinkedList
        List<String> list = new LinkedList<>();
        List<String> spy = spy(list);

        // You have to use doReturn() for stubbing
        doReturn("foo").when(spy).get(0);
        doReturn("AAA").when(spy).get(1);

        assertEquals("foo", spy.get(0));
        assertEquals("AAA", spy.get(1));
    }
}