import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day01_2 {
    enum SPELT_DIGITS {
        ONE("one", 1),
        TWO("two", 2),
        THREE("three", 3),
        FOUR("four", 4),
        FIVE("five", 5),
        SIX("six", 6),
        SEVEN("seven", 7),
        EIGHT("eight", 8),
        NINE("nine", 9);

        public final String spelt;
        public final int value;

        SPELT_DIGITS(String spelt, int value) {
            this.spelt = spelt;
            this.value = value;
        }
    }

    private static final String[] DIGITS =
            {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static void main(String[] args) throws IOException {
        int calibrationValue = 0;
        try (final BufferedReader r = Files.newBufferedReader(Path.of("2023/input/day01.input.txt"))) {
            String line;
            while ((line = r.readLine()) != null) {
                int value = getCalibrationValue(line);
                calibrationValue += value;
            }
        }
        System.out.println(calibrationValue);
    }

    private record CalibrationDigit(int first, int index) {}

    private static int getCalibrationValue(final String line) {
        CalibrationDigit first = getFirstCalibrationDigit(line);
        CalibrationDigit last = getLastCalibrationDigit(line);
        return first.first * 10 + last.first;
    }

    private static CalibrationDigit getFirstCalibrationDigit(final String line) {
        int[] spelt = Arrays.stream(SPELT_DIGITS.values())
                            .mapToInt(v -> line.indexOf(v.spelt))
                            .toArray();
        int sMinIndex = getMinValueIndex(spelt);
        boolean sValid = sMinIndex >= 0;

        int[] digits = Arrays.stream(DIGITS)
                             .mapToInt(c -> line.lastIndexOf(c))
                             .toArray();
        int dMinIndex = getMinValueIndex(digits);
        boolean dValid = dMinIndex >= 0;

        int first;
        int nextIndex;
        if (sValid && dValid && spelt[sMinIndex] < digits[dMinIndex]) {
            first = SPELT_DIGITS.values()[sMinIndex].value;
            nextIndex = spelt[sMinIndex]
                        + SPELT_DIGITS.values()[sMinIndex].spelt.length();
        } else if (sValid && dValid) {
            first = DIGITS[dMinIndex].charAt(0) - '0';
            nextIndex = digits[dMinIndex] + 1;
        } else if (sValid && !dValid) {
            first = SPELT_DIGITS.values()[sMinIndex].value;
            nextIndex = spelt[sMinIndex]
                        + SPELT_DIGITS.values()[sMinIndex].spelt.length();
        } else if (dValid) {
            first = DIGITS[dMinIndex].charAt(0) - '0';
            nextIndex = digits[dMinIndex] + 1;
        } else {
            first = -1;
            nextIndex = -1;
        }

        return new CalibrationDigit(first, nextIndex);
    }

    private static CalibrationDigit getLastCalibrationDigit(final String line) {
        int[] spelt = Arrays.stream(SPELT_DIGITS.values())
                            .mapToInt(v -> line.lastIndexOf(v.spelt))
                            .toArray();
        int sMaxIndex = getMaxValueIndex(spelt);
        boolean sValid = sMaxIndex >= 0;

        int[] digits = Arrays.stream(DIGITS)
                             .mapToInt(c -> line.lastIndexOf(c))
                             .toArray();
        int dMaxIndex = getMaxValueIndex(digits);
        boolean dValid = dMaxIndex >= 0;

        int first;
        int nextIndex;
        if (sValid && dValid && spelt[sMaxIndex] > digits[dMaxIndex]) {
            first = SPELT_DIGITS.values()[sMaxIndex].value;
            nextIndex = spelt[sMaxIndex]
                    + SPELT_DIGITS.values()[sMaxIndex].spelt.length() + 1;
        } else if (sValid && dValid) {
            first = DIGITS[dMaxIndex].charAt(0) - '0';
            nextIndex = digits[dMaxIndex] + 1;
        } else if (sValid && !dValid) {
            first = SPELT_DIGITS.values()[sMaxIndex].value;
            nextIndex = spelt[sMaxIndex]
                    + SPELT_DIGITS.values()[sMaxIndex].spelt.length() + 1;
        } else if (dValid) {
            first = DIGITS[dMaxIndex].charAt(0) - '0';
            nextIndex = digits[dMaxIndex] + 1;
        } else {
            first = -1;
            nextIndex = -1;
        }

        return new CalibrationDigit(first, nextIndex);
    }

    private static int getMinValueIndex(final int[] array) {
        int result = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] >= 0) {
                if (result == -1) {
                    result = i;
                } else if (array[i] < array[result]) {
                    result = i;
                }
            }
        }
        return result;
    }

    private static int getMaxValueIndex(final int[] array) {
        int result = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] >= 0) {
                if (result == -1) {
                    result = i;
                } else if (array[i] > array[result]) {
                    result = i;
                }
            }
        }
        return result;
    }
}
