import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

public class Day08 {

    private static class Grid {
        private final List<List<Integer>> grid;

        public final int rows;
        public final int columns;

        public Grid(List<List<Integer>> grid) {
            this.grid = grid;
            rows = grid.size();
            columns = grid.get(0).size();
        }

        public int getHeight(int x, int y) {
            return grid.get(y).get(x);
        }
    }

    public static void main(String[] args) throws IOException {
        final List<List<Integer>> listGrid = new ArrayList<>();

        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day08.input.txt"))) {
            String line;
            while ((line = r.readLine()) != null) {
                listGrid.add(line.chars()
                             .mapToObj(c -> c - '0')
                             .toList());
            }
        }

        final Grid grid = new Grid(listGrid);
        final List<BitSet> visGrid =
                IntStream.range(0, grid.rows)
                        .mapToObj(n -> new BitSet(grid.columns))
                        .toList();

        for (int y = 0; y < grid.rows; y++) {
            for (int x = 0; x < grid.columns; x++) {
                if (isVisible(grid, x, y)) {
                    visGrid.get(y).set(x);
                }
            }
        }

        System.out.println(visGrid.stream()
                                  .mapToInt(BitSet::cardinality)
                                  .sum());

        int max = 0;
        for (int y = 0; y < grid.rows; y++) {
            for (int x = 0; x < grid.columns; x++) {
                int curr = getScenicScore(grid, x, y);
                if (curr > max) {
                    max = curr;
                }
            }
        }
        System.out.println(max);
    }

    private static boolean isVisible(final Grid grid,
                                     final int x, final int y) {
        final int height = grid.getHeight(x, y);

        boolean fromLeft = true;
        for (int col = x - 1; col >= 0 && fromLeft; col--) {
            fromLeft = grid.getHeight(col, y) < height;
        }
        boolean fromRight = true;
        for (int col = x + 1; col < grid.columns && fromRight; col++) {
            fromRight = grid.getHeight(col, y) < height;
        }

        boolean fromTop = true;
        for (int row = y - 1; row >= 0 && fromTop; row--) {
            fromTop = grid.getHeight(x, row) < height;
        }
        boolean fromBottom = true;
        for (int row = y + 1; row < grid.rows && fromBottom; row++) {
            fromBottom = grid.getHeight(x, row) < height;
        }

        return fromLeft || fromRight || fromTop || fromBottom;
    }

    private static int getScenicScore(final Grid grid,
                                      final int x, final int y) {
        final int height = grid.getHeight(x, y);

        int fromLeft = 0;
        for (int col = x - 1; col >= 0; col--) {
            fromLeft++;
            if (grid.getHeight(col, y) >= height) {
                break;
            }
        }
        int fromRight = 0;
        for (int col = x + 1; col < grid.columns; col++) {
            fromRight++;
            if (grid.getHeight(col, y) >= height) {
                break;
            }
        }

        int fromTop = 0;
        for (int row = y - 1; row >= 0; row--) {
            fromTop++;
            if (grid.getHeight(x, row) >= height) {
                break;
            }
        }
        int fromBottom = 0;
        for (int row = y + 1; row < grid.rows; row++) {
            fromBottom++;
            if (grid.getHeight(x, row) >= height) {
                break;
            }
        }

        return fromLeft * fromRight * fromTop * fromBottom;

    }
}
