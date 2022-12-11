import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day11 {

    @FunctionalInterface
    private interface Operation {
        int perform(int old, int arg);
    }

    private static final Operation add = Integer::sum;
    private static final Operation multiply = (a, b) -> a * b;

    private static final class Arg {
        public final Integer value;

        private Arg(Integer value) {
            this.value = value;
        }
    }

    private static final class NextMove {
        private final Predicate<Item> test;

        private final int ifTrue;
        private final int ifFalse;

        private NextMove(Predicate<Item> test, int ifTrue, int ifFalse) {
            this.test = test;
            this.ifTrue = ifTrue;
            this.ifFalse = ifFalse;
        }

        public int nextMove(Item item) {
            return test.test(item) ? ifTrue : ifFalse;
        }
    }

    private static final class Monkey {
        public static final Set<Monkey> monkeys = new LinkedHashSet<>();

        public final int id;

        public final Operation operation;
        public final Arg arg;

        public final NextMove move;

        private final List<Item> items = new ArrayList<>();

        private Monkey(int id, List<Item> items, Operation operation, Arg arg, NextMove move) {
            this.id = id;
            this.items.addAll(items);
            this.operation = operation;
            this.arg = arg;
            this.move = move;
        }

        public int passToMonkey(final Item item) {
            return move.nextMove(item);
        }

        public void passItems() {
            items.forEach(this::passItem);
        }

        private void passItem(Item item) {
            item.updateWorry(this);
        }
    }

    private static class Item {
        public int worry;

        public Item(int worry) {
            this.worry = worry;
        }

        public void updateWorry(final Monkey monkey) {
            worry = monkey.operation.perform(worry, monkey.arg.value == null
                                                    ? worry : monkey.arg.value);
            worry /= 3;
        }
    }

    private static Monkey readMonkey(BufferedReader r) throws IOException {
//Monkey 0:
//  Starting items: 54, 98, 50, 94, 69, 62, 53, 85
//  Operation: new = old * 13
//  Test: divisible by 3
//    If true: throw to monkey 2
//    If false: throw to monkey 1
        String line;
        int id = parseInt((line = r.readLine())
                          .substring("Monkey ".length(), line.length() - 1));

        List<Item> items = Arrays.stream(r.readLine()
                                         .substring("  Starting items: ".length())
                                         .split(", "))
                                 .mapToInt(Integer::parseInt)
                                 .mapToObj(Item::new)
                                 .toList();

        Pattern operationPattern = Pattern.compile(" {2}Operation: new = old ([*+]) (\\d+|old)");
        Matcher matcher = operationPattern.matcher(r.readLine());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid operation");
        }
        Operation operation = matcher.group(1).equals("+") ? add : multiply;
        Arg arg = new Arg(matcher.group(2).equals("old") ? null : parseInt(matcher.group(2)));

        int divisibleBy = parseInt(r.readLine().substring("  Test: divisible by ".length()));
        int monkeyTrue  = parseInt(r.readLine().substring("    If true: throw to monkey ".length()));
        int monkeyFalse = parseInt(r.readLine().substring("    If false: throw to monkey ".length()));

        return new Monkey(id, items, operation, arg,
                          new NextMove((Item item) -> (item.worry % divisibleBy == 0),
                                       monkeyTrue, monkeyFalse));
    }
    public static void main(String[] args) throws IOException {
        final Set<Monkey> monkeys = Monkey.monkeys;

        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day11.input.txt"))) {
            String line;
            do {
                monkeys.add(readMonkey(r));
                // Empty line between monkeys
                line = r.readLine();
            } while (line != null);
        }

        for (int i = 0; i < 20; i++) {
            monkeys.forEach(Monkey::passItems);
        }
    }

}
