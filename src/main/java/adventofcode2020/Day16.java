package adventofcode2020;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Value;

public class Day16 {

  public long solve(InputStream inputStream, String prefix) throws IOException {
    List<List<String>> lineGroups = Utils.readLineGroups(inputStream);
    List<Condition> conditions = parseConditions(lineGroups.get(0));
    List<Integer> myTicket = parseTicketLine(lineGroups.get(1).get(1));
    List<List<Integer>> nearbyTickets = lineGroups.get(2).stream()
        .skip(1)
        .map(this::parseTicketLine)
        .collect(Collectors.toList());

    List<List<Integer>> validTickets = nearbyTickets.stream()
        .filter(ticket -> ticket.stream()
            .allMatch(position -> conditions.stream()
                .map(Condition::getRanges)
                .anyMatch(ranges -> checkIfRangeMatch(position, ranges))))
        .collect(Collectors.toList());
    validTickets.add(myTicket);
    Map<String, Set<Integer>> groups = conditions.stream().collect(
        Collectors
            .toMap(Condition::getName, condition -> IntStream.range(0, validTickets.get(0).size())
                .filter(i -> validTickets.stream()
                    .allMatch(ticket -> checkIfRangeMatch(ticket.get(i), condition.getRanges())))
                .boxed()
                .collect(Collectors.toSet())
            ));
    return getColumnsNumbers(groups).entrySet().stream()
        .filter(entry -> entry.getKey().startsWith(prefix))
        .mapToLong(entry -> myTicket.get(entry.getValue()))
        .reduce(1L, (left, right) -> left * right);
  }

  private Map<String, Integer> getColumnsNumbers(Map<String, Set<Integer>> groups) {
    Map<String, Integer> columns = new HashMap<>();
    List<String> remainingNames = new ArrayList<>(groups.keySet());
    while (!remainingNames.isEmpty()) {
      for (int i = 0; i < remainingNames.size(); i++) {
        String name = remainingNames.get(i);
        if (groups.get(name).size() == 1) {
          int column = groups.get(name).iterator().next();
          columns.put(name, column);
          remainingNames.remove(i);
          i--;
          for (Map.Entry<String, Set<Integer>> groupEntry : groups.entrySet()) {
            groupEntry.getValue().remove(column);
          }
        }
      }
    }
    return columns;
  }

  private boolean checkIfRangeMatch(int position, List<Range> ranges) {
    return ranges.stream()
        .anyMatch(range -> range.getFrom() <= position && position <= range.getTo());
  }

  private List<Integer> parseTicketLine(String line) {
    return Arrays.stream(line.split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }

  private List<Condition> parseConditions(List<String> linesGroup) {
    return linesGroup.stream()
        .map(line -> {
          String[] parts = line.split(": ");
          String name = parts[0];
          List<Range> ranges = Arrays.stream(parts[1].split(" or "))
              .map(rangesWithSeparator -> {
                int separator = rangesWithSeparator.indexOf("-");
                return new Range(Integer.parseInt(rangesWithSeparator.substring(0, separator)),
                    Integer.parseInt(rangesWithSeparator.substring(separator + 1)));
              })
              .collect(Collectors.toList());
          return new Condition(name, ranges);
        })
        .collect(Collectors.toList());
  }

  @Value
  public static class Condition {

    String name;
    List<Range> ranges;
  }

  @Value
  public static class Range {

    int from;
    int to;
  }
}