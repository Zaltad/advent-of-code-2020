import static org.junit.Assert.assertEquals;

import adventofcode2020.Day11;
import adventofcode2020.Day12;
import java.io.FileNotFoundException;
import org.junit.Test;

public class Day12Test {
  @Test
  public void solve_shouldSolveExample() throws FileNotFoundException {
    assertEquals(286,
        new Day12().solve(getClass().getClassLoader().getResourceAsStream("input12.txt")));
  }
}
