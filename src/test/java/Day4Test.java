import static org.junit.Assert.assertEquals;

import adventofcode2020.Day4;
import java.io.IOException;
import org.junit.Test;

public class Day4Test {

  @Test
  public void solve_shouldSolveAllInvalidExample() throws IOException {
    assertEquals(0,
        new Day4().solve(getClass().getClassLoader().getResourceAsStream("input4a.txt")));
  }

  @Test
  public void solve_shouldSolveAllValidExample() throws IOException {
    assertEquals(4,
        new Day4().solve(getClass().getClassLoader().getResourceAsStream("input4b.txt")));
  }
}
