package interview;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * You went on a vacation with friends.
 * Each of you paid for certain meals on the trip for the group.
 * Write a function that determines who owes money to whom so that everyone pays equally.
 */
public class WhoOwes {
    record Receipt(String name, double paid){}
    record Refund(String from, String to, double money){}

    public static void whoOwes(List<Receipt> receipts){
        var totalForPerson = totalForPerson(receipts);
        var total = totalForPerson.stream().mapToDouble(Receipt::paid).sum();
        var share = total / totalForPerson.size();
        var refunds = calculateRefunds(totalForPerson, share);
        System.out.println(format(refunds));
    }

    public static List<Refund> calculateRefunds(List<Receipt> totalForPerson, double share){
        var partitioned = partitionIntoCreditorsAndDebtors(totalForPerson,share, LinkedList::new);
        var creditors = partitioned.get(true);
        var debtors = partitioned.get(false);
        List<Refund> debts = new ArrayList<>();
        while(!creditors.isEmpty() && !debtors.isEmpty()){
            Receipt creditor = creditors.pollFirst();
            Receipt debtor = debtors.pollFirst();
            double credit = creditor.paid-share;
            double refund = Math.min(share-debtor.paid, credit);
            debts.add(new Refund(debtor.name, creditor.name, refund));
            //check new creditor position
            double creditorUpdatedPaid = creditor.paid - refund;
            if(creditorUpdatedPaid > share)
                creditors.addFirst(new Receipt(creditor.name, creditorUpdatedPaid));
            //check new debtor position
            double debtorUpdatedPaid = debtor.paid + refund;
            if(debtorUpdatedPaid < share)
                debtors.addFirst(new Receipt(debtor.name, debtorUpdatedPaid));
        }
        return debts;
    }

    public static List<Receipt> totalForPerson(List<Receipt> receipts){
        return receipts.stream()
                .collect(Collectors.groupingBy(Receipt::name, Collectors.summingDouble(Receipt::paid)))
                .entrySet().stream().map(e -> new Receipt(e.getKey(),e.getValue()))
                .toList();
    }

    public static <T extends Collection<Receipt>> Map<Boolean, T> partitionIntoCreditorsAndDebtors(List<Receipt> totalForPerson, double share, Supplier<T> supplier){
        return totalForPerson.stream()
                //filter person with right share
                .filter(r -> r.paid != share)
                //and partition in creditors and debtors
                .collect(Collectors.partitioningBy(p -> p.paid-share >=0, Collectors.toCollection(supplier)));
    }

    public static String format(List<Refund> debts){
        if(debts.isEmpty())
            return "Nobody owes anything";
        StringBuilder sb = new StringBuilder();
        var nFormat = NumberFormat.getCurrencyInstance(Locale.US);
        for(Refund slice: debts){
            if(!sb.isEmpty())
                sb.append(", ");
            sb.append(MessageFormat.format("{0} owes {1} {2}",slice.from,slice.to, nFormat.format(slice.money)));
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        List<Receipt> receipts;

        receipts = List.of(
                new Receipt("Ximena",45),
                new Receipt("Clara",130),
                new Receipt("Ximena",100),
                new Receipt("Cassidy",140),
                new Receipt("Cassidy",76),
                new Receipt("Clara",29),
                new Receipt("Ximena",20)
        );
        whoOwes(receipts);

        receipts = List.of(
                new Receipt("Ximena",45),
                new Receipt("Clara",130),
                new Receipt("Cassidy",140)
        );
        whoOwes(receipts);

        receipts = List.of(
                new Receipt("Ximena",45),
                new Receipt("Clara",45),
                new Receipt("Cassidy",45)
        );
        whoOwes(receipts);

        receipts = List.of(
                new Receipt("Ximena",10),
                new Receipt("Clara",50),
                new Receipt("Cassidy",90)
        );
        whoOwes(receipts);

        receipts = List.of(
                new Receipt("Ximena",10),
                new Receipt("Clara",50),
                new Receipt("Cassidy",90.09)
        );
        whoOwes(receipts);

        receipts = List.of(
                new Receipt("Ximena",45.01),
                new Receipt("Clara",45.02),
                new Receipt("Cassidy",45.03)
        );
        whoOwes(receipts);

        receipts = List.of(
                new Receipt("Ximena",40),
                new Receipt("Clara",100),
                new Receipt("Cassidy",100)
        );
        whoOwes(receipts);

        receipts = List.of();
        whoOwes(receipts);
    }
}
