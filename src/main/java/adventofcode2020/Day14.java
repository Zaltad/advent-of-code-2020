package adventofcode2020;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Value;

public class Day14 {

  private static final String SEPARATOR = " = ";
  private static final int MAX_BITS = 36;

  public long solve(InputStream inputStream) throws IOException {
    List<MaskValues> lineGroups = readLineGroups(inputStream);
    Map<Long, Long> latestValues = lineGroups.stream()
        .flatMap(maskValues -> maskValues.getLocations().stream()
            .map(locationValue -> applyOperation(locationValue.getLocation(), maskValues.getMask(),
                locationValue.getValue()))
            .flatMap(Collection::stream)
        )
        .collect(Collectors
            .toMap(LocationValue::getLocation, LocationValue::getValue, (left, right) -> right));
    return latestValues.values().stream()
        .reduce(0L, Long::sum);
  }

  private void findAllAddresses(char[] locationInBinary, int index, List<Long> addresses) {
    if (index == MAX_BITS) {
      addresses.add(Long.parseLong(new StringBuilder(new String(locationInBinary))
          .reverse()
          .toString(), 2));
    } else if (locationInBinary[index] == 'X') {
      for (int bit = 0; bit <= 1; bit++) {
        locationInBinary[index] = Character.forDigit(bit, 10);
        findAllAddresses(locationInBinary, index + 1, addresses);
        locationInBinary[index] = 'X';
      }
    } else {
      findAllAddresses(locationInBinary, index + 1, addresses);
    }
  }

  private List<LocationValue> applyOperation(Long location, String mask, Long value) {
    char[] locationInBinaryReversed = new char[MAX_BITS];
    Arrays.fill(locationInBinaryReversed, '0');
    String locationBinaryString = Long.toBinaryString(location);
    System.arraycopy(new StringBuilder(locationBinaryString)
        .reverse()
        .toString()
        .toCharArray(), 0, locationInBinaryReversed, 0, locationBinaryString.length());
    String maskReversed = new StringBuilder(mask)
        .reverse()
        .toString();
    for (int i = 0; i < MAX_BITS; i++) {
      if (maskReversed.charAt(i) != '0') {
        locationInBinaryReversed[i] = maskReversed.charAt(i);
      }
    }
    List<Long> addresses = new ArrayList<>();
    findAllAddresses(locationInBinaryReversed, 0, addresses);
    return addresses.stream()
        .map(address -> new LocationValue(address, value))
        .collect(Collectors.toList());
  }

  private List<MaskValues> readLineGroups(InputStream inputStream) throws IOException {
    List<MaskValues> lineGroups = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String mask = null;
      List<LocationValue> linesGroup = new ArrayList<>();
      String line;
      while ((line = br.readLine()) != null) {
        if (line.startsWith("mask")) {
          if (mask != null) {
            lineGroups.add(new MaskValues(mask, linesGroup));
          }
          mask = line.split(SEPARATOR)[1];
          linesGroup = new ArrayList<>();
        } else {
          Long location = Long.parseLong(line.substring(line.indexOf("[") + 1, line.indexOf("]")));
          Long value = Long.parseLong(line.split(SEPARATOR)[1]);
          linesGroup.add(new LocationValue(location, value));
        }
      }
      if (!linesGroup.isEmpty()) {
        lineGroups.add(new MaskValues(mask, linesGroup));
      }
    }
    return lineGroups;
  }

  @Value
  public static class MaskValues {

    String mask;
    List<LocationValue> locations;
  }

  @Value
  public static class LocationValue {

    Long location;
    Long value;
  }
}