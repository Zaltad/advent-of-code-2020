package adventofcode2020;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import lombok.Value;

public class Day13 {

  public long solve(InputStream inputStream) throws FileNotFoundException {
    List<String> lines = Utils.readLines(inputStream);
    String[] buses = lines.get(1).split(",");
    List<Multiple> multiples = IntStream.range(0, buses.length)
        .filter(i -> !"x".equals(buses[i]))
        .mapToObj(i -> new Multiple(Long.parseLong(buses[i]), i))
        .collect(Collectors.toList());
    long product = multiples.stream().map(Multiple::getNumber)
        .reduce(1L, (acc, right) -> acc * right);
    return multiples.stream().map(multiple -> {
      long x = product / multiple.getNumber();
      long remainder = (multiple.getNumber() - multiple.getIndex()) % multiple.getNumber();
      long xInversed = LongStream.range(0, multiple.getNumber() - 2)
          .reduce(1L, (acc, right) -> (acc * x) % multiple.getNumber());
      assert ((x * xInversed) % multiple.getNumber() == 1);
      return remainder * x * xInversed;
    }).reduce(Long::sum).get() % product;
  }

  @Value
  public static class Multiple {

    long number;
    long index;
  }
}