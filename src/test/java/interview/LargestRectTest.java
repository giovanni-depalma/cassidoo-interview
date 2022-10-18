package interview;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LargestRectTest {

    @Test
    public void test1(){
        int[][] islands = new int[][]{
                {0,0,0,1,0},
                {1,1,0,0,1},
                {1,1,0,0,0},
                {0,0,1,0,0}
        };
        LargestRect.Rect expected = new LargestRect.Rect(2,2,4);
        LargestRect.Rect actual = new LargestRect().apply(islands);
        assertEquals(expected, actual);
    }

    @Test
    public void test2(){
        int[][] islands = new int[][]{
                {0,0,0,1,0},
                {1,1,1,0,1},
                {1,1,1,0,0},
                {0,0,1,0,0}
        };
        LargestRect.Rect expected = new LargestRect.Rect(3,2,6);
        LargestRect.Rect actual = new LargestRect().apply(islands);
        assertEquals(expected, actual);
    }

    @Test
    public void test3(){
        int[][] islands = new int[][]{
                {0,0,0,1,0,0,1,1},
                {1,1,1,0,1,1,1,1},
                {1,1,1,0,1,1,1,1},
                {0,0,1,0,1,1,1,1}
        };
        LargestRect.Rect expected = new LargestRect.Rect(4,3,12);
        LargestRect.Rect actual = new LargestRect().apply(islands);
        assertEquals(expected, actual);
    }

    @Test
    public void testNull(){
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> new LargestRect().apply(null)
        );
    }

    @Test
    public void testEmpty(){
        LargestRect.Rect expected = new LargestRect.Rect(-1,-1,-1);
        LargestRect.Rect actual = new LargestRect().apply(new int[][]{});
        assertEquals(expected, actual);
    }
}
