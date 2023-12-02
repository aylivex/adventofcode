import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 {
    public static void main(String[] args) throws IOException {
        int curr = 0;
        try (final BufferedReader r = new BufferedReader(
                                      new FileReader("input/day01.input.txt"))) {
            List<Integer> elves = new ArrayList<>();

            String line;
            while ((line = r.readLine()) != null) {
                if (line.isEmpty()) {
                    elves.add(curr);
                    curr = 0;
                } else {
                    curr += Integer.parseInt(line);
                }
            }
            elves.add(curr);
            Collections.sort(elves);
            List<Integer> max = elves.subList(elves.size() - 3, elves.size());
            System.out.println(max.size());
            System.out.println(max.stream()
                                  .reduce(Integer::sum));
        }
    }
}
