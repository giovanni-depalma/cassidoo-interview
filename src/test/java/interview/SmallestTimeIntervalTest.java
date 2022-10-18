package interview;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SmallestTimeIntervalTest {

    private final SmallestTimeInterval f = new SmallestTimeInterval();

    @Test
    public void testMaxRange(){
        List<String> seq = List.of("00:00", "23:59");
        int expected = 24*60-1;
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }

    @Test
    public void testSameElement(){
        List<String> seq = List.of("01:00", "01:00", "02:00");
        int expected = 0;
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }

    @Test
    public void testMinDistance(){
        List<String> seq = List.of("01:00", "08:15", "20:06", "11:30", "13:45", "14:10", "20:05", "23:59");
        int expected = 1;
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }

    @Test
    public void testSimple(){
        List<String> seq = List.of("01:00", "08:15", "11:30", "13:45", "14:10", "20:05");
        int expected = 25;
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }


    @Test
    public void testEmpty(){
        List<String> seq = List.of();
        int expected = -1;
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }

    @Test
    public void testOneElement(){
        List<String> seq = List.of("01:00");
        int expected = -1;
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }

    @Test
    public void testInvalidElement(){
        List<String> seq = List.of("01:00","24:00");
        int expected = -1;
        int actual = f.apply(seq);
        assertEquals(expected, actual);
    }



}
