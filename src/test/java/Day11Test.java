import static org.junit.Assert.assertEquals;

import adventofcode2020.Day11;
import java.io.FileNotFoundException;
import org.junit.Test;

public class Day11Test {

  @Test
  public void solve_shouldSolveExample() throws FileNotFoundException {
    assertEquals(26,
        new Day11().solve(getClass().getClassLoader().getResourceAsStream("input11.txt")));
  }
}
