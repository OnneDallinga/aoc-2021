package puzzles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DayTwo {

    public static int puzzleOne(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);

        int horizontalPosition = 0;
        int depth = 0;

        while (scanner.hasNextLine()) {
            String[] order = scanner.nextLine().split(" ");
            String direction = order[0];
            int change = Integer.parseInt(order[1]);

            switch (direction) {
                case "forward":
                    horizontalPosition += change;
                    break;
                case "down":
                    depth += change;
                    break;
                case "up":
                    depth -= change;
                    break;
            }
        }

        return horizontalPosition * depth;
    }

    public static int puzzleTwo(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);

        int aim = 0;
        int horizontalPosition = 0;
        int depth = 0;

        while (scanner.hasNextLine()) {
            String[] order = scanner.nextLine().split(" ");
            String direction = order[0];
            int change = Integer.parseInt(order[1]);

            switch (direction) {
                case "forward":
                    horizontalPosition += change;
                    depth += (aim * change);
                    break;
                case "down":
                    aim += change;
                    break;
                case "up":
                    aim -= change;
                    break;
            }
        }

        return horizontalPosition * depth;
    }
}
