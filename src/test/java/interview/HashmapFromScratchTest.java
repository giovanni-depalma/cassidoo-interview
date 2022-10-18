package interview;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HashmapFromScratchTest {

    @Test
    public void testFound(){
        HashmapFromScratch<Integer,Integer> map = new HashmapFromScratch<>();
        int expected = 1000;
        int key = 0;
        map.put(key,expected);
        int actual = map.get(key);
        assertEquals(expected, actual);
    }

    @Test
    public void testNotFound(){
        HashmapFromScratch<Integer,Integer> map = new HashmapFromScratch<>();
        Integer expected = null;
        int key = 0;
        Integer actual = map.get(key);
        assertEquals(expected, actual);
    }

    @Test
    public void testPutAndDelete(){
        HashmapFromScratch<Integer,Integer> map = new HashmapFromScratch<>();
        final int max = 100;
        for(int i = 0; i < max; i++){
            map.put(i, 1500+i);
        }
        for(int i = 0; i < max; i++){
            assertEquals(1500+i, map.get(i));
        }
        for(int i = 0; i < max; i++){
            map.remove(i);
        }
        for(int i = 0; i < max; i++){
            assertNull( map.get(i));
        }
    }

    @Test
    public void testPutAndDeleteBadHashcode(){
        HashmapFromScratch<KeyWithBadHashcode,Integer> map = new HashmapFromScratch<>();
        final int max = 100;
        for(int i = 0; i < max; i++){
            map.put(new KeyWithBadHashcode(i), 1500+i);
        }
        for(int i = 0; i < max; i++){
            assertEquals(1500+i, map.get(new KeyWithBadHashcode(i)));
        }
        for(int i = 0; i < max; i++){
            map.remove(new KeyWithBadHashcode(i));
        }
        for(int i = 0; i < max; i++){
            assertNull( map.get(new KeyWithBadHashcode(i)));
        }
    }

    @AllArgsConstructor
    public class KeyWithBadHashcode{

        final int value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            KeyWithBadHashcode that = (KeyWithBadHashcode) o;
            return value == that.value;
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }
}
