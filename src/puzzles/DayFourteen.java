package puzzles;

import Utils.Utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayFourteen {

    Map<String, String> polymerTemplate = new HashMap<>();
    Map<String, BigDecimal> pairCounter = new HashMap<>();
    Map<String, BigDecimal> characterCounter = new HashMap<>();

    public BigDecimal puzzleOne() {
        setup();
        runSteps(10);
        return getMostMinusLeastOccurringCharacter();
    }

    public BigDecimal puzzleTwo() {
        setup();
        runSteps(40);
        return getMostMinusLeastOccurringCharacter();
    }

    private void runSteps(int steps) {
        int step = 1;
        while (step <= steps) {
            step++;
            runStep();
        }
    }

    private BigDecimal getMostMinusLeastOccurringCharacter() {
        final BigDecimal[] largestCounter = {BigDecimal.ZERO};
        final BigDecimal[] smallestCounter = {BigDecimal.ZERO};

        characterCounter.forEach((string, counter) -> {
            if (counter.compareTo(largestCounter[0]) > 0) {
                largestCounter[0] = counter;
            }
            if (counter.compareTo(smallestCounter[0]) < 0 || smallestCounter[0].compareTo(BigDecimal.ZERO) == 0) {
                smallestCounter[0] = counter;
            }
        });
        return largestCounter[0].subtract(smallestCounter[0]);
    }

    private void runStep() {
        Map<String, BigDecimal> newPairCounter = new HashMap<>(pairCounter);

        pairCounter.forEach((pair, counter) -> {
            if (counter.compareTo(BigDecimal.ZERO) > 0) {
                String result = polymerTemplate.get(pair);
                characterCounter.put(result, characterCounter.get(result).add(counter));
                String newPairOne = pair.charAt(0) + result;
                String newPairTwo = result + pair.charAt(1);
                newPairCounter.put(newPairOne, newPairCounter.get(newPairOne).add(counter));
                newPairCounter.put(newPairTwo, newPairCounter.get(newPairTwo).add(counter));
                newPairCounter.put(pair, newPairCounter.get(pair).subtract(counter));
            }
        });
        pairCounter = new HashMap<>(newPairCounter);
    }

    private void setup() {
        List<String> input = Utils.parseFile("src/resources/14.txt");
        String polymer = input.get(0);

        for (int i = 2; i < input.size(); i++) {
            String[] split = input.get(i).split(" -> ");
            polymerTemplate.put(split[0], split[1]);
        }
        polymerTemplate.forEach((k, v) -> {
            pairCounter.put(k, BigDecimal.ZERO);
            characterCounter.put(v, BigDecimal.ZERO);
        });

        for (int i = 0; i < polymer.length(); i++) {
            String c = String.valueOf(polymer.charAt(i));
            if (i != polymer.length() - 1) {
                String pair = c + "" + polymer.charAt(i + 1);
                pairCounter.put(pair, pairCounter.get(pair).add(BigDecimal.ONE));
            }
            characterCounter.put(c, characterCounter.get(c).add(BigDecimal.ONE));
        }
    }

}
