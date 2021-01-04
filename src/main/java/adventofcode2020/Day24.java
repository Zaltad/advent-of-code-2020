package adventofcode2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Value;

public class Day24 {

  public enum Direction {
    E(new Position(1, 0)), SE(new Position(0, -1)), SW(new Position(-1, -1)),
    W(new Position(-1, 0)), NW(new Position(0, 1)), NE(new Position(1, 1));

    private final Position position;

    Direction(Position position) {
      this.position = position;
    }

    public Position getPosition() {
      return position;
    }
  }

  public int solve(InputStream inputStream) throws FileNotFoundException {
    List<String> lines = Utils.readLines(inputStream);
    Map<Position, Long> flipCounts = getFlipCounts(lines);
    return countBlackTilesAfterIterations(flipCounts);
  }

  private int countBlackTilesAfterIterations(Map<Position, Long> flipCounts) {
    Set<Position> blackTiles = flipCounts.entrySet().stream()
        .filter((e) -> e.getValue() % 2 == 1).map(Entry::getKey).collect(Collectors.toSet());
    for (int i = 0; i < 100; i++) {
      Set<Position> finalBlackTiles = blackTiles;
      Set<Position> newBlackTiles = blackTiles.stream()
          .filter(tile -> List.of(1, 2).contains((int) getNeighbors(tile)
              .filter(finalBlackTiles::contains).count()))
          .collect(Collectors.toSet());
      Set<Position> whiteTilesWithBlackTileNeighbor = blackTiles.stream()
          .flatMap(tile -> getNeighbors(tile)
              .filter(pos -> !finalBlackTiles.contains(pos))
          ).collect(Collectors.toSet());
      newBlackTiles
          .addAll(whiteTilesWithBlackTileNeighbor.stream().filter(tile -> getNeighbors(tile)
              .filter(finalBlackTiles::contains).count() == 2).collect(Collectors.toSet()));
      blackTiles = newBlackTiles;
    }
    return blackTiles.size();
  }

  private Map<Position, Long> getFlipCounts(List<String> lines) {
    return lines.stream().map(line -> {
      List<Direction> directions = new ArrayList<>();
      for (int i = 0; i < line.length(); i++) {
        if (List.of('s', 'n').contains(line.charAt(i))) {
          directions.add(Direction.valueOf(line.substring(i, i + 2).toUpperCase()));
          i++;
        } else {
          directions.add(Direction.valueOf(String.valueOf(line.charAt(i)).toUpperCase()));
        }
      }
      return directions.stream().reduce(new Position(0, 0),
          (acc, pos) -> new Position(acc.getX() + pos.getPosition().getX(),
              acc.getY() + pos.getPosition().getY()),
          (acc, pos) -> new Position(acc.getX() + pos.getX(), acc.getY() + pos.getY()));
    }).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  private Stream<Position> getNeighbors(Position tile) {
    return Arrays.stream(Direction.values())
        .map(dir -> {
          Position move = dir.getPosition();
          return new Position(tile.getX() + move.getX(), tile.getY() + move.getY());
        });
  }

  @Value
  public static class Position {

    int x;
    int y;
  }
}