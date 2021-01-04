import static org.junit.Assert.assertEquals;

import adventofcode2020.Day24;
import java.io.IOException;
import org.junit.Test;

public class Day24Test {

  @Test
  public void solve_shouldSolveExample() throws IOException {
    assertEquals(2208,
        new Day24().solve(getClass().getClassLoader().getResourceAsStream("input24.txt")));
  }
}
