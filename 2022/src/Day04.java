import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day04 {
    public static void main(String[] args) throws IOException {
        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day04.input.txt"))) {
            String line;
            int sum = 0;
            while ((line = r.readLine()) != null) {
                List<Integer> intervals = Arrays.stream(line.split("[-,]"))
                                                .map(Integer::valueOf)
                                                .toList();
                if (intervals.get(1) >= intervals.get(2)
                    && intervals.get(0) <= intervals.get(3)) {
                    sum++;
                }
            }
            System.out.println(sum);
        }
    }
}
