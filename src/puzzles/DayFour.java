package puzzles;

import Utils.Utils;

import java.util.*;

public class DayFour {

    List<String> input;
    List<String> numbersDrawn;
    List<BingoBoard> bingoBoards;

    public int puzzleOne() {
        setupBoard();

        for (String numberDrawn : numbersDrawn) {
            for (BingoBoard bingoBoard : bingoBoards) {
                bingoBoard.markNumber(numberDrawn);
                if (bingoBoard.isFinished()) {
                    return bingoBoard.calculateScore(numberDrawn);
                }
            }
        }

        return 0;
    }

    public int puzzleTwo() {
        setupBoard();

        List<BingoBoard> finishedBoards = new ArrayList<>();
        for (String numberDrawn : numbersDrawn) {
            for (BingoBoard bingoBoard : bingoBoards) {
                bingoBoard.markNumber(numberDrawn);
                if (bingoBoard.isFinished() && !finishedBoards.contains(bingoBoard)) {
                    finishedBoards.add(bingoBoard);
                    if (finishedBoards.size() == bingoBoards.size()) {
                        return bingoBoard.calculateScore(numberDrawn);
                    }
                }
            }
        }

        return 0;
    }

    private void setupBoard() {
        input = Utils.parseFile("src/resources/0401.txt").stream()
                .filter(s -> !s.isEmpty())
                .toList();

        numbersDrawn = parseString(input, 0, ",");

        bingoBoards = new ArrayList<>();
        BingoBoard newBingoBoard = new BingoBoard();

        for (int i = 1; i < input.size(); ) {
            newBingoBoard.rowOne = new ArrayList<>(parseString(input, i++, " "));
            newBingoBoard.rowTwo = new ArrayList<>(parseString(input, i++, " "));
            newBingoBoard.rowThree = new ArrayList<>(parseString(input, i++, " "));
            newBingoBoard.rowFour = new ArrayList<>(parseString(input, i++, " "));
            newBingoBoard.rowFive = new ArrayList<>(parseString(input, i++, " "));
            newBingoBoard.makeColumns();
            bingoBoards.add(newBingoBoard);
            newBingoBoard = new BingoBoard();
        }
    }

    private List<String> parseString(List<String> input, int index, String regex) {
        return Arrays.stream(input.get(index).split(regex))
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .toList();
    }

    private static class BingoBoard {
        List<String> rowOne = new ArrayList<>();
        List<String> rowTwo = new ArrayList<>();
        List<String> rowThree = new ArrayList<>();
        List<String> rowFour = new ArrayList<>();
        List<String> rowFive = new ArrayList<>();
        List<String> columnOne = new ArrayList<>();
        List<String> columnTwo = new ArrayList<>();
        List<String> columnThree = new ArrayList<>();
        List<String> columnFour = new ArrayList<>();
        List<String> columnFive = new ArrayList<>();

        Set<String> allNumbers = new HashSet<>();

        public void makeColumns() {
            columnOne.add(rowOne.get(0));
            columnOne.add(rowTwo.get(0));
            columnOne.add(rowThree.get(0));
            columnOne.add(rowFour.get(0));
            columnOne.add(rowFive.get(0));

            columnTwo.add(rowOne.get(1));
            columnTwo.add(rowTwo.get(1));
            columnTwo.add(rowThree.get(1));
            columnTwo.add(rowFour.get(1));
            columnTwo.add(rowFive.get(1));

            columnThree.add(rowOne.get(2));
            columnThree.add(rowTwo.get(2));
            columnThree.add(rowThree.get(2));
            columnThree.add(rowFour.get(2));
            columnThree.add(rowFive.get(2));

            columnFour.add(rowOne.get(3));
            columnFour.add(rowTwo.get(3));
            columnFour.add(rowThree.get(3));
            columnFour.add(rowFour.get(3));
            columnFour.add(rowFive.get(3));

            columnFive.add(rowOne.get(4));
            columnFive.add(rowTwo.get(4));
            columnFive.add(rowThree.get(4));
            columnFive.add(rowFour.get(4));
            columnFive.add(rowFive.get(4));

            allNumbers.addAll(rowOne);
            allNumbers.addAll(rowTwo);
            allNumbers.addAll(rowThree);
            allNumbers.addAll(rowFour);
            allNumbers.addAll(rowFive);
        }

        public void markNumber(String number) {
            if (allNumbers.contains(number)) {
                allNumbers.remove(number);

                rowOne.remove(number);
                rowTwo.remove(number);
                rowThree.remove(number);
                rowFour.remove(number);
                rowFive.remove(number);
                columnOne.remove(number);
                columnTwo.remove(number);
                columnThree.remove(number);
                columnFour.remove(number);
                columnFive.remove(number);
            }
        }

        public boolean isFinished() {
            return rowOne.isEmpty() ||
                    rowTwo.isEmpty() ||
                    rowThree.isEmpty() ||
                    rowFour.isEmpty() ||
                    rowFive.isEmpty() ||
                    columnOne.isEmpty() ||
                    columnTwo.isEmpty() ||
                    columnThree.isEmpty() ||
                    columnFour.isEmpty() ||
                    columnFive.isEmpty();
        }

        public int calculateScore(String lastNumber) {
            int unmarkedValue = allNumbers.stream().mapToInt(Integer::parseInt).sum();
            return unmarkedValue * Integer.parseInt(lastNumber);
        }

    }

}
