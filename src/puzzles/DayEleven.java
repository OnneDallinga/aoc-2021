package puzzles;

import Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DayEleven {

    List<String> input;
    List<Octopus> octopi = new ArrayList<>();
    long flashes = 0;

    public long puzzleOne() {
        input = Utils.parseFile("src/resources/11.txt");
        return runPuzzle(input, 100);
    }

    protected long runPuzzle(List<String> input, int steps) {
        createOctopiMap(input);
        int step = 1;
        while (step <= steps) {
            advanceAStep();
            step++;
        }
        return flashes;
    }

    public long puzzleTwo() {
        input = Utils.parseFile("src/resources/11.txt");
        createOctopiMap(input);
        int step = 1;
        boolean haveAllOctopiFlashed = octopi.stream().allMatch(octopus -> octopus.energyLevel == 0);
        while (!haveAllOctopiFlashed) {
            advanceAStep();
            step++;
            haveAllOctopiFlashed = octopi.stream().allMatch(octopus -> octopus.energyLevel == 0);
        }
        return step - 1;
    }

    private void createOctopiMap(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            String[] horizontalRow = input.get(i).split("");
            for (int j = 0; j < horizontalRow.length; j++) {
                octopi.add(new Octopus(j, i, Integer.parseInt(horizontalRow[j])));
            }
        }
    }

    private void advanceAStep() {
        octopi.forEach(Octopus::increaseEnergyLevel);

        Set<Octopus> flashingOctopi = octopi.stream()
                .filter(Octopus::isFlashing)
                .collect(Collectors.toSet());

        flashes += flashingOctopi.size();


        while (!flashingOctopi.isEmpty()) {
            octopi.stream()
                    .filter(Octopus::isFlashing)
                    .forEach(Octopus::flash);

            octopi.stream()
                    .filter(flashingOctopi::contains)
                    .forEach(octopus -> octopus.findNeighboursAndBoostThem(octopi));

            flashingOctopi = octopi.stream()
                    .filter(Octopus::isFlashing)
                    .collect(Collectors.toSet());

            flashes += flashingOctopi.size();
        }
        octopi.forEach(Octopus::resetEnergyLevel);
    }

    private static class Octopus {
        int x;
        int y;
        int energyLevel;
        boolean hasFlashed = false;

        public Octopus(int x, int y, int energyLevel) {
            this.x = x;
            this.y = y;
            this.energyLevel = energyLevel;
        }

        public void resetEnergyLevel() {
            this.hasFlashed = false;
            if (this.energyLevel > 9) {
                this.energyLevel = 0;
            }
        }

        public void increaseEnergyLevel() {
            energyLevel++;
        }

        public boolean isFlashing() {
            return this.energyLevel > 9 && !hasFlashed;
        }

        public void flash() {
            this.hasFlashed = true;
        }

        public void findNeighboursAndBoostThem(List<Octopus> octopi) {
            List<Octopus> neighbours = octopi.stream().filter(octopus -> isLeft(octopus)
                            || isRight(octopus)
                            || isTop(octopus)
                            || isBottom(octopus)
                            || isTopLeftOctopus(octopus)
                            || isTopRight(octopus)
                            || isBottomLeft(octopus)
                            || isBottomRight(octopus))
                    .toList();
            octopi.stream().filter(neighbours::contains).forEach(Octopus::increaseEnergyLevel);
        }

        private boolean isLeft(Octopus octopus) {
            return octopus.x == (this.x - 1) && octopus.y == this.y;
        }

        private boolean isRight(Octopus octopus) {
            return octopus.x == (this.x + 1) && octopus.y == this.y;
        }

        private boolean isTop(Octopus octopus) {
            return octopus.x == this.x && octopus.y == (this.y - 1);
        }

        private boolean isBottom(Octopus octopus) {
            return octopus.x == this.x && octopus.y == (this.y + 1);
        }

        private boolean isTopLeftOctopus(Octopus octopus) {
            return octopus.x == (this.x - 1) && octopus.y == (this.y - 1);
        }

        private boolean isTopRight(Octopus octopus) {
            return octopus.x == (this.x + 1) && octopus.y == (this.y - 1);
        }

        private boolean isBottomLeft(Octopus octopus) {
            return octopus.x == (this.x - 1) && octopus.y == (this.y + 1);
        }

        private boolean isBottomRight(Octopus octopus) {
            return octopus.x == (this.x + 1) && octopus.y == (this.y + 1);
        }

        @Override
        public String toString() {
            return "Octopus{" +
                    "x=" + x +
                    ", y=" + y +
                    ", energyLevel=" + energyLevel +
                    ", hasFlashed=" + hasFlashed +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Octopus octopus = (Octopus) o;
            return x == octopus.x && y == octopus.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
