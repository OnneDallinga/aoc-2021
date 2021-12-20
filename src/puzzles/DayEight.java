package puzzles;

import Utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayEight {

    List<String> input;

    DisplayLetter zero;
    DisplayLetter one;
    DisplayLetter two;
    DisplayLetter three;
    DisplayLetter four;
    DisplayLetter five;
    DisplayLetter six;
    DisplayLetter seven;
    DisplayLetter eight;
    DisplayLetter nine;

    public long puzzleOne() {
        input = Utils.parseFile("src/resources/0801.txt");

        return input.stream()
                .flatMap(string -> Arrays.stream(string.split("\\|")[1].trim().split(" "))
                        .filter(segmentString -> segmentString.length() == 2
                                || segmentString.length() == 4
                                || segmentString.length() == 3
                                || segmentString.length() == 7))
                .count();
    }

    public int puzzleTwo() {
        input = Utils.parseFile("src/resources/0801.txt");

        return input.stream().map(
                        lineOfInput -> {
                            determineSignals(lineOfInput);
                            return determineOutput(lineOfInput);
                        })
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int determineOutput(String lineOfInput) {
        return Integer.parseInt(Arrays.stream(lineOfInput.split("\\|")[1].trim().split(" "))
                .map(outputValue -> {
                    List<String> outputValueAsList = Arrays.asList(outputValue.trim().split(""));
                    return findCorrespondingSignal(outputValueAsList).toString();
                }).collect(Collectors.joining()));
    }

    private Integer findCorrespondingSignal(List<String> outputValueAsList) {
        if (zero.isLetter(outputValueAsList)) {
            return zero.value;
        } else if (one.isLetter(outputValueAsList)) {
            return one.value;
        } else if (two.isLetter(outputValueAsList)) {
            return two.value;
        } else if (three.isLetter(outputValueAsList)) {
            return three.value;
        } else if (four.isLetter(outputValueAsList)) {
            return four.value;
        } else if (five.isLetter(outputValueAsList)) {
            return five.value;
        } else if (six.isLetter(outputValueAsList)) {
            return six.value;
        } else if (seven.isLetter(outputValueAsList)) {
            return seven.value;
        } else if (eight.isLetter(outputValueAsList)) {
            return eight.value;
        } else if (nine.isLetter(outputValueAsList)) {
            return nine.value;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void determineSignals(String lineOfInput) {
        one = new DisplayLetter(1, parseInputString(2, lineOfInput));
        four = new DisplayLetter(4, parseInputString(4, lineOfInput));
        seven = new DisplayLetter(7, parseInputString(3, lineOfInput));
        eight = new DisplayLetter(8, parseInputString(7, lineOfInput));
        three = new DisplayLetter(3, parseInputStringContainedInDisplayLetter(5, seven, lineOfInput));
        nine = new DisplayLetter(9, parseInputStringContainedInDisplayLetter(6, four, lineOfInput));
        six = new DisplayLetter(6, findSix(lineOfInput));
        zero = new DisplayLetter(0, findZero(lineOfInput));
        five = new DisplayLetter(5, findFive(lineOfInput));
        two = new DisplayLetter(2, findTwo(lineOfInput));
    }

    private List<String> parseInputString(int segmentStringLength, String lineOfInput) {
        return Arrays.asList(Arrays.stream(lineOfInput.split("\\|")[0].trim().split(" "))
                .filter(segmentString -> segmentString.length() == segmentStringLength)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .split(""));
    }

    private List<String> parseInputStringContainedInDisplayLetter(int segmentStringLength, DisplayLetter displayLetter, String lineOfInput) {
        return Arrays.asList(Arrays.stream(lineOfInput.split("\\|")[0].trim().split(" "))
                .filter(segmentString -> segmentString.length() == segmentStringLength)
                .filter(segmentString -> Arrays.asList(segmentString.split("")).containsAll(displayLetter.segments))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .split(""));
    }

    private List<String> findSix(String lineOfInput) {
        return Arrays.asList(Arrays.stream(lineOfInput.split("\\|")[0].trim().split(" "))
                .filter(segmentString -> segmentString.length() == 6)
                .filter(segmentString -> !Arrays.asList(segmentString.split("")).containsAll(one.segments))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .split(""));
    }

    private List<String> findZero(String lineOfInput) {
        return Arrays.asList(Arrays.stream(lineOfInput.split("\\|")[0].trim().split(" "))
                .filter(segmentString -> segmentString.length() == 6)
                .filter(segmentString -> {
                    List<String> segments = Arrays.asList(segmentString.split(""));
                    return !segments.containsAll(nine.segments)
                            && !segments.containsAll(six.segments);
                })
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .split(""));
    }

    private List<String> findTwo(String lineOfInput) {
        return Arrays.asList(Arrays.stream(lineOfInput.split("\\|")[0].trim().split(" "))
                .filter(segmentString -> segmentString.length() == 5)
                .filter(segmentString -> {
                    List<String> segments = Arrays.asList(segmentString.split(""));
                    return !segments.containsAll(five.segments) && !nine.segments.containsAll(segments);
                })
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .split(""));
    }

    private List<String> findFive(String lineOfInput) {
        return Arrays.asList(Arrays.stream(lineOfInput.split("\\|")[0].trim().split(" "))
                .filter(segmentString -> segmentString.length() == 5)
                .filter(segmentString -> {
                    List<String> segments = Arrays.asList(segmentString.split(""));
                    return six.segments.containsAll(segments);
                })
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .split(""));
    }

    private static class DisplayLetter {

        int segmentLength;
        int value;
        List<String> segments;

        DisplayLetter(int value, List<String> segments) {
            this.segmentLength = segments.size();
            this.value = value;
            this.segments = segments;
        }

        public boolean isLetter(List<String> outputValue) {
            return segmentLength == outputValue.size() && outputValue.containsAll(segments);
        }

    }

}
