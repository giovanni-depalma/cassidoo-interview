package interview;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * You are given a list of positive integers which represents some range of integers which has been truncated.
 * Find the missing bits, insert ellipses to show that that part has been truncated, and print it.
 * If the consecutive values differ by exactly two, then insert the missing value.
 */
public class MissingBits{

  public static List<?> missingBits(int [] values){
    return Stream.concat(
        Stream.of(values[0]),
        IntStream.range(1, values.length).boxed().flatMap(i -> mapper(values[i], values[i -1]))
    ).toList();
  }

  public static Stream<?> mapper(int value, int prevValue){
    return switch (value - prevValue){
      case 1 -> Stream.of(value);
      case 2 -> Stream.of(value-1,value);
      default -> Stream.of("...",value);
    };
  }

  public static void main(String[] args) {
    System.out.println(missingBits(new int[]{1,2,3,4,20,21,22,23}));
    System.out.println(missingBits(new int[]{1,2,3,5,6}));
    System.out.println(missingBits(new int[]{1,3,20,27}));
  }
}
