package puzzles;

import Utils.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DayThree {

    public static int puzzleOne() {
        List<String> input = Utils.parseFile("src/resources/0301.txt");

        Map<Integer, List<String>> indexMap = new HashMap<>();

        input.forEach(string -> {
                    String[] split = string.split("");

                    for (int i = 0; i < split.length; i++) {
                        if (indexMap.containsKey(i)) {
                            indexMap.get(i).add(split[i]);
                        } else {
                            List<String> characterList = new ArrayList<>();
                            characterList.add(split[i]);
                            indexMap.put(i, characterList);
                        }
                    }
                }
        );

        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();

        for (int i = 0; i < indexMap.keySet().size(); i++) {
            AtomicInteger amountOfZeroes = new AtomicInteger();
            AtomicInteger amountOfOnes = new AtomicInteger();

            indexMap.get(i).forEach(string -> {
                if (string.equals("0")) {
                    amountOfZeroes.getAndIncrement();
                } else {
                    amountOfOnes.getAndIncrement();
                }
            });

            if (amountOfOnes.get() > amountOfZeroes.get()) {
                gamma.append("1");
                epsilon.append("0");
            } else {
                gamma.append("0");
                epsilon.append("1");
            }
        }

        return Integer.parseInt(gamma.toString(), 2) * Integer.parseInt(epsilon.toString(), 2);
    }

    public static int puzzleTwo() {
        List<String> input = Utils.parseFile("src/resources/0301.txt");

        String oxygenRating = calculateRating(input, '1', '0');
        String scrubberRating = calculateRating(input, '0', '1');

        return Integer.parseInt(oxygenRating, 2) * Integer.parseInt(scrubberRating, 2);
    }

    private static String calculateRating(List<String> input, Character charOne, Character charTwo) {
        String result = null;
        List<String> copyOfInput = new ArrayList<>(input);
        int sampleStringLength = copyOfInput.get(0).length();

        for (int i = 0; i < sampleStringLength; i++) {
            int finalI = i;
            Map<Character, Long> valueAtIndexMap = copyOfInput.stream()
                    .map(string -> string.charAt(finalI))
                    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

            if (copyOfInput.size() == 1) {
                result = copyOfInput.get(0);
                break;
            }

            if (valueAtIndexMap.get('0') == null || valueAtIndexMap.get('1') == null) {
                continue;
            }

            if (valueAtIndexMap.get('1') < valueAtIndexMap.get('0')) {
                copyOfInput = copyOfInput.stream()
                        .filter(string -> string.charAt(finalI) == charOne)
                        .collect(Collectors.toList());
            } else {
                copyOfInput = copyOfInput.stream()
                        .filter(string -> string.charAt(finalI) == charTwo)
                        .collect(Collectors.toList());
            }

            if (copyOfInput.size() == 1) {
                result = copyOfInput.get(0);
                break;
            }
        }
        return result;
    }

}
