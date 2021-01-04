package adventofcode2020;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {

  private static final Map<String, Predicate<String>> REQUIRED_FIELDS = Map.of(
      "byr", (year) -> {
        int yr = Integer.parseInt(year);
        return 1920 <= yr && yr <= 2002;
      },
      "iyr", (year) -> {
        int yr = Integer.parseInt(year);
        return 2010 <= yr && yr <= 2020;
      },
      "eyr", (year) -> {
        int yr = Integer.parseInt(year);
        return 2020 <= yr && yr <= 2030;
      },
      "hgt", (height) -> {
        if (!(height.endsWith("cm") || height.endsWith("in"))) {
          return false;
        }
        boolean inCm = height.endsWith("cm");
        String hgtWithoutMeasurement = height
            .substring(0, inCm ? height.indexOf("cm") : height.indexOf("in"));
        int hgt = Integer.parseInt(hgtWithoutMeasurement);
        if (inCm) {
          return 150 <= hgt && hgt <= 193;
        }
        return 59 <= hgt && hgt <= 76;
      },
      "hcl", (color) -> {
        Pattern pattern = Pattern.compile("^#[a-f0-9]{6}$");
        Matcher matcher = pattern.matcher(color);
        return matcher.matches();
      },
      "ecl", (color) -> List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(color),
      "pid", (id) -> {
        Pattern pattern = Pattern.compile("^[0-9]{9}$");
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
      }
  );

  public long solve(InputStream inputStream) throws IOException {
    List<List<String>> lineGroups = Utils.readLineGroups(inputStream);
    return lineGroups.stream().filter(this::linesGroupContainsRequiredFields).count();
  }

  private boolean linesGroupContainsRequiredFields(List<String> linesGroup) {
    Map<String, String> fields = linesGroup.stream().map(line -> line.split(" "))
        .flatMap(Stream::of)
        .collect(Collectors.toMap(identifier -> identifier.substring(0, identifier.indexOf(":")),
            identifier -> identifier.substring(identifier.indexOf(":") + 1)));
    return REQUIRED_FIELDS.entrySet().stream().allMatch(
        entry -> fields.containsKey(entry.getKey()) &&
            entry.getValue().test(fields.get(entry.getKey()))
    );
  }
}