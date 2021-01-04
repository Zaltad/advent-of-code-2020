import static org.junit.Assert.assertEquals;

import adventofcode2020.Day15;
import adventofcode2020.Day16;
import java.io.IOException;
import org.junit.Test;

public class Day16Test {
  @Test
  public void solve_shouldSolveExample1() throws IOException {
    assertEquals(12,
        new Day16().solve(getClass().getClassLoader().getResourceAsStream("input16.txt"), "class"));
  }

  @Test
  public void solve_shouldSolveExample2() throws IOException {
    assertEquals(11,
        new Day16().solve(getClass().getClassLoader().getResourceAsStream("input16.txt"), "row"));
  }

  @Test
  public void solve_shouldSolveExample3() throws IOException {
    assertEquals(13,
        new Day16().solve(getClass().getClassLoader().getResourceAsStream("input16.txt"), "seat"));
  }
}
