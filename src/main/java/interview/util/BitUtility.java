package interview.util;

import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.IntStream;

public class BitUtility {

    public static int toInteger(int[] bits){
        int res = 0;
        for(int bit: bits){
            res = ( res << 1 ) | bit;
        }
        return res;
    }

    public static int toInteger(BitSet bitSet){
        int res = 0;
        int size = bitSet.length();
        for(int i = 0; i < size; i++){
            res = ( res << 1 ) | (bitSet.get(size -i -1) ? 1 : 0);
        }
        return res;
    }

    public static int[] toBitArray(int value, int size){
        int[] bits = new int[size];
        for(int i=0; i < size; i++){
            bits[size-i-1] = (value & (1 << i)) == 0 ? 0 : 1;
        }
        return bits;
    }

    public static int[][] toBitMatrix(int value, int w,int h){
        int size = w*h;
        int[] bits = new int[size];
        for(int i=0; i < size; i++){
            bits[size-i-1] = (value & (1 << i)) == 0 ? 0 : 1;
        }
        return splitArray(bits,h);
    }

    public static int[][] splitArray(int[] inputArray, int chunkSize) {
        return IntStream.iterate(0,  i -> i + chunkSize)
                .limit((int) Math.ceil((double) inputArray.length / chunkSize))
                .mapToObj(j -> Arrays.copyOfRange(inputArray, j, Math.min(inputArray.length, j + chunkSize)))
                .toArray(int[][]::new);
    }

    public static BitSet toBitSet(int value, int size){
        BitSet bitSet = new BitSet(size);
        for(int i=0; i < size; i++){
            bitSet.set(i, (value & (1 << i)) != 0);
        }
        return bitSet;
    }




    public static void main(String[] args) {
        int[][] allBits = new int[][]{
                {1,0,0,1,1,0},
                {1,0,0,0,1,0,0,0}
        };

        for(int[] bits: allBits){
            System.out.println("--------");
            int intValue = toInteger(bits);
            System.out.println(intValue + ": "+Arrays.toString(bits));
            BitSet bitsConverted = toBitSet(intValue, bits.length);
            System.out.println("BitSet: "+bitsConverted);
            System.out.println("BitArray: "+Arrays.toString(toBitArray(intValue, bits.length)));
            intValue = toInteger(bitsConverted);
            System.out.println(intValue + ": "+bitsConverted);
            System.out.println("--------");
        }

        int t[] = new int[]{114,  305, 39, 562};
        int j[] = new int[]{275, 71, 802, 113};
        int l[] = new int[]{547, 116, 785, 23};
        int z[] = new int[]{99, 306};
        int s[] = new int[]{54, 561};
        int i[] = new int[]{15, 4369};
        int o[] = new int[]{51};


        for(int tetroMino: z){
            int[] bins = toBitArray(tetroMino, 16);
            int[][] matrix = splitArray(bins, 4);
            System.out.println("--------");
            System.out.println(tetroMino);
            for(int[]row: matrix){
                System.out.println(Arrays.toString(row));
            }
            System.out.println("--------");
        }
    }
}
