import static org.junit.Assert.assertEquals;

import adventofcode2020.Day10;
import adventofcode2020.Day9;
import java.io.FileNotFoundException;
import org.junit.Test;

public class Day10Test {
  @Test
  public void solve_shouldSolveSmallExample() throws FileNotFoundException {
    assertEquals(8,
        new Day10().solve(getClass().getClassLoader().getResourceAsStream("input10a.txt")));
  }

  @Test
  public void solve_shouldSolveLargerExample() throws FileNotFoundException {
    assertEquals(19208,
        new Day10().solve(getClass().getClassLoader().getResourceAsStream("input10b.txt")));
  }
}
