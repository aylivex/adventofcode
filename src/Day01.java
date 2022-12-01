import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Day01 {
    public static void main(String[] args) throws IOException {
        int max = 0;
        int curr = 0;
        try (final BufferedReader r = new BufferedReader(
                new InputStreamReader(Day01.class.getResourceAsStream("day01.input.txt")))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.isEmpty()) {
                    if (curr > max) {
                        max = curr;
                    }
                    curr = 0;
                } else {
                    curr += Integer.parseInt(line);
                }
            }
            if (curr > max) {
                max = curr;
            }
            System.out.println(max);
        }
    }
}
