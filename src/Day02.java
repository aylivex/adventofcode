import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day02 {
    public static void main(String[] args) throws IOException {
        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day02.input.txt"))) {
            int score = 0;
            String line;
            while ((line = r.readLine()) != null) {
                score += getScore(line);
            }
            System.out.println(score);
        }
    }

    private static int getScore(String line) {
        return getChoiceScore(line.charAt(2))
               + getWinScore(line.charAt(0), line.charAt(2));
    }

    private static int getChoiceScore(char you) {
        return switch (you) {
            case 'X' -> 1;
            case 'Y' -> 2;
            case 'Z' -> 3;
            default  -> throw new IllegalArgumentException(
                    "Illegal character '" + you + "'");
        };
    }

    private static int getWinScore(char opp, char you) {
        return switch (you) {
            case 'X' -> (opp == 'C') ? 6 : (opp == 'A' ? 3 : 0);
            case 'Y' -> (opp == 'A') ? 6 : (opp == 'B' ? 3 : 0);
            case 'Z' -> (opp == 'B') ? 6 : (opp == 'C' ? 3 : 0);
            default  -> throw new IllegalArgumentException(
                    "Illegal character '" + you + "'");
        };
    }
}
