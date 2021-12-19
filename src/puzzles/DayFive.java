package puzzles;

import Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DayFive {

    public int puzzleOne() {
        List<String> input = Utils.parseFile("src/resources/0501.txt");

        List<Coordinate> coordinates = input.stream()
                .map(VentLine::new)
                .filter(ventLine -> ventLine.isVertical() || ventLine.isHorizontal())
                .flatMap(ventLine -> ventLine.getAllCoordinates().stream())
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet()
                .stream()
                .filter(coordinate -> coordinate.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .toList();

        return coordinates.size();
    }

    public int puzzleTwo() {
        List<String> input = Utils.parseFile("src/resources/0501.txt");

        List<Coordinate> coordinates = input.stream()
                .map(VentLine::new)
                .flatMap(ventLine -> ventLine.getAllCoordinates().stream())
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet()
                .stream()
                .filter(coordinate -> coordinate.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .toList();

        return coordinates.size();
    }

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            Coordinate other = (Coordinate) obj;

            return other.x == x && other.y == y;
        }

        @Override
        public int hashCode() {
            return 37 * (x * y) + 15;
        }
    }

    private static class VentLine {
        int x1;
        int x2;
        int y1;
        int y2;

        List<Coordinate> allCoordinates;

        public VentLine(String coordinates) {
            String[] split = coordinates.split("->");
            String leftSplit = split[0].trim();
            x1 = Integer.parseInt(leftSplit.split(",")[0]);
            y1 = Integer.parseInt(leftSplit.split(",")[1]);
            String rightSplit = split[1].trim();
            x2 = Integer.parseInt(rightSplit.trim().split(",")[0]);
            y2 = Integer.parseInt(rightSplit.trim().split(",")[1]);

            allCoordinates = new ArrayList<>();

            if (isHorizontal()) {
                int start = y1;
                int end = y2;

                if (start < end) {
                    while (start <= end) {
                        allCoordinates.add(new Coordinate(x1, start));
                        start++;
                    }
                } else if (start > end) {
                    while (start >= end) {
                        allCoordinates.add(new Coordinate(x1, start));
                        start--;
                    }
                }
            } else if (isVertical()) {
                int start = x1;
                int end = x2;

                if (start < end) {
                    while (start <= end) {
                        allCoordinates.add(new Coordinate(start, y1));
                        start++;
                    }
                } else if (start > end) {
                    while (start >= end) {
                        allCoordinates.add(new Coordinate(start, y1));
                        start--;
                    }
                }
            } else {
                int startX = x1;
                int startY = y1;
                int endX = x2;
                int endY = y2;

                if (startX < endX && startY < endY) {
                    while (startX <= endX) {
                        allCoordinates.add(new Coordinate(startX, startY));
                        startX++;
                        startY++;
                    }
                } else if (startX > endX && startY < endY) {
                    while (startX >= endX) {
                        allCoordinates.add(new Coordinate(startX, startY));
                        startX--;
                        startY++;
                    }
                } else if (startX < endX && startY > endY) {
                    while (startX <= endX) {
                        allCoordinates.add(new Coordinate(startX, startY));
                        startX++;
                        startY--;
                    }
                } else if (startX > endX && startY > endY) {
                    while (startX >= endX) {
                        allCoordinates.add(new Coordinate(startX, startY));
                        startX--;
                        startY--;
                    }
                }
            }
        }

        public boolean isHorizontal() {
            return x1 == x2;
        }

        public boolean isVertical() {
            return y1 == y2;
        }

        public List<Coordinate> getAllCoordinates() {
            return allCoordinates;
        }

    }
}
