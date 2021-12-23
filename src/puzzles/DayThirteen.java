package puzzles;

import Utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class DayThirteen {

    List<Dot> dots = new ArrayList<>();
    List<Instruction> instructions = new ArrayList<>();

    public long puzzleOne() {
        List<String> input = Utils.parseFile("src/resources/13.txt");
        parseFile(input);
        instructions = List.of(instructions.get(0));
        runInstructions();
        return dots.size();
    }

    public int puzzleTwo() {
        List<String> input = Utils.parseFile("src/resources/13.txt");
        parseFile(input);
        runInstructions();

        for (int i = 0; i < 40; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < 40; j++) {
                int finalI = i;
                int finalJ = j;
                if (dots.stream().anyMatch(dot -> dot.x == finalJ && dot.y == finalI)) {
                    builder.append("X");
                } else {
                    builder.append(".");
                }
            }
            System.out.println(builder);
        }
        return 0;
    }

    protected void parseFile(List<String> input) {
        input.forEach(line -> {
            if (line.contains(",")) {
                String[] coordinates = line.split(",");
                dots.add(new Dot(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
            } else if (line.contains("fold")) {
                instructions.add(new Instruction(line.charAt(11) == 'x', Integer.parseInt(line.substring(13))));
            }
        });
    }

    protected void runInstructions() {
        instructions.forEach(this::runInstruction);
    }

    private void runInstruction(Instruction instruction) {
        List<Dot> dotsToRemove = new ArrayList<>();

        if (instruction.isFoldAlongVerticalLine) {
            dots.forEach(dot -> {
                if (dot.x == instruction.coordinate) {
                    dotsToRemove.add(dot);
                } else if (dot.x > instruction.coordinate) {
                    dot.x = swapDotCoordinate(dot.x, instruction.coordinate);
                }
            });
        } else {
            dots.forEach(dot -> {
                if (dot.y == instruction.coordinate) {
                    dotsToRemove.add(dot);
                } else if (dot.y > instruction.coordinate) {
                    dot.y = swapDotCoordinate(dot.y, instruction.coordinate);
                }
            });
        }
        dots.removeAll(dotsToRemove);
        dots = new ArrayList<>(new HashSet<>(dots));
    }

    private int swapDotCoordinate(int dotCoordinate, int instructionCoordinate) {
        return dotCoordinate - ((dotCoordinate - instructionCoordinate) * 2);
    }

    private static class Dot {
        int x;
        int y;

        public Dot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Dot{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dot dot = (Dot) o;
            return x == dot.x && y == dot.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Instruction {
        boolean isFoldAlongVerticalLine;
        int coordinate;

        public Instruction(boolean isFoldingX, int coordinate) {
            this.isFoldAlongVerticalLine = isFoldingX;
            this.coordinate = coordinate;
        }

        @Override
        public String toString() {
            return "Instruction{" +
                    "isFoldingX=" + isFoldAlongVerticalLine +
                    ", coordinate=" + coordinate +
                    '}';
        }
    }

}
