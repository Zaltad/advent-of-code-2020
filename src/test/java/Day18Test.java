import static org.junit.Assert.assertEquals;

import adventofcode2020.Day18;
import java.util.List;
import org.junit.Test;

public class Day18Test {

  @Test
  public void solve_shouldSolveExample1() {
    assertEquals(231,
        new Day18().solve(List.of("1 + 2 * 3 + 4 * 5 + 6")));
  }

  @Test
  public void solve_shouldSolveExample2() {
    assertEquals(51,
        new Day18().solve(List.of("1 + (2 * 3) + (4 * (5 + 6))")));
  }

  @Test
  public void solve_shouldSolveExample3() {
    assertEquals(46,
        new Day18().solve(List.of("2 * 3 + (4 * 5)")));
  }

  @Test
  public void solve_shouldSolveExample4() {
    assertEquals(1445,
        new Day18().solve(List.of("5 + (8 * 3 + 9 + 3 * 4 * 3)")));
  }

  @Test
  public void solve_shouldSolveExample5() {
    assertEquals(669060,
        new Day18().solve(List.of("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")));
  }

  @Test
  public void solve_shouldSolveExample6() {
    assertEquals(23340,
        new Day18().solve(List.of("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")));
  }
}
