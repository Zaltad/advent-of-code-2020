package adventofcode2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day23 {

  public String solve(String line) throws IOException {
//    String line = "469217538";
//    String line = "389125467";
    List<Integer> numbers = line.chars()
        .mapToObj(digit -> Character.digit(digit, 10))
        .collect(Collectors.toList());
    IntStream.range(numbers.stream().max(Integer::compareTo).get() + 1, 100).forEach(
        numbers::add);
    int n = numbers.size();
    System.out.println(n);
    List<Integer> sortedNumbers = numbers.stream()
        .sorted()
        .collect(Collectors.toList());
    int index = 0;
    for (int i = 0; i < 1000; i++) {
      System.out.println(numbers.stream().limit(100).collect(Collectors.toList()));
      int number = numbers.get(index);
      List<Integer> neighbors = new ArrayList<>();
      for (int j = index + 1; j < index + 4; j++) {
        neighbors.add(numbers.get(j % n));
      }
      numbers.removeAll(neighbors);
      int sortedIndex = (n + sortedNumbers.indexOf(number) - 1) % n;
      while (!numbers.contains(sortedNumbers.get(sortedIndex))) {
        sortedIndex = (n + sortedIndex - 1) % n;
      }
      int destinationIndex = numbers.indexOf(sortedNumbers.get(sortedIndex));
      for (int j = destinationIndex + 1; j < destinationIndex + 4; j++) {
        numbers.add(j % n, neighbors.get(j - 1 - destinationIndex));
      }
      index = (numbers.indexOf(number) + 1) % n;
    }
    int firstIndex = numbers.indexOf(1);
    StringBuilder answer = new StringBuilder();
    for (int i = (firstIndex + 1) % n, j = 0; j < n - 1; i = (i + 1) % n, j++) {
      answer.append(numbers.get(i));
    }
    return answer.toString();
  }
}