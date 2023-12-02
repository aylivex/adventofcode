import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day01_2 {
    public static void main(String[] args) throws IOException {
        int calibrationValue = 0;
        try (final BufferedReader r = Files.newBufferedReader(Path.of("2023/input/day01.input.txt"))) {
            String line;
            while ((line = r.readLine()) != null) {
                final char[] chars = line.toCharArray();
                char first = '-';
                char last = '-';
                for (char c : chars) {
                    if (c >= '0' && c <= '9') {
                        if (first == '-') {
                            first = c;
                            last = first;
                        } else {
                            last = c;
                        }
                    }
                }
                calibrationValue += (first - '0') * 10 + (last - '0');
            }
        }
        System.out.println(calibrationValue);
    }
}
