import static org.junit.Assert.assertEquals;

import adventofcode2020.Day21;
import adventofcode2020.Day22;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;

public class Day22Test {
  @Test
  public void solve_shouldSolveExample() throws IOException {
    assertEquals(291,
        new Day22().solve(getClass().getClassLoader().getResourceAsStream("input22.txt")));
  }
}
