package interview;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * This week’s question:
 * Given an int array coins and an int amount, return an array of coins that add
 * up to amount (and an empty array if it’s an impossible combination).
 * 
 * Solution:
 * depth-first search (preorder) in tree of coins builded lazily, preferring larger coins.
 * 
 * For example, with coins [2, 3, 5, 7] and amount 17
 * root
 * ├─ [7]
 * │..├─ [7,7]
 * │.....├─ [7,7,7]
 * │.....├─ [7,7,5]
 * │.....├─ [7,7,3] ✓
 * 
 * with coins [2, 3, 6, 14] and amount 15
 * root
 * ├─ [14]
 * │..├─ [14,14]
 * │..├─ [14,6]
 * │..├─ [14,3]
 * │..├─ [14,2]
 * ├─ [6]
 * │..├─ [6,14]
 * │..├─ [6,6]
 * │.....├─ [6,6,14]
 * │.....├─ [6,6,6]
 * │.....├─ [6,6,3] ✓
 */
public class CoinCombo {

    public static int[] coinCombo(int[] coins, int amount) {
        int[] sortedCoins = Arrays.stream(coins).filter(c -> c <= amount).sorted().toArray();
        Deque<Coins> stack = new ArrayDeque<>();
        Coins root = new Coins(new int[]{}, 0);
        stack.add(root);
        while (!stack.isEmpty()) {
            Coins current = stack.pop();
            //System.out.println(current);
            if (current.total == amount)
                return Arrays.stream(current.coins()).sorted().toArray();
            else if (current.total < amount) {
                //push in order into stack, processing follows "Last In First Out" property
                for (int nextCoin : sortedCoins) {
                    stack.push(current.addCoin(nextCoin));
                }
            }
        }
        return root.coins;
    }

    public static void print(int[] coins) {
        System.out.println(Arrays.toString(coins));
    }

    record Coins(int[] coins, int total) {
        public Coins addCoin(int nextCoin) {
            int newSize = this.coins.length+1;
            int[] clone = Arrays.copyOf(this.coins, newSize);
            clone[newSize-1] = nextCoin;
            return new Coins(clone, this.total + nextCoin);
        }

        @Override
        public String toString() {
            return "Coins [coins="+ Arrays.toString(coins)+", total="+total+"]";
        }
    }

    public static void main(String[] args) {
        print(coinCombo(new int[] { 2, 3, 5, 7 }, 17)); // [3,7,7]
        print(coinCombo(new int[] { 2, 3, 5 }, 11)); // [3,3,5]
        print(coinCombo(new int[] { 2, 3, 6, 14 }, 15)); // [3, 6, 6]
        print(coinCombo(new int[] { 2, 3, 5, 7 }, 5)); // [5]
        print(coinCombo(new int[] { 5, 7 }, 6)); // []
        print(coinCombo(new int[] { 5, 7 }, 0)); // []
        print(coinCombo(new int[] {}, 6)); // []
    }

}
