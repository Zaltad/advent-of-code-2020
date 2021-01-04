import static org.junit.Assert.assertEquals;

import adventofcode2020.Day3;
import adventofcode2020.Day6;
import java.io.IOException;
import org.junit.Test;

public class Day6Test {
  @Test
  public void solve_shouldSolveExample() throws IOException {
    assertEquals(6,
        new Day6().solve(getClass().getClassLoader().getResourceAsStream("input6.txt")));
  }
}
