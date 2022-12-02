import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day02 {
    public static void main(String[] args) throws IOException {
        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day02.input.txt"))) {
            int score1 = 0;
            int score2 = 0;
            String line;
            while ((line = r.readLine()) != null) {
                score1 += getScore1(line);
                score2 += getScore2(line);
            }
            System.out.println(score1);
            System.out.println(score2);
        }
    }

    private static int getScore1(String line) {
        return getChoiceScore(line.charAt(2))
               + getWinScore(line.charAt(0), line.charAt(2));
    }

    private static int getChoiceScore(char you) {
        return switch (you) {
            case 'X', 'A' -> 1;
            case 'Y', 'B' -> 2;
            case 'Z', 'C' -> 3;
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

    private static int getScore2(String line) {
        return getYourWinScore(line.charAt(2))
               + getChoiceScore(mapYourChoice(line.charAt(0), line.charAt(2)));
    }

    private static char mapYourChoice(char opp, char you) {
        return switch (you) {
            case 'X' -> (opp == 'A' ? 'C' : (opp == 'B' ? 'A' : 'B'));
            case 'Y' -> opp;
            case 'Z' -> (opp == 'A' ? 'B' : (opp == 'B' ? 'C' : 'A'));
            default -> throw new IllegalArgumentException(
                    "Illegal character '" + you + "'");
        };
    }

    private static int getYourWinScore(char you) {
        return switch (you) {
            case 'X' -> 0;
            case 'Y' -> 3;
            case 'Z' -> 6;
            default -> throw new IllegalArgumentException(
                    "Illegal character '" + you + "'");
        };
    }
}
