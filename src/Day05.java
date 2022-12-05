import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 {
    public static void main(String[] args) throws IOException {
        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day05.input.txt"))) {
            final List<String> crates = new ArrayList<>();
            String line;
            while (!(line = r.readLine()).isEmpty()) {
                crates.add(line);
            }

            final List<Deque<Character>> stacks = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                final int col = 1 + i * 4;
                Deque<Character> stack = new ArrayDeque<>();
                for (int j = crates.size() - 2; j >= 0; j--) {
                    char c = crates.get(j).charAt(col);
                    if (c == ' ') {
                        break;
                    }
                    stack.add(crates.get(j).charAt(col));
                }
                stacks.add(stack);
            }
            stacks.forEach(s -> {
                s.forEach(System.out::print);
                System.out.println();
            });

            Pattern regex = Pattern.compile("move (\\d+) from (\\d) to (\\d)");
            // Moves
            while ((line = r.readLine()) != null) {
                Matcher matcher = regex.matcher(line);
                if (!matcher.matches()) {
                    throw new IllegalStateException("No match found");
                }
                int num = Integer.parseInt(matcher.group(1));
                int from = Integer.parseInt(matcher.group(2)) - 1;
                int to = Integer.parseInt(matcher.group(3)) - 1;

                for (int i = 0; i < num; i++) {
                    stacks.get(to).addLast(stacks.get(from).removeLast());
                }
            }
            System.out.println("\n\nResult:");
            stacks.forEach(s -> {
                s.forEach(System.out::print);
                System.out.println();
            });
            System.out.print("Answer: ");
            stacks.stream()
                  .map(Deque::getLast)
                  .forEach(System.out::print);
            System.out.println();
        }
    }
}
