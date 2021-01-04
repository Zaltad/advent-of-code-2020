import static org.junit.Assert.assertEquals;

import adventofcode2020.Day7;
import adventofcode2020.Day8;
import java.io.IOException;
import org.junit.Test;

public class Day8Test {
  @Test
  public void solve_shouldSolveExample() throws IOException {
    assertEquals(Integer.valueOf(8),
        new Day8().solve(getClass().getClassLoader().getResourceAsStream("input8.txt")));
  }
}
