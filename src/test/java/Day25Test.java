import static org.junit.Assert.assertEquals;

import adventofcode2020.Day24;
import adventofcode2020.Day25;
import java.io.IOException;
import org.junit.Test;

public class Day25Test {
  @Test
  public void solve_shouldSolveExample() throws IOException {
    assertEquals(14897079,
        new Day25().solve(getClass().getClassLoader().getResourceAsStream("input25.txt")));
  }
}
