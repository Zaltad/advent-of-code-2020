import static org.junit.Assert.assertEquals;

import adventofcode2020.Day14;
import adventofcode2020.Day15;
import java.io.IOException;
import org.junit.Test;

public class Day15Test {
  @Test
  public void solve_shouldSolveExample1() {
    assertEquals(175594,
        new Day15().solve("0,3,6"));
  }

  @Test
  public void solve_shouldSolveExample2() {
    assertEquals(2578,
        new Day15().solve("1,3,2"));
  }

  @Test
  public void solve_shouldSolveExample3() {
    assertEquals(3544142,
        new Day15().solve("2,1,3"));
  }

  @Test
  public void solve_shouldSolveExample4() {
    assertEquals(261214,
        new Day15().solve("1,2,3"));
  }

  @Test
  public void solve_shouldSolveExample5() {
    assertEquals(18,
        new Day15().solve("3,2,1"));
  }

  @Test
  public void solve_shouldSolveExample6() {
    assertEquals(362,
        new Day15().solve("3,1,2"));
  }
}
