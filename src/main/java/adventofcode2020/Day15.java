package adventofcode2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15 {

  public int solve(String inputString) {
    List<Integer> numbers = Arrays.stream(inputString.split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
    Map<Integer, Integer> lastIndex = new HashMap<>();
    Integer lastNumber = null;
    for (int i = 0; i < 30000000; i++) {
      int number;
      if (i >= numbers.size()) {
        number = !lastIndex.containsKey(lastNumber) ? 0 : (i - 1) - lastIndex.get(lastNumber);
      } else {
        number = numbers.get(i);
      }
      if (lastNumber != null) {
        lastIndex.put(lastNumber, i - 1);
      }
      lastNumber = number;
    }
    return lastNumber;
  }
}