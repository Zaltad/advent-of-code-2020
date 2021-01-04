import static org.junit.Assert.assertEquals;

import adventofcode2020.Day7;
import java.io.IOException;
import org.junit.Test;

public class Day7Test {

  @Test
  public void solve_shouldSolveExample() throws IOException {
    assertEquals(126,
        new Day7().solve(getClass().getClassLoader().getResourceAsStream("input7.txt")));
  }
}
