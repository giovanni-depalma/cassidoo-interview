package interview;

import java.util.*;
import java.util.function.Function;

public class LongestSubSeq implements Function<List<Integer>, Integer> {

    @Override
    public Integer apply(List<Integer> seq) {
        return  seq.parallelStream()
                .sorted()
                //subsequences of consecutive integers
                .reduce(new LinkedList<LinkedList<Integer>>(),
                        (acc, item) -> {
                            LinkedList<LinkedList<Integer>> cloneAcc = new LinkedList<>(acc);
                            Optional.ofNullable(cloneAcc.pollLast()).ifPresentOrElse((lastSeq)->{
                                Optional<Integer> lastValue = Optional.ofNullable(lastSeq.peekLast());
                                if (lastValue.isPresent() && isInSequence(lastValue.get(), item)) {
                                    //consecutive, merge seq with item
                                    cloneAcc.add(listWith(lastSeq, item));
                                } else {
                                    //not consecutive, add separated subsequence
                                    cloneAcc.add(lastSeq);
                                    cloneAcc.add(listOf(item));
                                }
                            }, () ->{
                                //first element
                                cloneAcc.add(listOf(item));
                            });
                            return cloneAcc;
                        },
                        (l1, l2) -> {
                            LinkedList<LinkedList<Integer>> cloneL1 = new LinkedList<>(l1);
                            LinkedList<LinkedList<Integer>> cloneL2 = new LinkedList<>(l2);
                            Optional<LinkedList<Integer>> lastSeq1 = Optional.ofNullable(cloneL1.pollLast());
                            Optional<LinkedList<Integer>> firstSeq2 = Optional.ofNullable(cloneL2.pollFirst());
                            Optional<Integer> lastValueSeq1 = lastSeq1.map(LinkedList::peekLast);
                            Optional<Integer> firstValueSeq2 = firstSeq2.map(LinkedList::peekFirst);
                            if (lastValueSeq1.isPresent() && firstValueSeq2.isPresent() &&
                                    isInSequence(lastValueSeq1.get(), firstValueSeq2.get())) {
                                cloneL1.add(merge(lastSeq1.get(), firstSeq2.get()));
                            } else {
                                //not consecutive, add separated subsequence
                                lastSeq1.ifPresent(current -> cloneL1.add(new LinkedList<>(current)));
                                firstSeq2.ifPresent(current -> cloneL1.add(new LinkedList<>(current)));
                            }
                            //add last element of l2
                            cloneL1.addAll(cloneL2);
                            return cloneL1;
                        }
                )
                //find best subsequence
                .stream().max(Comparator.comparing(List::size))
                //organize in map for quick searches
                .map(HashSet::new)
                //preserve original order
                .map(winnerSequence -> {
                    List<Integer> result = new ArrayList<>(seq);
                    result.removeIf(i -> !winnerSequence.contains(i));
                    return result;
                })
                .map(result -> {
                    System.out.println(result);
                    return result;
                })
                .map(List::size)
                .orElse(0);
    }

    private LinkedList<Integer> listWith(LinkedList<Integer> list, Integer toAdd){
        LinkedList<Integer> newSeq = new LinkedList<>(list);
        newSeq.add(toAdd);
        return newSeq;
    }

    private LinkedList<Integer> listOf(Integer toAdd){
        LinkedList<Integer> newSeq = new LinkedList<>();
        newSeq.add(toAdd);
        return newSeq;
    }

    private LinkedList<Integer> merge(LinkedList<Integer> list1, LinkedList<Integer> list2){
        LinkedList<Integer> newSeq = new LinkedList<>();
        newSeq.addAll(list1);
        newSeq.addAll(list2);
        return newSeq;
    }


    private boolean isInSequence(int num1, int num2){
        return num1 + 1 == num2;
    }
}
