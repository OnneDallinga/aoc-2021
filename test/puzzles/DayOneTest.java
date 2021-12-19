package puzzles;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class DayOneTest {

    @Test
    public void testPuzzleTwo() throws FileNotFoundException {
        assertEquals(5, DayOne.puzzleTwo("src/resources/testinput.txt"));
    }

}
