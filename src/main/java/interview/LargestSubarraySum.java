package interview;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Given an unsorted array of integers and a number n, find the subarray of length n that has the largest sum.
 * Example:
 * $ largestSubarraySum([3,1,4,1,5,9,2,6], 3)
 * $ [9, 2, 6]
 *
 * Solution:
 * Generate a stream of all sublist, example
 * [3,1,4], [1,4,1], [4,1,5], [1,5,9]...
 * and then look for max sum
 */
public class LargestSubarraySum {
    record ListWithSum(List<Integer> list, int sum) {
    }

    public static List<Integer> largestSubarraySum(List<Integer> list, int length) {
        return IntStream.range(0, list.size()).mapToObj(i -> {
                    int toIndex = Math.min(list.size(), i + length);//check to not outbound
                    List<Integer> sublist = IntStream.range(i, toIndex).mapToObj(list::get).toList();
                    int sum = sublist.stream().reduce(Integer::sum).orElse(0);
                    return new ListWithSum(sublist, sum);
                })
                .max(Comparator.comparing(ListWithSum::sum))
                .map(ListWithSum::list)
                .orElse(List.of());
    }

    public static void main(String[] args) {
        System.out.println(largestSubarraySum(List.of(3, 1, 4, 1, 5, 9, 2, 6), 3));
        System.out.println(largestSubarraySum(List.of(), 3));
    }
}
