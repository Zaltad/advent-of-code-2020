package adventofcode2020;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.IOUtils;

public class Day2 {

  public long solve(InputStream inputStream) throws IOException {
    List<String> lines = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
    return lines.stream().filter(this::isValid).count();
  }

  private boolean isValid(String line) {
    int dashIndex = line.indexOf('-');
    int index1 = Integer.parseInt(line.substring(0, dashIndex));
    int spaceIndex = line.indexOf(' ');
    int index2 = Integer.parseInt(line.substring(dashIndex + 1, spaceIndex));
    char letter = line.charAt(spaceIndex + 1);
    String password = line.substring(line.lastIndexOf(" ") + 1);
    return password.charAt(index1 - 1) == letter ^ password.charAt(index2 - 1) == letter;
  }
}