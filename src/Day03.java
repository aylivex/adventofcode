import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day03 {
    public static void main(String[] args) throws IOException {
        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day03.input.txt"))) {
            String line;
            int sum = 0;
            final List<String> group = new ArrayList<>(3);
            while ((line = r.readLine()) != null) {
                group.add(line);
                if (group.size() == 3) {
                    group.sort(Comparator.comparingInt(String::length));
                    sum += group.get(2)
                                .chars()
                                .filter(c -> group.get(0).indexOf(c) >= 0
                                             && group.get(1).indexOf(c) >= 0)
                                .findFirst()
                                .stream()
                                .map(c -> c <= 'Z' ? 27 + (c - 'A')
                                                   :  1 + (c - 'a'))
                                .sum();
                    group.clear();
                }
            }
            System.out.println(sum);
        }

    }
}
