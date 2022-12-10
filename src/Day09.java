import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day09 {

    public static void main(String[] args) throws IOException {
        Set<Point> visited = new HashSet<>();

        List<Point> rope = IntStream.range(0, 10)
                                    .mapToObj(n -> new Point(0, 0))
                                    .toList();

        Point tail = rope.get(rope.size() - 1);
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
                    moveHead(rope.get(0), direction);

                    boolean moved;
                    int i = 1;      // Rope consists of two or more knots
                    do {
                        moved = moveTail(rope.get(i - 1), rope.get(i));
                    } while (++i < rope.size());

                    if (moved) {
                        visited.add(tail.getLocation());
                    }
                }
            }
        }

        System.out.println(visited.size());
    }

    private static void moveHead(Point head, String direction) {
        switch (direction) {
            case "R" -> head.x++;
            case "U" -> head.y++;
            case "L" -> head.x--;
            case "D" -> head.y--;
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    private static boolean moveTail(Point head, Point tail) {
        Point distance = getDistance(head, tail);
        if (isAdjacent(distance)) {
            return false;
        }

        if (distance.y == 0) {
            tail.x += sign(distance.x);
        } else if (distance.x == 0) {
            tail.y += sign(distance.y);
        } else {
            tail.x += sign(distance.x);
            tail.y += sign(distance.y);
        }
        return true;
    }

    private static int sign(int n) {
        return n > 0 ? 1 : -1;
    }

    private static Point getDistance(Point head, Point tail) {
        return new Point(head.x - tail.x,
                         head.y - tail.y);
    }

    private static boolean isAdjacent(Point distance) {
        return Math.abs(distance.x) <= 1
               && Math.abs(distance.y) <= 1;
    }

}
