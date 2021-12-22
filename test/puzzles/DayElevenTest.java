package puzzles;

import Utils.Utils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DayElevenTest {

    @Test
    public void testDayElevenPuzzleOne() {
        List<String> input = Utils.parseFile("test/resources/11.txt");
        assertEquals(35, new DayEleven().runPuzzle(input, 2));
        assertEquals(204, new DayEleven().runPuzzle(input, 10));
    }

}
