package adventofcode2020;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import lombok.Value;

public class Day18 {

  public long solve(InputStream inputStream) throws FileNotFoundException {
    List<String> lines = Utils.readLines(inputStream);
    return solve(lines);
  }

  public long solve(List<String> lines) {
    return lines.stream()
        .map(line -> line.replace(" ", ""))
        .map(this::evaluateExpression)
        .map(Member::getOperand)
        .reduce(Long::sum).get();
  }

  private Member evaluateExpression(String expr) {
    List<Member> members = new ArrayList<>();
    for (int i = 0; i < expr.length(); i++) {
      if (expr.charAt(i) == '(') {
        int j = findClosingBracket(expr, i + 1, 1);
        members.add(evaluateExpression(expr.substring(i + 1, j)));
        i = j;
      } else if (Character.isDigit(expr.charAt(i))) {
        members.add(new Member((long) Character.digit(expr.charAt(i), 10), null));
      } else {
        members.add(new Member(null, expr.charAt(i)));
      }
    }
    char addOp = '+';
    while (members.stream().anyMatch(m -> m.getOperator() != null && m.getOperator() == addOp)) {
      evaluateFirstBiOperation(addOp, members, Long::sum);
    }
    char mulOp = '*';
    while (members.stream().anyMatch(m -> m.getOperator() != null && m.getOperator() == mulOp)) {
      evaluateFirstBiOperation(mulOp, members, (x, y) -> x * y);
    }
    return new Member(members.get(0).getOperand(), null);
  }

  private void evaluateFirstBiOperation(char op, List<Member> members,
      BinaryOperator<Long> biOperation) {
    int index = members.indexOf(new Member(null, op));
    Long operand = biOperation
        .apply(members.get(index - 1).getOperand(), members.get(index + 1).getOperand());
    index--;
    for (int i = 0; i < 3; i++) {
      members.remove(index);
    }
    members.add(index, new Member(operand, null));
  }

  private int findClosingBracket(String expr, int index, int bracketsOpened) {
    while (bracketsOpened != 0) {
      if (expr.charAt(index) == '(') {
        bracketsOpened++;
      } else if (expr.charAt(index) == ')') {
        bracketsOpened--;
      }
      index++;
    }
    return index - 1;
  }

  @Value
  public static class Member {

    Long operand;
    Character operator;
  }
}