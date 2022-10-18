package interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This week’s question:
 * Given a string that represents items as asterisks (*) and compartment walls as pipes (|),
 * a start index, and an end index, return the number of items in a closed compartment.
 *
 * Extra credit: What if you had multiple pairs of start and end indices? You can do it in O(n) time!
 *
 * SOLUTION
 *  - loop of every character counting the number of items in a closed compartment (starting from 0)
 *  - during the loop counting the "handicap" starting from the "i-th" index
 *
 * EXAMPLE
 *
 * str = '|**|*|*'
 *
 * totals:      [0, 0, 0, 2, 2, 3, 3]
 * handicapIdx: [0, 1, 1, 1, 2, 2, 3]  every wall is a potential increment of handicap
 * handicap:    [0, 2, 3, 3] effective handicap value
 *
 * result = totals[end-1] - handicap[handicapIdx[start]]
 * start: 1, end: 6
 *      =>  totals[6]-handicap[handicapIdx[1]]
 *      =>  totals[6] - handicap[2]
 *      =>  3 - 2 = 1
 */
public class ContainedItems {
    private static final char ITEM = '*';
    private static final char WALL = '|';

    private int[] totals;
    private int[] handicapIdx;
    private List<Integer> handicap;

    public ContainedItems(String str) {
        this.consume(str);
    }

    private void consume(String str){
        int size = str.length();
        totals = new int[size];
        handicapIdx = new int[size];
        handicap = new ArrayList<>();

        int currentTotal = 0;
        int wall = 0;
        int currentItems = 0;
        for(int i = 0; i < size; i++){
            char c = str.charAt(i);
            handicapIdx[i] = wall;//change AFTER every wall, so it's executed before wall's increment
            switch (c){
                case WALL -> {
                    wall++;
                    currentTotal += currentItems;//sum closed items
                    handicap.add(currentTotal);//set last handicap as currenTotal
                    currentItems = 0;
                }
                case ITEM -> {
                    if(wall > 0){
                        currentItems++;
                    }
                }
            }
            totals[i] = currentTotal;
        }
        if(currentItems > 0){
            handicap.add(currentTotal);//set last handicap value
        }
    }

    public int getItems(int start, int end) {
        return getItems(List.of(new Pair(start, end))).get(0);
    }

    private int limitIndex(int index, int max){
        return index < max ? index : max -1;
    }

    public List<Integer> getItems(List<Pair> indices) {
        return indices.stream().map(p -> {
            int end = limitIndex(p.end - 1, totals.length);
            int start = limitIndex(p.start, totals.length);
            int currentHandicap = handicap.get(handicapIdx[start]);
            return totals[end] - currentHandicap;
        }).toList();
    }

    public void debug(){
        System.out.println("totals: " + Arrays.toString(totals));
        System.out.println("handicapIdx: " + Arrays.toString(handicapIdx));
        System.out.println("handicap: " + handicap);
    }


    record Pair(int start, int end) {
    }

    public static void main(String[] args) {
        var f = new ContainedItems("|**|*|*");
        f.debug();
        System.out.println(f.getItems(0, 5));
        System.out.println(f.getItems(0, 6));
        System.out.println(f.getItems(1, 7));

        System.out.println(f.getItems(List.of(new Pair(0, 5), new Pair(0, 6), new Pair(1, 7))));
    }
}
