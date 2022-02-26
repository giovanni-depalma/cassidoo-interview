package interview;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LongestSubSeqAlternative implements Function<List<Integer>, Integer> {

    record Tuple(int t1, int t2) {
    }

    @Override
    public Integer apply(List<Integer> seq) {
        List<Integer> sorted = seq.stream().sorted().toList();
        return
                IntStream.range(0, sorted.size()).parallel()
                        //stream with index
                        .mapToObj(index -> new Tuple(sorted.get(index), index))
                        //set t2 = val - index (if consecutive it will be the same value)
                        .map(t -> new Tuple(t.t1, t.t1 - t.t2))
                        .collect(Collectors.groupingBy(Tuple::t2))
                        .values()
                        //find best subsequence
                        .parallelStream().max(Comparator.comparing(List::size))
                        .map(List::size)
                        .orElse(0);
    }

}