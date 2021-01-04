import static org.junit.Assert.assertEquals;

import adventofcode2020.Day21;
import java.io.FileNotFoundException;
import org.junit.Test;

public class Day21Test {

  @Test
  public void solve_shouldSolveExample() throws FileNotFoundException {
    assertEquals("mxmxvkd,sqjhc,fvjkl",
        new Day21().solve(getClass().getClassLoader().getResourceAsStream("input21.txt")));
  }
}
