package adventofcode2020;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Value;

public class Day19 {

  public long solve(InputStream inputStream) throws IOException {
    List<List<String>> lineGroups = Utils.readLineGroups(inputStream);
    Map<Integer, Rule> rules = lineGroups.get(0).stream().map(line -> {
      String[] parts = line.split(": ");
      int ruleId = Integer.parseInt(parts[0]);
      if (parts[1].contains("\"")) {
        char pattern = parts[1].replace("\"", "").charAt(0);
        return new Rule(ruleId, pattern, List.of());
      } else {
        List<List<Integer>> nestedRules = Arrays.stream(parts[1].split(" \\| "))
            .map(ruleIds -> Arrays.stream(ruleIds.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList()))
            .collect(Collectors.toList());
        return new Rule(ruleId, null, nestedRules);
      }
    })
        .collect(Collectors.toMap(Rule::getId, Function.identity()));
    return lineGroups.get(1).stream()
        .filter(message -> {
          Set<Integer> indexes = validateMessage(message, List.of(0), 0, rules);
          return indexes.stream().anyMatch(ind -> ind == message.length());
        })
        .count();
  }

  private Set<Integer> validateMessage(String message, List<Integer> ruleIds, int index,
      Map<Integer, Rule> rules) {
    if (index > message.length()) {
      return Set.of();
    }
    if (ruleIds.isEmpty()) {
      return Set.of(index);
    }
    List<Rule> conditions = ruleIds.stream()
        .map(rules::get)
        .collect(Collectors.toList());
    Set<Integer> indexes = Set.of(index);
    for (Rule rule : conditions) {
      if (indexes.isEmpty()) {
        return Set.of();
      }
      if (rule.getPattern() != null) {
        indexes = indexes.stream()
            .filter(ind -> ind < message.length() && message.charAt(ind) == rule.getPattern())
            .map(ind -> ind + 1)
            .collect(Collectors.toSet());
      } else {
        indexes = indexes.stream()
            .flatMap(ind -> rule.getNestedRules().stream()
                .map(nestedRules -> validateMessage(message, nestedRules, ind, rules))
                .flatMap(Collection::stream)
            ).collect(Collectors.toSet());
      }
    }
    return indexes;
  }

  @Value
  public static class Rule {

    int id;
    Character pattern;
    List<List<Integer>> nestedRules;
  }
}