package puzzles;

import Utils.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DayTen {

    private int score = 0;
    private BigInteger lineScore = BigInteger.ZERO;
    List<String> input;
    List<Character> currentLine;
    List<BigInteger> lineScores = new ArrayList<>();

    public int puzzleOne() {
        input = Utils.parseFile("src/resources/10.txt");
        scorePuzzleOneAndDiscardCorruptedLines(input);
        return score;
    }

    public BigInteger puzzleTwo() {
        input = Utils.parseFile("src/resources/10.txt");

        input = this.input.stream()
                .filter(line -> !isLineCorrupted(line))
                .toList();

        input.forEach(line -> {
            lineScore = BigInteger.ZERO;
            currentLine = new ArrayList<>(Arrays.stream(line.split("")).map(s -> s.charAt(0)).toList());

            while (currentLine.size() > 0) {
                Character lastCharacter = currentLine.get(currentLine.size() - 1);
                switch (lastCharacter) {
                    case '(' -> lineScore = (lineScore.multiply(BigInteger.valueOf(5))).add(BigInteger.valueOf(1));
                    case '[' -> lineScore = (lineScore.multiply(BigInteger.valueOf(5))).add(BigInteger.valueOf(2));
                    case '{' -> lineScore = (lineScore.multiply(BigInteger.valueOf(5))).add(BigInteger.valueOf(3));
                    case '<' -> lineScore = (lineScore.multiply(BigInteger.valueOf(5))).add(BigInteger.valueOf(4));
                    case ')' -> removeFirstInstanceOf('(', currentLine);
                    case ']' -> removeFirstInstanceOf('[', currentLine);
                    case '}' -> removeFirstInstanceOf('{', currentLine);
                    case '>' -> removeFirstInstanceOf('<', currentLine);
                }
                currentLine.remove(currentLine.size() - 1);
            }
            lineScores.add(lineScore);
        });
        Collections.sort(lineScores);
        return lineScores.get(Math.round((float) lineScores.size() / 2));
    }

    private boolean isLineCorrupted(String line) {
        if (line.length() % 2 != 0) {
            return true;
        }
        currentLine = new ArrayList<>();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);
            boolean isCorrupt = false;
            switch (character) {
                case ')' -> isCorrupt = isCharacterClosed('(');
                case ']' -> isCorrupt = isCharacterClosed('[');
                case '}' -> isCorrupt = isCharacterClosed('{');
                case '>' -> isCorrupt = isCharacterClosed('<');
                default -> currentLine.add(character);
            }
            if (isCorrupt) {
                return true;
            }
        }
        return false;
    }

    private boolean isCharacterClosed(Character character) {
        if (currentLine.get(currentLine.size() - 1) == character) {
            currentLine.remove(currentLine.size() - 1);
            return false;
        } else {
            return true;
        }
    }

    private void removeFirstInstanceOf(Character character, List<Character> characters) {
        characters.remove(characters.lastIndexOf(character));
    }

    private void scorePuzzleOneAndDiscardCorruptedLines(List<String> input) {
        input.forEach(line -> {
            currentLine = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                char character = line.charAt(i);
                boolean isCorrupt = false;
                switch (character) {
                    case ')' -> isCorrupt = isLineCorruptAndScoreIfSo('(', 3);
                    case ']' -> isCorrupt = isLineCorruptAndScoreIfSo('[', 57);
                    case '}' -> isCorrupt = isLineCorruptAndScoreIfSo('{', 1197);
                    case '>' -> isCorrupt = isLineCorruptAndScoreIfSo('<', 25137);
                    default -> currentLine.add(character);
                }
                if (isCorrupt) {
                    break;
                }
            }
        });
    }

    private boolean isLineCorruptAndScoreIfSo(Character character, int points) {
        if (currentLine.get(currentLine.size() - 1) == character) {
            currentLine.remove(currentLine.size() - 1);
            return false;
        } else {
            score += points;
            return true;
        }
    }
}
