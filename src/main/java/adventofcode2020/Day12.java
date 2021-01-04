package adventofcode2020;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

public class Day12 {

  public enum Action {
    N, E, S, W, L, R, F;

    public Action next(int delta) {
      return values()[(4 + this.ordinal() + delta) % 4];
    }

    public static List<Action> xAxis() {
      return List.of(E, W);
    }

    public static List<Action> yAxis() {
      return List.of(N, S);
    }

    public static int isPositive(Action action) {
      return List.of(N, E).contains(action) ? 1 : -1;
    }
  }

  public int solve(InputStream inputStream) throws FileNotFoundException {
    List<String> lines = Utils.readLines(inputStream);
    List<ActionValue> actionValues = lines.stream()
        .map(line -> new ActionValue(Action.valueOf(line.substring(0, 1)),
            Integer.parseInt(line.substring(1))))
        .collect(Collectors.toList());
    Position position = new Position(0, 0);
    Position waypoint = new Position(10, 1);
    for (ActionValue actionValue : actionValues) {
      switch (actionValue.getAction()) {
        case N:
        case S:
          waypoint.addY((actionValue.getAction() == Action.N ? 1 : -1) * actionValue.getValue());
          break;
        case E:
        case W:
          waypoint.addX((actionValue.getAction() == Action.E ? 1 : -1) * actionValue.getValue());
          break;
        case L:
        case R:
          int times = (actionValue.getValue() % 360) / 90;
          int x = waypoint.getX();
          int y = waypoint.getY();
          Action xAxis = ((x > 0) ? Action.E : Action.W);
          Action yAxis = ((y > 0) ? Action.N : Action.S);
          xAxis = xAxis.next((actionValue.getAction() == Action.R) ? times : -times);
          yAxis = yAxis.next((actionValue.getAction() == Action.R) ? times : -times);
          if (Action.xAxis().contains(yAxis)) {
            var tempXAxis = xAxis;
            xAxis = yAxis;
            yAxis = tempXAxis;
            var tempX = x;
            x = y;
            y = tempX;
          }
          waypoint = new Position(Action.isPositive(xAxis) * Math.abs(x),
              Action.isPositive(yAxis) * Math.abs(y));
          break;
        case F:
          position.addX(waypoint.getX() * actionValue.getValue());
          position.addY(waypoint.getY() * actionValue.getValue());
          break;
      }
    }
    return Math.abs(position.getX()) + Math.abs(position.getY());
  }

  @Getter
  @AllArgsConstructor
  public static class Position {

    private int x;
    private int y;

    public void addX(int x) {
      this.x += x;
    }

    public void addY(int y) {
      this.y += y;
    }
  }

  @Value
  public static class ActionValue {

    Action action;
    int value;
  }
}