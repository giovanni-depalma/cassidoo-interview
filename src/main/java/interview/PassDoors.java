package interview;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Let’s say you have n doors that start out as closed. 
 * With the first pass across the doors, you toggle every door open. 
 * With the second pass, you toggle every second door. With the third, every third door, and so on. 
 * Write a function that takes in an integer numberOfPasses, and returns how many doors are open after the number of passes. 
 */
public class PassDoors {

    private static String values(int n, int pass){
       return IntStream.range(1, n + 1).mapToObj(val -> pass == 0 || val % pass == 0 ? "1" : "0").collect(Collectors.joining());
    }

    private static int valuesAsInt(int n, int pass){
        return Integer.parseInt(values(n, pass), 2);
     }
    
    private static int passDoors(int n, int numberOfPasses) {
        int initial = valuesAsInt(n, 0);
        int result = IntStream.range(1, numberOfPasses+1).reduce(initial, (current, pass) ->  current ^ valuesAsInt(n, pass));//XOR
        return (int)Integer.toBinaryString(result).chars().filter(c -> (char)c == '0').count();
    }

    public static void main(String[] args) {
        var n = 7;
        var numberOfPasses = 4;
        System.out.println(passDoors(n, numberOfPasses));
    }

}
