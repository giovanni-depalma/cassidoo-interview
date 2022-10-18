package interview;

import java.util.*;

/**
 * This week’s question:
 * Given a 2D array of 0s and 1s, return the size of the largest rectangle of 1s in the array.
 */
public class LargestRect {

    record Rect(int w, int h, int area){
        public Rect max(Rect other) {
            return other.area() >= area ? other : this;
        }
        public String toString(){
            return w+"x"+h;
        }
    }

    public Rect apply(int[][] matrix){
        if(matrix == null)
            throw new IllegalArgumentException("matrix is null");
        return Arrays.stream(toHistograms(matrix)).parallel()
                .map(this::largestHistogram)
                .max(Comparator.comparing(Rect::area))
                .orElse(new Rect(-1,-1,-1));
    }

    private int[][] toHistograms(int[][] matrix){
        int[][] histograms = matrix.clone();
        for (int i=0; i < histograms.length; i++){
            for(int j = 0; j < histograms[i].length; j++){
                histograms[i][j] += (i == 0 || histograms[i][j] == 0 ? 0 : histograms[i-1][j]);
            }
        }
        return histograms;
    }

    private Rect largestHistogram(int[] hist){
        Deque<Integer> stack = new ArrayDeque<>();
        int i = 0;
        Rect maxRect = new Rect(-1,-1,-1);
        while(i < hist.length){
            if(stack.isEmpty() || hist[stack.peekLast()] <= hist[i] )
                stack.add(i++);
            else {
                int removedBar = hist[stack.pollLast()];
                maxRect = maxRect.max(calcRect(removedBar, stack, i));
            }
        }
        while (!stack.isEmpty()){
            int removedBar = hist[stack.pollLast()];
            maxRect = maxRect.max(calcRect(removedBar, stack, i));
        }
        return maxRect;
    }

    private Rect calcRect(int bar, Deque<Integer> stack, int i){
        int w = (stack.isEmpty() ? i : i - stack.peekLast() -1 );
        return new Rect(w, bar, w * bar);
    }


    public static void main(String[] args) {
        int[][] islands = new int[][]{
                {0,0,0,1,0},
                {1,1,0,0,1},
                {1,1,0,0,0},
                {0,0,1,0,0}
        };
        System.out.println(new LargestRect().apply(islands));
    }
}
