import static org.junit.Assert.assertEquals;

import adventofcode2020.Day13;
import adventofcode2020.Day14;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;

public class Day14Test {
  @Test
  public void solve_shouldSolveExample() throws IOException {
    assertEquals(208,
        new Day14().solve(getClass().getClassLoader().getResourceAsStream("input14.txt")));
  }
}
