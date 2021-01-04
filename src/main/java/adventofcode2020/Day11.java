package adventofcode2020;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.Value;

public class Day11 {

  private static final List<Step> STEPS = getSteps();

  private static List<Step> getSteps() {
    List<Step> steps = new ArrayList<>();
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (!(i == 0 && j == 0)) {
          steps.add(new Step(i, j));
        }
      }
    }
    return steps;
  }

  public int solve(InputStream inputStream) throws FileNotFoundException {
    List<String> seating = Utils.readLines(inputStream);
    int rows = seating.size();
    int cols = seating.get(0).length();
    char[][] seatingArray = new char[rows][];
    char[][] newSeatingArray = new char[rows][cols];
    for (int i = 0; i < rows; i++) {
      seatingArray[i] = seating.get(i).toCharArray();
    }
    boolean changesMade = true;
    while (changesMade) {
      changesMade = false;
      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
          char character = seatingArray[row][col];
          if (character != '.') {
            int occupiedSeats = countNeighbouringOccupiedSeats(seatingArray, row, col, rows, cols);
            if (character == 'L' && occupiedSeats == 0) {
              newSeatingArray[row][col] = '#';
              changesMade = true;
            } else if (character == '#' && occupiedSeats >= 5) {
              newSeatingArray[row][col] = 'L';
              changesMade = true;
            } else {
              newSeatingArray[row][col] = seatingArray[row][col];
            }
          } else {
            newSeatingArray[row][col] = seatingArray[row][col];
          }
        }
      }
      var tmp = seatingArray;
      seatingArray = newSeatingArray;
      newSeatingArray = tmp;
    }
    return countOccupiedSeats(seatingArray, rows, cols);
  }

  private int countOccupiedSeats(char[][] seatingArray, int rows, int cols) {
    int occupiedSeats = 0;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (seatingArray[row][col] == '#') {
          occupiedSeats++;
        }
      }
    }
    return occupiedSeats;
  }

  private int countNeighbouringOccupiedSeats(char[][] seatingArray, int row, int col,
      int rows, int cols) {
    int occupiedSeats = 0;
    for (Step step : STEPS) {
      int newRow = row + step.getRow();
      int newCol = col + step.getCol();
      while (inBounds(newRow, newCol, rows, cols)) {
        if (seatingArray[newRow][newCol] != '.') {
          if (seatingArray[newRow][newCol] == '#') {
            occupiedSeats++;
          }
          break;
        }
        newRow += step.getRow();
        newCol += step.getCol();
      }
    }
    return occupiedSeats;
  }

  private boolean inBounds(int newRow, int newCol, int rows, int cols) {
    return 0 <= newRow && newRow < rows && 0 <= newCol && newCol < cols;
  }

  @Value
  public static class Step {

    int row;
    int col;
  }
}