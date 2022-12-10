import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class Day10 {

    private enum Command {
        NOOP(1),
        ADDX(2);

        public final int cycles;

        Command(int cycles) {
            this.cycles = cycles;
        }
    }

    public static void main(String[] args) throws IOException {
        try (final BufferedReader r = new BufferedReader(
                new FileReader("input/day10.input.txt"))) {
            int cycle = 0;
            int x = 1;

            int sum = 0;
            int[] strengthCycles = {20, 60, 100, 140, 180, 220};
            int cycleIndex = 0;

            int commandCycles = 0;
            Command command = null;
            int addxArg = 0; // dummy initializer

            while (true) {
                cycle++;
                if (--commandCycles <= 0) {
                    if (command == Command.ADDX) {
                        x += addxArg;
                    }
                    // Read next command
                    String line = r.readLine();
                    if (line == null) {
                        break;
                    }

                    if (line.equals("noop")) {
                        command = Command.NOOP;
                    } else {
                        command = Command.ADDX;
                        addxArg = parseInt(line.substring("addx ".length()));
                    }
                    commandCycles = command.cycles;
                }

                if (cycle == strengthCycles[cycleIndex]) {
                    sum += cycle * x;
                    if (++cycleIndex >= strengthCycles.length) {
                        break;
                    }
                }
            }
            System.out.println(sum);
        }
    }
}
