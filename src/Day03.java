import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day03 {
    public static void main(String[] args) throws IOException {
        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day03.input.txt"))) {
            String line;
            int sum = 0;
            while ((line = r.readLine()) != null) {
                final int half = line.length() / 2;
                final String left = line.substring(0, half);
                final String right = line.substring(half);
                sum += right.chars()
                            .filter(c -> left.indexOf(c) >= 0)
                            .findFirst()
                            .stream()
                            .map(c -> c <= 'Z' ? 27 + (c - 'A')
                                               :  1 + (c - 'a'))
                            .sum();
            }
            System.out.println(sum);
        }

    }
}
