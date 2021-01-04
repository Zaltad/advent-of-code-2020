package adventofcode2020;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

  public static List<String> readLines(InputStream inputStream) {
    List<String> lines = new ArrayList<>();
    try (Scanner input = new Scanner(inputStream)) {
      while (input.hasNext()) {
        lines.add(input.nextLine());
      }
    }
    return lines;
  }

  public static List<List<String>> readLineGroups(InputStream inputStream) throws IOException {
    List<List<String>> lineGroups = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      List<String> linesGroup = new ArrayList<>();
      String line;
      while ((line = br.readLine()) != null) {
        if (line.isBlank()) {
          lineGroups.add(linesGroup);
          linesGroup = new ArrayList<>();
        } else {
          linesGroup.add(line);
        }
      }
      if (!linesGroup.isEmpty()) {
        lineGroups.add(linesGroup);
      }
    }
    return lineGroups;
  }

  public static List<Integer> readNumbers(InputStream inputStream) {
    Scanner scanner = new Scanner(inputStream);
    List<Integer> numbers = new ArrayList<>();
    while (scanner.hasNextInt()) {
      numbers.add(scanner.nextInt());
    }
    return numbers;
  }
}
