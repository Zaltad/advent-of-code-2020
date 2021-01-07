import static org.junit.Assert.assertEquals;

import adventofcode2020.Day22;
import adventofcode2020.Day23;
import java.io.IOException;
import org.junit.Test;

public class Day23Test {

  @Test
  public void solve_shouldSolveExample() throws IOException {
    assertEquals(149245887792L,
        new Day23().solve("389125467"));
  }
}
