import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.arraycopy;

public class Day06 {
    public static void main(String[] args) throws IOException {
        final int[] buf = new int[4];
        int count = 0;
        int last;
        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day06.input.txt"))) {
            do {
                if (count < buf.length) {
                    buf[count++] = last = r.read();
                } else {
                    arraycopy(buf, 1, buf, 0, buf.length - 1);
                    buf[buf.length - 1] = last = r.read();
                    count++;

                    boolean found = false;
                    for (int i = 0; !found && i < buf.length; i++) {
                        for (int j = i + 1; !found && j < buf.length; j++) {
                            found = buf[i] == buf[j];
                        }
                    }
                    if (!found) {
                        break;
                    }
                }
            } while (last != -1);
            System.out.println(count);
        }
    }
}
