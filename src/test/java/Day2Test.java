import static org.junit.Assert.assertEquals;

import adventofcode2020.Day2;
import java.io.IOException;
import org.junit.Test;

public class Day2Test {

  @Test
  public void solve_shouldSolveExample() throws IOException {
    assertEquals(1,
        new Day2().solve(getClass().getClassLoader().getResourceAsStream("input2.txt")));
  }
}
