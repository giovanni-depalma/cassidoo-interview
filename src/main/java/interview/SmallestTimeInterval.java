package interview;

import java.util.BitSet;
import java.util.List;
import java.util.function.Function;

/**
 * This week’s question:
 * Given a list of times in a 24-hour period, return the smallest interval between two times in the list.
 * Hint: you can do this in O(n) time!
 */
    public class SmallestTimeInterval implements Function<List<String>, Integer> {
        private static final int MIN_INTERVAL = 1;

        public int toMinutes(String time){
            String[] arr = time.split(":");
            return Integer.parseInt(arr[0]) * 60 + Integer.parseInt(arr[1]) ;
        }


        public Integer apply(List<String> times){
            // If there are no items or there is only 1, then return -1
            if(times.size() <= 1)
                return -1;
            int nBits = 24*60 - 1;//from 00:00 to 23:59
            BitSet bs = new BitSet(nBits);
            for(String time: times){
                int index = toMinutes(time);
                if(index > nBits)
                    return -1; //invalid element, then return -1
                if(bs.get(index))
                    return 0; //duplicated elements, then return 0 as distance
                bs.set(index);
            }
            int distance = Integer.MAX_VALUE;
            int prev = -1;
            for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i+1)) {
                if(prev != -1)
                    distance = Math.min(distance, i-prev);
                if(distance <= MIN_INTERVAL){
                    break; //avoid unnecessary cycles
                }
                prev = i;
            }
            return distance;
        }

        public static void main(String[] args) {
            SmallestTimeInterval f = new SmallestTimeInterval();
            List<String> times = List.of("01:00", "08:15", "11:30", "13:45", "14:10", "20:05");
            System.out.println(f.apply(times));//25

            times = List.of("00:00", "23:59");
            System.out.println(f.apply(times));//1439

            times = List.of("00:00", "01:00", "23:58", "23:59");
            System.out.println(f.apply(times));//1

            times = List.of("01:00", "08:15", "11:30", "11:30", "14:10", "20:05");
            System.out.println(f.apply(times));//0

        }
}
