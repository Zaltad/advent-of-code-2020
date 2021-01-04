import static org.junit.Assert.assertEquals;

import adventofcode2020.Day13;
import java.io.FileNotFoundException;
import org.junit.Test;

public class Day13Test {
  @Test
  public void solve_shouldSolveExample() throws FileNotFoundException {
    assertEquals(1068781,
        new Day13().solve(getClass().getClassLoader().getResourceAsStream("input13.txt")));
  }
}
