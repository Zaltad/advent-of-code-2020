import static org.junit.Assert.assertEquals;

import adventofcode2020.Day3;
import org.junit.Test;

public class Day3Test {

  @Test
  public void solve_shouldSolveExample() {
    assertEquals(336,
        new Day3().solve(getClass().getClassLoader().getResourceAsStream("input3.txt")));
  }
}
