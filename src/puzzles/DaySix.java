package puzzles;

import Utils.Utils;

import java.util.Arrays;
import java.util.List;

public class DaySix {

    public long puzzleOne() {
        return calculateFishes(80);
    }

    public long puzzleTwo() {
        List<String> input = Utils.parseFile("src/resources/0601.txt");
        List<Integer> fishes = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .toList();

        long zeroFishes = 0;
        long oneFishes = 0;
        long twoFishes = 0;
        long threeFishes = 0;
        long fourFishes = 0;
        long fiveFishes = 0;
        long sixFishes = 0;
        long sevenFishes = 0;
        long eightFishes = 0;

        for (Integer fish : fishes) {
            switch (fish) {
                case 0 -> zeroFishes++;
                case 1 -> oneFishes++;
                case 2 -> twoFishes++;
                case 3 -> threeFishes++;
                case 4 -> fourFishes++;
                case 5 -> fiveFishes++;
                case 6 -> sixFishes++;
            }
        }

        int currentDay = 1;

        while (currentDay <= 256) {
            currentDay++;

            long reproducingFishes = zeroFishes;

            zeroFishes = oneFishes;
            oneFishes = twoFishes;
            twoFishes = threeFishes;
            threeFishes = fourFishes;
            fourFishes = fiveFishes;
            fiveFishes = sixFishes;
            sixFishes = sevenFishes + reproducingFishes;
            sevenFishes = eightFishes;
            eightFishes = reproducingFishes;
        }

        return zeroFishes + oneFishes + twoFishes + threeFishes + fourFishes + fiveFishes + sixFishes + sevenFishes + eightFishes;
    }

    private long calculateFishes(int amountOfDays) {
        List<String> input = Utils.parseFile("src/resources/0601.txt");
        List<LanternFish> lanternFishes = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .map(LanternFish::new)
                .toList();

        int currentDay = 1;

        while (currentDay <= amountOfDays) {
            currentDay++;
            lanternFishes = lanternFishes.stream()
                    .flatMap(lanternFish -> lanternFish.advanceADay().stream())
                    .toList();
            System.out.println(currentDay);
        }

        return lanternFishes.size();
    }

    private static class LanternFish {
        int timer;

        LanternFish(int timer) {
            this.timer = timer;
        }

        public List<LanternFish> advanceADay() {
            timer--;
            if (timer == -1) {
                return List.of(new LanternFish(8), new LanternFish(6));
            }
            return List.of(this);
        }
    }
}
