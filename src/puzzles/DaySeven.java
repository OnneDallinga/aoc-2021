package puzzles;

import Utils.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DaySeven {

    public int puzzleOne() {
        List<Integer> crabPositions = Arrays.stream(Utils.parseFile("src/resources/0701.txt").get(0).split(","))
                .map(Integer::parseInt).toList();

        int maxValue = Collections.max(crabPositions);
        int minFuelCost = Integer.MAX_VALUE;

        for (int i = 0; i < maxValue; i++) {
            int finalI = i;

            int fuelcost = crabPositions.stream()
                    .map(crabPosition -> Math.abs(crabPosition - finalI))
                    .mapToInt(Integer::intValue)
                    .sum();

            if (fuelcost < minFuelCost) {
                minFuelCost = fuelcost;
            }
        }
        return minFuelCost;
    }

    public long puzzleTwo() {
        List<Integer> crabPositions = Arrays.stream(Utils.parseFile("src/resources/0701.txt").get(0).split(","))
                .map(Integer::parseInt).toList();

        long maxValue = Collections.max(crabPositions);
        long minFuelCost = Long.MAX_VALUE;

        for (int i = 0; i < maxValue; i++) {
            int finalI = i;

            long fuelcost = crabPositions.stream()
                    .map(crabPosition -> calculateFuelCost(crabPosition, finalI))
                    .mapToLong(Long::longValue)
                    .sum();

            if (fuelcost < minFuelCost) {
                minFuelCost = fuelcost;
            }
        }
        return minFuelCost;
    }

    private long calculateFuelCost(int crabPosition, int currentPosition) {
        long max = Math.abs(crabPosition - currentPosition);
        long total = max;

        while (max > 0) {
            max--;
            total += max;
        }
        return total;
    }


}
