package interview;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContainedItemsTest {

    @Test
    public void test1() {
        var f = new ContainedItems("|**|*|*");
        var expected = List.of(2, 3, 1, 0);
        var actual = f.getItems(List.of(
                new ContainedItems.Pair(0, 5),
                new ContainedItems.Pair(0, 6),
                new ContainedItems.Pair(1, 7),
                new ContainedItems.Pair(5, 7))
        );
        assertEquals(expected, actual);
    }

    @Test
    public void test2() {
        var f = new ContainedItems("|**|*|");
        var expected = List.of(2, 3, 1);
        var actual = f.getItems(List.of(
                new ContainedItems.Pair(0, 5),
                new ContainedItems.Pair(0, 6),
                new ContainedItems.Pair(1, 7))
        );
        assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        var f = new ContainedItems("||**|*|*");
        var expected = List.of(2, 3, 1);
        var actual = f.getItems(List.of(
                new ContainedItems.Pair(0, 5),
                new ContainedItems.Pair(0, 7),
                new ContainedItems.Pair(2, 7))
        );
        assertEquals(expected, actual);
    }

    @Test
    public void test4() {
        var f = new ContainedItems("**|*|**||");
        var expected = List.of(3, 3, 1);
        var actual = f.getItems(List.of(
                new ContainedItems.Pair(0, 10),
                new ContainedItems.Pair(2, 10),
                new ContainedItems.Pair(2,5))
        );
        assertEquals(expected, actual);
    }
}
