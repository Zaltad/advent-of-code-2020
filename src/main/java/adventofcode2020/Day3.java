package adventofcode2020;

import java.io.InputStream;
import java.util.List;
import lombok.Value;

public class Day3 {

  private final static char TREE = '#';

  public int solve(InputStream inputStream) {
    List<String> lines = Utils.readLines(inputStream);
    int rows = lines.size();
    int cols = lines.get(0).length();
    List<Step> steps = List.of(new Step(1, 1),
        new Step(1, 3),
        new Step(1, 5),
        new Step(1, 7),
        new Step(2, 1));
    return steps.stream()
        .map(step -> countMapTreesForStep(step.getRow(), step.getCol(), lines, rows, cols))
        .reduce((acc, count) -> acc * count)
        .get();
  }

  private int countMapTreesForStep(int rowStep, int colStep, List<String> lines, int rows,
      int cols) {
    int treesCount = 0;
    int rowIndex = 0;
    int colIndex = 0;
    while (rowIndex < rows) {
      if (lines.get(rowIndex).charAt(colIndex) == TREE) {
        treesCount++;
      }
      rowIndex += rowStep;
      colIndex = (colIndex + colStep) % cols;
    }
    return treesCount;
  }

  @Value
  public static class Step {

    int row;
    int col;
  }
}