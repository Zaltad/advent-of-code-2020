import static org.junit.Assert.assertEquals;

import adventofcode2020.Day9;
import org.junit.Test;

public class Day9Test {

  @Test
  public void solve_shouldSolveExample() {
    assertEquals(Integer.valueOf(77),
        new Day9().solve(getClass().getClassLoader().getResourceAsStream("input9.txt"), 127));
  }
}
