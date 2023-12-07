import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

public class Day01_2 {
    private static final Map<String, Integer> DIGITS =
            Map.ofEntries(Map.entry("one", 1),
                          Map.entry("1", 1),
                          Map.entry("two", 2),
                          Map.entry("2", 2),
                          Map.entry("three", 3),
                          Map.entry("3", 3),
                          Map.entry("four", 4),
                          Map.entry("4", 4),
                          Map.entry("five", 5),
                          Map.entry("5", 5),
                          Map.entry("six", 6),
                          Map.entry("6", 6),
                          Map.entry("seven", 7),
                          Map.entry("7", 7),
                          Map.entry("eight", 8),
                          Map.entry("8", 8),
                          Map.entry("nine", 9),
                          Map.entry("9", 9));

    public static void main(String[] args) throws IOException {
        int calibrationValue = 0;
        try (final BufferedReader r = Files.newBufferedReader(Path.of("2023/input/day01.txt"))) {
            String line;
            while ((line = r.readLine()) != null) {
                int value = getCalibrationValue(line);
                calibrationValue += value;
            }
        }
        System.out.println(calibrationValue);
    }

    private record Pair(String digit, int index) {}

    private static int getCalibrationValue(final String line) {
        int first = getFirstCalibrationDigit(line);
        int last = getLastCalibrationDigit(line);
        return first * 10 + last;
    }

    private static final Predicate<Pair> positiveIndex =
            pair -> pair.index >= 0;

    private static final Comparator<Pair> pairComparator =
            Comparator.comparingInt(pair -> pair.index);

    private static int getFirstCalibrationDigit(final String line) {
        String digit = DIGITS.keySet()
                             .stream()
                             .map(d -> new Pair(d, line.indexOf(d)))
                             .filter(positiveIndex)
                             .min(pairComparator)
                             .orElseThrow()
                       .digit;
        return DIGITS.get(digit);
    }

    private static int getLastCalibrationDigit(final String line) {
        String digit = DIGITS.keySet()
                             .stream()
                             .map(d -> new Pair(d, line.lastIndexOf(d)))
                             .filter(positiveIndex)
                             .max(pairComparator)
                             .orElseThrow()
                       .digit;
        return DIGITS.get(digit);
    }
}
