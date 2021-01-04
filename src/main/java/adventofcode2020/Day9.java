package adventofcode2020;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Day9 {

  private static final int WINDOW = 25;

  public Integer solve(InputStream inputStream, int foundNumber) {
    List<Integer> numbers = Utils.readNumbers(inputStream);
    long sum = 0;
    int i = 0;
    int j = 0;
    int n = numbers.size();
    while (j < n) {
      while (j < n && sum < foundNumber) {
        sum += numbers.get(j);
        j++;
      }
      while (i <= j && sum > foundNumber) {
        sum -= numbers.get(i);
        i++;
      }
      if (sum == foundNumber) {
        return Stream.of(numbers.stream().skip(i).limit(j - i + 1).reduce(Integer::min),
            numbers.stream().skip(i).limit(j - i + 1).reduce(Integer::max))
            .flatMap(Optional::stream)
            .reduce(Integer::sum).get();
      }
    }
    return null;
  }

  private void findFirstNonSummingNumber(List<Integer> numbers) {
    Map<Integer, Integer> count = new TreeMap<>();
    for (int i = 0; i < WINDOW; i++) {
      count.merge(numbers.get(i), 1, Integer::sum);
    }
    for (int i = WINDOW; i < numbers.size(); i++) {
      int need = numbers.get(i);
      boolean foundPair = false;
      for (int first : count.keySet()) {
        int left = need - first;
        if (need == left) {
          if (count.get(need) >= 2) {
            foundPair = true;
            break;
          }
        } else if (count.getOrDefault(left, 0) >= 1) {
          foundPair = true;
          break;
        }
        if (2 * first > need) {
          break;
        }
      }
      if (!foundPair) {
        break;
      }
      int droppingNumber = numbers.get(i - WINDOW);
      count.put(droppingNumber, count.get(droppingNumber) - 1);
      count.merge(numbers.get(i), 1, Integer::sum);
    }
  }
}