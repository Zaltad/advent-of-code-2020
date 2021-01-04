package adventofcode2020;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

  private static final int ROWS_DELIMETER = 7;

  public List<Integer> solve(InputStream inputStream) throws IOException {
    List<String> lines = Utils.readLines(inputStream);
    List<Integer> seats = lines.stream()
        .map(pass -> {
          int row = binarySearch(pass.substring(0, ROWS_DELIMETER), 'F');
          int col = binarySearch(pass.substring(ROWS_DELIMETER), 'L');
          return row * 8 + col;
        })
        .sorted()
        .collect(Collectors.toList());
    List<Integer> result = new ArrayList<>();
    for (int i = 1; i < seats.size(); i++) {
      if (seats.get(i) - seats.get(i - 1) == 2) {
        result.add(seats.get(i) - 1);
      }
    }
    return result;
  }

  private int binarySearch(String pass, char lowerBoundNotation) {
    int n = pass.length();
    int low = 0;
    int high = (1 << n) - 1;
    int index = 0;
    while (index < n) {
      int mid = (low + high) / 2;
      if (pass.charAt(index) == lowerBoundNotation) {
        high = mid;
      } else {
        low = mid + 1;
      }
      index++;
    }
    return low;
  }
}