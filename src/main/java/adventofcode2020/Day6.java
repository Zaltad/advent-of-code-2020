package adventofcode2020;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 {

  public long solve(InputStream inputStream) throws IOException {
    List<List<String>> lineGroups = Utils.readLineGroups(inputStream);
    return lineGroups.stream().map(this::getMatchingAnswersPerGroup).mapToLong(x -> x).sum();
  }

  private int getUniqueAnswersPerGroup(List<String> linesGroup) {
    Set<Character> uniqueAnswers = linesGroup.stream()
        .flatMap(line -> line.chars().mapToObj(i -> (char) i))
        .collect(Collectors.toSet());
    return uniqueAnswers.size();
  }

  private long getMatchingAnswersPerGroup(List<String> linesGroup) {
    return IntStream.range('a', 'z' + 1).filter(i -> linesGroup.stream()
        .allMatch(line -> line.indexOf(i) != -1))
        .count();
  }
}