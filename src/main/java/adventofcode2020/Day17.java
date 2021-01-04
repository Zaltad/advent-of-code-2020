package adventofcode2020;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Day17 {

  private static final int CYCLES = 6;
  private static final char INACTIVE = '.';
  private static final char ACTIVE = '#';

  public int solve(InputStream inputStream) throws FileNotFoundException {
    List<String> view = Utils.readLines(inputStream);
    int w = 1 + 2 * CYCLES;
    int z = 1 + 2 * CYCLES;
    int x = view.size() + 2 * CYCLES;
    int y = view.get(0).length() + 2 * CYCLES;
    char[][][][] cube = new char[w][z][x][y];
    char[][][][] newCube = new char[w][z][x][y];
    fillArrayWithInactive(cube);
    fillArrayWithInactive(newCube);
    for (int i = 0; i < view.size(); i++) {
      System.arraycopy(view.get(i).toCharArray(), 0, cube[CYCLES][CYCLES][i + CYCLES], CYCLES,
          view.get(0).length());
    }
    for (int i = 0; i < CYCLES; i++) {
      for (int width = 0; width < w; width++) {
        for (int layer = 0; layer < z; layer++) {
          for (int row = 0; row < x; row++) {
            for (int col = 0; col < y; col++) {
              int activeCount = 0;
              for (int wdelta = -1; wdelta <= 1; wdelta++) {
                for (int zdelta = -1; zdelta <= 1; zdelta++) {
                  for (int xdelta = -1; xdelta <= 1; xdelta++) {
                    for (int ydelta = -1; ydelta <= 1; ydelta++) {
                      int newWidth = width + wdelta;
                      int newLayer = layer + zdelta;
                      int newRow = row + xdelta;
                      int newCol = col + ydelta;
                      if (!(wdelta == 0 && zdelta == 0 && xdelta == 0 && ydelta == 0) &&
                          inBounds(newWidth, newLayer, newRow, newCol, w, z, x, y) &&
                          cube[newWidth][newLayer][newRow][newCol] == ACTIVE) {
                        activeCount++;
                      }
                    }
                  }
                }
              }
              if (cube[width][layer][row][col] == ACTIVE && List.of(2, 3).contains(activeCount)) {
                newCube[width][layer][row][col] = ACTIVE;
              } else if (cube[width][layer][row][col] == INACTIVE && activeCount == 3) {
                newCube[width][layer][row][col] = ACTIVE;
              } else {
                newCube[width][layer][row][col] = INACTIVE;
              }
            }
          }
        }
      }
      var tmp = cube;
      cube = newCube;
      newCube = tmp;
    }
    return countActiveLocations(cube, w, z, x, y);
  }

  private int countActiveLocations(char[][][][] array, int width, int layers, int rows,
      int cols) {
    int activeLocations = 0;
    for (int w = 0; w < width; w++) {
      for (int layer = 0; layer < layers; layer++) {
        for (int row = 0; row < rows; row++) {
          for (int col = 0; col < cols; col++) {
            if (array[w][layer][row][col] == ACTIVE) {
              activeLocations++;
            }
          }
        }
      }
    }
    return activeLocations;
  }

  private void fillArrayWithInactive(char[][][][] array) {
    for (char[][][] wAxis : array) {
      for (char[][] zAxis : wAxis) {
        for (char[] xAxis : zAxis) {
          Arrays.fill(xAxis, INACTIVE);
        }
      }
    }
  }

  private boolean inBounds(int newWidth, int newLayer, int newRow, int newCol, int width,
      int layers, int rows, int cols) {
    return 0 <= newWidth && newWidth < width &&
        0 <= newLayer && newLayer < layers &&
        0 <= newRow && newRow < rows &&
        0 <= newCol && newCol < cols;
  }
}