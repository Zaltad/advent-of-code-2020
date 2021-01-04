import static org.junit.Assert.assertEquals;

import adventofcode2020.Day17;
import java.io.FileNotFoundException;
import org.junit.Test;

public class Day17Test {

  @Test
  public void solve_shouldSolveExample() throws FileNotFoundException {
    assertEquals(848,
        new Day17().solve(getClass().getClassLoader().getResourceAsStream("input17.txt")));
  }
}
