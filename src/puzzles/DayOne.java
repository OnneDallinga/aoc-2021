package puzzles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DayOne {

    public static void puzzleOne() throws FileNotFoundException {
        File file = new File("src/dayonepuzzletwoinput.txt");
        Scanner scanner = new Scanner(file);

        int number1;
        int number2 = Integer.MAX_VALUE;
        int count = 0;

        while (scanner.hasNextLine()) {
            number1 = Integer.parseInt(scanner.nextLine());
            if (number1 > number2) {
                count++;
            }
            number2 = number1;
        }
        System.out.println(count);
    }

    public static int puzzleTwo(String path) throws FileNotFoundException {
        File file2 = new File(path);
        Scanner scanner = new Scanner(file2);

        int numberOne = 0;
        int numberTwo = 0;
        int numberThree = 0;
        int numberFour = 0;

        int index = 0;

        int count = 0;

        while (scanner.hasNextLine()) {
            int numberFromFile = Integer.parseInt(scanner.nextLine());

            if (index == 0) {
                numberOne = numberFromFile;
            } else if (index == 1) {
                numberTwo = numberFromFile;
            } else if (index == 2) {
                numberThree = numberFromFile;
            } else if (index % 4 == 3) {
                numberFour = numberFromFile;
                int sumA = (numberOne + numberTwo + numberThree);
                int sumB = (numberTwo + numberThree + numberFour);
                if (sumB > sumA) {
                    count++;
                }
            } else if (index % 4 == 0) {
                numberOne = numberFromFile;
                int sumA = (numberTwo + numberThree + numberFour);
                int sumB = (numberThree + numberFour + numberOne);
                if (sumB > sumA) {
                    count++;
                }
            } else if (index % 4 == 1) {
                numberTwo = numberFromFile;
                int sumA = (numberThree + numberFour + numberOne);
                int sumB = (numberFour + numberOne + numberTwo);
                if (sumB > sumA) {
                    count++;
                }
            } else if (index % 4 == 2) {
                numberThree = numberFromFile;
                int sumA = (numberFour + numberOne + numberTwo);
                int sumB = (numberOne + numberTwo + numberThree);
                if (sumB > sumA) {
                    count++;
                }
            }
            index++;
        }
        return count;
    }
}
