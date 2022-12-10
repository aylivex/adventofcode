import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09 {

    private static Point head;
    private static Point tail;

    private static Set<Point> visited;

    public static void main(String[] args) throws IOException {
        visited = new HashSet<>();

        head = new Point(0, 0);
        tail = new Point(0, 0);

        visited.add(tail.getLocation());

        final Pattern pattern = Pattern.compile("([RULD]) (\\d+)");

        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day09.input.txt"))) {
            String line;
            while ((line = r.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("Invalid input: " + line);
                }

                final String direction = matcher.group(1);
                int steps = Integer.parseInt(matcher.group(2));
                while (steps-- > 0) {
                    moveHead(direction);
                    moveTail();
                }
            }
        }

        System.out.println(visited.size());
    }

    private static void moveHead(String direction) {
        switch (direction) {
            case "R" -> head.x++;
            case "U" -> head.y++;
            case "L" -> head.x--;
            case "D" -> head.y--;
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    private static void moveTail() {
        Point distance = getDistance();
        if (isAdjacent(distance)) {
            // No need to move
            return;
        } else if (Math.abs(distance.x) >= 2 && distance.y == 0) {
            tail.x += sign(distance.x);
        } else if (distance.x == 0 && Math.abs(distance.y) >= 2) {
            tail.y += sign(distance.y);
        } else {
            tail.x += sign(distance.x);
            tail.y += sign(distance.y);
        }
        visited.add(tail.getLocation());
    }

    private static int sign(int n) {
        return n > 0 ? 1 : -1;
    }

    private static Point getDistance() {
        return new Point(head.x - tail.x,
                         head.y - tail.y);
    }

    private static boolean isAdjacent(Point distance) {
        return Math.abs(distance.x) <= 1
               && Math.abs(distance.y) <= 1;
    }

}
