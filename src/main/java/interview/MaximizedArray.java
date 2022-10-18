package interview;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * This week’s question:
 * Given two integer arrays of size n, return a new array of size n such that n consists of only unique elements and the sum of all its elements is maximum.
 */
public class MaximizedArray {

    public static int[] maximizedArray(int[] arr1, int[] arr2) {
        var treeSet = new TreeSet<Integer>();
        Arrays.stream(arr1).forEach(treeSet::add);
        Arrays.stream(arr2).forEach(n -> {
            if (treeSet.add(n))
                treeSet.pollFirst();
        });
        return treeSet.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(maximizedArray(new int[]{7, 4, 10, 0, 1}, new int[]{9, 7, 2, 3, 6})));
        System.out.println(Arrays.toString(maximizedArray(new int[]{}, new int[]{})));
    }
}
