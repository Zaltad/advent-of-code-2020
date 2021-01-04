package adventofcode2020;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {

  public long solve(InputStream inputStream) throws FileNotFoundException {
    List<Integer> numbers = Utils.readNumbers(inputStream);
    numbers.add(0);
    List<Integer> sortedNumbers = numbers.stream().sorted().collect(Collectors.toList());
    sortedNumbers.add(sortedNumbers.get(sortedNumbers.size() - 1) + 3);
    int n = sortedNumbers.size();
    long[] dp = new long[n];
    dp[0] = 1;
    for (int i = 1; i < n; i++) {
      int number = sortedNumbers.get(i);
      for (int j = i - 1; j >= 0 && sortedNumbers.get(j) + 3 >= number; j--) {
        dp[i] += dp[j];
      }
    }
    return dp[n - 1];
  }
}