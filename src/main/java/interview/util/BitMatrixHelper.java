package interview.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.BitSet;

@Getter
public class BitMatrixHelper {
    private int rightShift = 0;
    private int bottomShift = 0;

    public int left(int value, int w, int h){
        return -1;
    }

    public int mostRight(int value, int w, int h){
        /**
         * create bitset like
         * 0001         * 0001
         * 0001
         * 0001
         */    BitSet bitSet = new BitSet(w*h);
        for(int i = 0; i < h; i++){
            bitSet.set(i*w);
        }
        rightShift = 0;
        int check = BitUtility.toInteger(bitSet);
        while ( (value & check) < 1) {
            value = value >> 1;
            rightShift++;
        }
        return value;
    }

    public int mostBottom(int value, int w){
        /**
         * create bitset like
         * 1111
         */
        BitSet bitSet = new BitSet(w);
        for(int i = 0; i < w; i++){
            bitSet.set(i);
        }
        bottomShift = 0;
        int check = BitUtility.toInteger(bitSet);
        while ( (value & check) < 1) {
            value = value >> w;
            bottomShift++;
        }
        return value;
    }


    public void printMatrix(String title, int[][] matrix){
        System.out.println("--------"+title+"--------");
        for(int[]row: matrix){
            System.out.println(Arrays.toString(row));
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        int test = 19520,  w = 4,  h = 4;
        BitMatrixHelper helper = new BitMatrixHelper();
        helper.printMatrix("test", BitUtility.toBitMatrix(test,w,h));
        int rigth = helper.mostRight(test,w,h);
        helper.printMatrix("right", BitUtility.toBitMatrix(rigth,w,h));
        int bottom = helper.mostBottom(test,w);
        helper.printMatrix("bottom", BitUtility.toBitMatrix(bottom,w,h));
        int bottomRight = helper.mostBottom(rigth, w);
        helper.printMatrix("bottomRight", BitUtility.toBitMatrix(bottomRight,w,h));
    }
}
