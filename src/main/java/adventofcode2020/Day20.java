package adventofcode2020;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Value;

public class Day20 {

  private static final List<String> PATTERN = List.of(
      "                  # ",
      "#    ##    ##    ###",
      " #  #  #  #  #  #   ");
  private static final List<Position> MONSTER_CELLS = getMonsterCells();

  private static List<Position> getMonsterCells() {
    return IntStream.range(0, PATTERN.size()).mapToObj(
        i -> IntStream.range(0, PATTERN.get(i).length())
            .filter(j -> PATTERN.get(i).charAt(j) == '#').mapToObj(j -> new Position(i, j)))
        .flatMap(Function.identity()).collect(Collectors.toList());
  }

  public enum Side {
    N, E, S, W
  }

  public void solve(InputStream inputStream) throws IOException {
    List<List<String>> lineGroups = Utils.readLineGroups(inputStream);
    List<Tile> tiles = lineGroups.stream().map(linesGroup -> {
      int tileId = Integer.parseInt(linesGroup.get(0)
          .replace(":", "")
          .split(" ")[1]);
      int n = linesGroup.size() - 1;
      char[][] tile = new char[n][];
      for (int i = 0; i < n; i++) {
        tile[i] = linesGroup.get(i + 1).toCharArray();
      }
      List<char[][]> orientedTiles = new ArrayList<>();
      for (int i = 0; i < 4; i++) {
        orientedTiles.add(tile);
        orientedTiles.add(reverseTile(tile));
        tile = rotateTiling(tile);
      }
      return new Tile(tileId, orientedTiles);
    }).collect(Collectors.toList());
    findTiling(0, tiles, (int) Math.sqrt(tiles.size()), new ArrayList<>(), new ArrayList<>());
  }

  private List<char[][]> getCroppedTiles(List<Tile> usedTiles, List<Integer> rotations) {
    return IntStream.range(0, usedTiles.size())
        .mapToObj(i -> {
          char[][] tile = usedTiles.get(i).getOrientedTiles().get(rotations.get(i));
          int n = tile.length;
          char[][] croppedTile = new char[n - 2][];
          for (int j = 1; j < n - 1; j++) {
            croppedTile[j - 1] = new String(tile[j]).substring(1, n - 1).toCharArray();
          }
          return croppedTile;
        })
        .collect(Collectors.toList());
  }

  private char[][] mergeCroppedTiles(int blocksAmount, List<char[][]> croppedTiles) {
    int blockLength = croppedTiles.get(0).length;
    char[][] croppedTiling = new char[blocksAmount * blockLength][];
    for (int blockIndex = 0; blockIndex < blocksAmount; blockIndex++) {
      for (int line = blockIndex * blockLength;
          line < (blockIndex + 1) * blockLength; line++) {
        int lineOffset = line - blockIndex * blockLength;
        StringBuilder concatLine = new StringBuilder();
        for (int tileIndex = blockIndex * blocksAmount; tileIndex < (blockIndex + 1) * blocksAmount;
            tileIndex++) { //?
          concatLine.append(croppedTiles.get(tileIndex)[lineOffset]);
        }
        croppedTiling[line] = concatLine.toString().toCharArray();
      }
    }
    return croppedTiling;
  }

  private boolean matchesPattern(char[][] croppedTiling, int i, int j, boolean fill) {
    int n = croppedTiling.length;
    boolean allMatch = true;
    for (Position cell : MONSTER_CELLS) {
      int x = cell.getX() + i;
      int y = cell.getY() + j;
      if (fill) {
        croppedTiling[x][y] = 'O';
      }
      allMatch &= (x < n && y < n && croppedTiling[x][y] == '#');
    }
    return allMatch;
  }

  private boolean checkRotation(char[][] croppedTiling) {
    int n = croppedTiling.length;
    boolean found = false;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (matchesPattern(croppedTiling, i, j, false)) {
          found = true;
          matchesPattern(croppedTiling, i, j, true);
        }
      }
    }
    return found;
  }

  private void findTiling(int index, List<Tile> tiles, int blocksAmount, List<Tile> usedTiles,
      List<Integer> rotations) {
    if (index == tiles.size()) {
      List<char[][]> croppedTiles = getCroppedTiles(usedTiles, rotations);
      char[][] croppedTiling = mergeCroppedTiles(blocksAmount, croppedTiles);
      boolean found = false;
      for (int i = 0; i < 4; i++) {
        if (checkRotation(croppedTiling)) {
          found = true;
          break;
        }
        char[][] reversedTiling = reverseTile(croppedTiling);
        if (checkRotation(reversedTiling)) {
          found = true;
          croppedTiling = reversedTiling;
          break;
        }
        croppedTiling = rotateTiling(croppedTiling);
      }
      if (found) {
        for (var x : croppedTiling) {
          System.out.println(new String(x));
        }
        System.out.println(
            Arrays.stream(croppedTiling).map(String::new).flatMapToInt(x -> x.chars())
                .filter(ch -> ch == '#').count());
      }
    } else {
      for (int i = 0; i < tiles.size(); i++) {
        if (!usedTiles.contains(tiles.get(i))) {
          for (int orientation = 0; orientation < 8; orientation++) {
            if (index % blocksAmount != 0) { //not left-side
              String[] neighborBorders = getBorders(usedTiles.get(index - 1)
                  .getOrientedTiles().get(rotations.get(index - 1)));
              if (!neighborBorders[Side.E.ordinal()]
                  .equals(getBorders(tiles.get(i).getOrientedTiles().get(orientation))[Side.W
                      .ordinal()])) {
                continue;
              }
            }
            if (index - blocksAmount >= 0) { //not upper-side
              String[] neighborBorders = getBorders(usedTiles.get(index - blocksAmount)
                  .getOrientedTiles()
                  .get(rotations.get(index - blocksAmount)));
              if (!neighborBorders[Side.S.ordinal()].equals(
                  getBorders(tiles.get(i).getOrientedTiles().get(orientation))[Side.N.ordinal()])) {
                continue;
              }
            }
            usedTiles.add(tiles.get(i));
            rotations.add(orientation);
            findTiling(index + 1, tiles, blocksAmount, usedTiles, rotations);
            usedTiles.remove(usedTiles.size() - 1);
            rotations.remove(rotations.size() - 1);
          }
        }
      }
    }
  }

  private char[][] rotateTiling(char[][] tile) {
    int n = tile.length;
    char[][] rotatedTile = new char[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        rotatedTile[j][n - 1 - i] = tile[i][j];
      }
    }
    return rotatedTile;
  }

  private char[][] reverseTile(char[][] tile) {
    int n = tile.length;
    char[][] reversedTile = new char[n][n];
    for (int i = 0; i < n; i++) {
      reversedTile[i] = new StringBuilder(new String(tile[i])).reverse().toString().toCharArray();
    }
    return reversedTile;
  }

  private String[] getBorders(char[][] tile) {
    int n = tile.length;
    String[] borders = new String[4];
    borders[0] = String.valueOf(tile[0]);
    borders[1] = getSideBorder(tile, n - 1);
    borders[2] = String.valueOf(tile[n - 1]);
    borders[3] = getSideBorder(tile, 0);
    return borders;
  }

  private String getSideBorder(char[][] tile, int col) {
    StringBuilder border = new StringBuilder();
    for (char[] chars : tile) {
      border.append(chars[col]);
    }
    return border.toString();
  }

  @Value
  public static class Position {

    int x;
    int y;
  }

  @Value
  public static class Tile {

    int id;
    List<char[][]> orientedTiles;
  }
}