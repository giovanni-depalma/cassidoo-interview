package interview.util;

import java.util.Arrays;
import java.util.BitSet;

public class BitMatrixUtility {

    public static int right(int value, int w, int h){
        /**
         * create bitset like
         * 0001
         * 0001
         * 0001
         * 0001
         */
        BitSet bitSet = new BitSet(w*h);
        for(int i = 0; i < h; i++){
            bitSet.set(i*w);
        }
        int check = BitUtility.toInteger(bitSet);
        while ( (value & check) < 1)
            value = value >> 1;
        return value;
    }

    public static int bottom(int value, int w){
        /**
         * create bitset like
         * 1111
         */
        BitSet bitSet = new BitSet(w);
        for(int i = 0; i < w; i++){
            bitSet.set(i);
        }
        int check = BitUtility.toInteger(bitSet);
        while ( (value & check) < 1)
            value = value >> w;
        return value;
    }

    public static void printMatrix(String title, int[][] matrix){
        System.out.println("--------"+title+"--------");
        for(int[]row: matrix){
            System.out.println(Arrays.toString(row));
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        int test = 19520,  w = 4,  h = 4;
        printMatrix("test", BitUtility.toBitMatrix(test,w,h));
        int rigth = right(test,w,h);
        printMatrix("right", BitUtility.toBitMatrix(rigth,w,h));
        int bottom = bottom(test,w);
        printMatrix("bottom", BitUtility.toBitMatrix(bottom,w,h));
        int bottomRight = bottom(rigth, w);
        printMatrix("bottomRight", BitUtility.toBitMatrix(bottomRight,w,h));
    }
}
