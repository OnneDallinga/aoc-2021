package puzzles;

import Utils.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DayNine {

    List<String> input;
    Map<Integer, Map<Integer, Coordinate>> coordinateMap = new HashMap<>();
    Set<Coordinate> alreadyUsedCoordinates = new HashSet<>();
    List<Integer> basins = new ArrayList<>();

    public int puzzleOne() {
        input = Utils.parseFile("src/resources/0901.txt");

        Map<Integer, Map<Integer, Integer>> coordinateMap = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            coordinateMap.put(i, new HashMap<>());
            String[] row = input.get(i).split("");
            for (int j = 0; j < row.length; j++) {
                coordinateMap.get(i).put(j, Integer.parseInt(row[j]));
            }
        }

        AtomicInteger sum = new AtomicInteger();

        coordinateMap.forEach((yCoordinate, row) -> row.forEach((xCoordinate, value) -> {
            if (xCoordinate != 0 && row.get(xCoordinate - 1) <= value) {
                return;
            } else if (xCoordinate != (row.size() - 1) && row.get(xCoordinate + 1) <= value) {
                return;
            } else if (yCoordinate != 0 && coordinateMap.get(yCoordinate - 1).get(xCoordinate) <= value) {
                return;
            } else if (yCoordinate != (coordinateMap.size() - 1) && coordinateMap.get(yCoordinate + 1).get(xCoordinate) <= value) {
                return;
            }
            sum.addAndGet(value + 1);
        }));

        return sum.get();
    }

    public int puzzleTwo() {
        input = Utils.parseFile("src/resources/0901.txt");
        createCoordinateMap(input);

        coordinateMap.forEach((yCoordinate, row) -> row.forEach((xCoordinate, coordinate) -> {
            if (isValidCoordinate(coordinate)) {
                int basinSize = 1;
                alreadyUsedCoordinates.add(coordinate);

                Set<Coordinate> followUpCoordinates = getFollowUpCoordinates(Set.of(coordinate));

                while (!followUpCoordinates.isEmpty()) {
                    alreadyUsedCoordinates.addAll(followUpCoordinates);
                    basinSize += followUpCoordinates.size();
                    followUpCoordinates = getFollowUpCoordinates(followUpCoordinates);
                }

                basins.add(basinSize);
            }
        }));

        return basins.stream()
                .sorted()
                .toList()
                .subList(basins.size() - 3, basins.size())
                .stream()
                .reduce(1, Math::multiplyExact);
    }

    private void createCoordinateMap(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            coordinateMap.put(i, new HashMap<>());
            String[] row = input.get(i).split("");
            for (int j = 0; j < row.length; j++) {
                Coordinate coordinate = new Coordinate(j, i, Integer.parseInt(row[j]));
                coordinateMap.get(i).put(j, coordinate);
            }
        }
    }

    private Set<Coordinate> getFollowUpCoordinates(Set<Coordinate> coordinates) {
        Set<Coordinate> potentialCoordinates = new HashSet<>();

        coordinates.forEach(coordinate -> {
            if (coordinate.x != 0) {
                Coordinate potentialCoordinate = coordinateMap.get(coordinate.y).get(coordinate.x - 1);
                if (isValidCoordinate(potentialCoordinate)) {
                    potentialCoordinates.add(potentialCoordinate);
                }
            }

            if (coordinate.x != (coordinateMap.get(coordinate.y).size() - 1)) {
                Coordinate potentialCoordinate = coordinateMap.get(coordinate.y).get(coordinate.x + 1);
                if (isValidCoordinate(potentialCoordinate)) {
                    potentialCoordinates.add(potentialCoordinate);
                }
            }

            if (coordinate.y != 0) {
                Coordinate potentialCoordinate = coordinateMap.get(coordinate.y - 1).get(coordinate.x);
                if (isValidCoordinate(potentialCoordinate)) {
                    potentialCoordinates.add(potentialCoordinate);
                }
            }

            if (coordinate.y != (coordinateMap.size() - 1)) {
                Coordinate potentialCoordinate = coordinateMap.get(coordinate.y + 1).get(coordinate.x);
                if (isValidCoordinate(potentialCoordinate)) {
                    potentialCoordinates.add(potentialCoordinate);
                }
            }
        });

        return potentialCoordinates;
    }

    private boolean isValidCoordinate(Coordinate potentialCoordinate) {
        return potentialCoordinate != null && potentialCoordinate.value != 9 && !alreadyUsedCoordinates.contains(potentialCoordinate);
    }

    private static class Coordinate {
        int x;
        int y;
        int value;

        public Coordinate(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y && value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, value);
        }
    }

}
