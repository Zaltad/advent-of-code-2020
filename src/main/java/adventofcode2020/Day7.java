package adventofcode2020;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Value;

public class Day7 {

  private static final String CONTAIN_SEPARATOR = " contain ";
  private static final String NO_OTHER = "no other";

  public int solve(InputStream inputStream) throws FileNotFoundException {
    List<String> lines = Utils.readLines(inputStream);
    Map<String, List<BagInformation>> bagsWithCounts = parseContainedBagsWithCounts(lines);
    return findNestedBagsCount("shiny gold", bagsWithCounts, new HashMap<>());
  }

  private Map<String, List<BagInformation>> parseContainedBagsWithCounts(List<String> lines) {
    return lines.stream()
        .flatMap(line -> {
          String[] parts = line.split(CONTAIN_SEPARATOR);
          String bag = bagNameExtractor(parts[0]);
          String[] containedBags = parts[1].replace(".", "").split(", ");
          if (containedBags.length == 1 && containedBags[0].startsWith(NO_OTHER)) {
            return Stream.of();
          }
          return Arrays.stream(containedBags)
              .map(b -> {
                int countSeparatorIndex = b.indexOf(" ");
                return new BagInformation(
                    bag,
                    Integer.parseInt(b.substring(0, countSeparatorIndex)),
                    bagNameExtractor(b.substring(countSeparatorIndex + 1)));
              });
        })
        .collect(Collectors.groupingBy(BagInformation::getBag));
  }

  private int findNestedBagsCount(String bag,
      Map<String, List<BagInformation>> bagsWithCounts,
      Map<String, Integer> knownCounts) {
    if (knownCounts.containsKey(bag)) {
      return knownCounts.get(bag);
    }
    if (!bagsWithCounts.containsKey(bag)) {
      return 0;
    }
    int bagsInside = bagsWithCounts.get(bag).stream()
        .map(b -> b.getCount() * (1 + findNestedBagsCount(b.getContainingBag(), bagsWithCounts,
            knownCounts)))
        .reduce(0, Integer::sum);
    knownCounts.put(bag, bagsInside);
    return bagsInside;
  }

  private String bagNameExtractor(String bag) {
    return bag.substring(0, bag.contains(" bags") ? bag.indexOf(" bags") : bag.indexOf(" bag"));
  }

  @Value
  public static class BagInformation {

    String bag;
    int count;
    String containingBag;
  }
}