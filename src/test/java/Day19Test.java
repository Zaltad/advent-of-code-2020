import static org.junit.Assert.assertEquals;

import adventofcode2020.Day19;
import java.io.IOException;
import org.junit.Test;

public class Day19Test {

  @Test
  public void solve_shouldSolveExample() throws IOException {
    assertEquals(12,
        new Day19().solve(getClass().getClassLoader().getResourceAsStream("input19.txt")));
  }
}
