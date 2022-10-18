package interview;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestSubSeqTest {

    private final LongestSubSeq f = new LongestSubSeq();

    @Test
    public void test1(){
        List<Integer> seq = List.of(1, 9, 87, 3, 10, 4, 20, 2, 45);
        int expected = 4;//List.of(1, 3, 4, 2);
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }

    @Test
    public void test2(){
        List<Integer> seq = List.of(36, 41, 56, 35, 91, 33, 34, 92, 43, 37, 42  );
        int expected = 5;//List.of(36, 35, 33, 34, 37);
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }

    @Test
    public void testEmpty(){
        List<Integer> seq = List.of();
        int expected = 0;
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }

    @Test
    public void testNotConsecutive(){
        List<Integer> seq = List.of(1,10,20,30,40,50);
        int expected = 1;
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }

    @Test
    public void testMultipleResult(){
        List<Integer> seq = List.of(1,10,11,12,21,22,23,50);
        int expected = 3; //List.of(10,11,12);
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }
}
