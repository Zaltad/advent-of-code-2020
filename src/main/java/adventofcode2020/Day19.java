package adventofcode2020;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    System.out.println(lineGroups.get(1).stream()
        .filter(message -> {
          Integer index = validateMessage(message, List.of(0), 0, rules);
          return index != null && index == message.length();
        })
        .collect(Collectors.toList()));
    return lineGroups.get(1).stream()
        .filter(message -> {
          Integer index = validateMessage(message, List.of(0), 0, rules);
          return index != null && index == message.length();
        })
        .count();
  }

  private Integer validateMessage(String message, List<Integer> ruleIds, int index,
      Map<Integer, Rule> rules) {
    List<Rule> conditions = ruleIds.stream()
        .map(rules::get)
        .collect(Collectors.toList());
    for (Rule rule : conditions) {
      if (rule.getPattern() != null) {
        if (index < message.length() && message.charAt(index) == rule.getPattern()) {
          index++;
        } else {
          return null;
        }
      } else {
        int finalIndex = index;
        Optional<Integer> indexOptional = rule.getNestedRules().stream()
            .map(nestedRule -> validateMessage(message, nestedRule, finalIndex, rules))
            .filter(Objects::nonNull)
            .findFirst();
        if (indexOptional.isEmpty()) {
          return null;
        }
        index = indexOptional.get();
      }
    }
    return index;
  }

  @Value
  public static class Rule {

    int id;
    Character pattern;
    List<List<Integer>> nestedRules;
  }
}