import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Long.parseLong;

public class Day07 {
    private static class Dir {
        private final String name;

        private final List<Dir> dirs = new ArrayList<>();

        private long fileSize;

        private long dirSize = -1;

        public Dir(String name) {
            this.name = name;
        }

        public void addDir(Dir dir) {
            dirs.add(dir);
        }

        public Dir getDir(String name) {
            return dirs.stream()
                       .filter(d -> d.name.equals(name))
                       .findFirst()
                       .orElseThrow();
        }

        public void addFile(long size) {
            fileSize += size;
        }

        private void updateSize() {
            dirs.forEach(Dir::updateSize);

            dirSize = dirs.stream()
                          .mapToLong(Dir::getTotalSize)
                          .sum();
        }

        public long getTotalSize() {
            if (dirSize == -1) {
                updateSize();
            }
            return fileSize + dirSize;
        }

        public Stream<Dir> stream() {
            return Stream.concat(Stream.of(this),
                                 dirs.stream()
                                     .flatMap(Dir::stream));
        }

        @Override
        public String toString() {
            return "dir " + name
                   + " (" + fileSize + " + " + dirSize + " = "
                          + (fileSize + dirSize) + ")";
        }
    }

    public static void main(String[] args) throws IOException {
        final Dir root = new Dir("/");

        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day07.input.txt"))) {
            final Deque<Dir> stack = new ArrayDeque<>();
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("$ cd ")) {
                    final String name = line.substring("$ cd ".length());
                    if (name.equals("/")) {
                        stack.clear();
                        stack.push(root);
                    } else if (name.equals("..")) {
                        stack.pop();
                    } else {
                        Dir newDir = stack.getFirst()
                                          .getDir(name);
                        stack.push(newDir);
                    }
                } else if (!line.equals("$ ls")) {
                    if (line.startsWith("dir ")) {
                        stack.getFirst()
                             .addDir(new Dir(line.substring("dir ".length())));
                    } else {
                        stack.getFirst()
                             .addFile(parseLong(line.split(" ")[0]));
                    }
                }
            }
        }

        long sum = root.stream()
                       .mapToLong(Dir::getTotalSize)
                       .filter(s -> s <= 100_000L)
                       .sum();
        System.out.println(sum);

        final long total = 70_000_000;
        final long free = total - root.getTotalSize();
        final long required = 30_000_000;
        long size = root.stream()
                        .mapToLong(Dir::getTotalSize)
                        .filter(s -> free + s >= required)
                        .min()
                        .orElseThrow();
        System.out.println(size);
    }
}
