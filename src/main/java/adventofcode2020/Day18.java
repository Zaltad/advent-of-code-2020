package adventofcode2020;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import lombok.Value;

public class Day18 {

  public long solve(InputStream inputStream) throws FileNotFoundException {
    List<String> lines = Utils.readLines(inputStream);
    return lines.stream()
        .map(line -> line.replace(" ", ""))
        .map(line -> evaluateExpression(line, 0))
        .map(Result::getResult)
        .reduce(Long::sum).get();
  }

  private Result evaluateExpression(String expr, int index) {
    long acc = 0;
    for (int i = index; i < expr.length(); i++) {
      char ch = expr.charAt(i);
      if (ch == '(') {
        Result result = evaluateExpression(expr, i + 1);
        acc += result.getResult();
        i = result.getIndex();
      } else if (ch == ')') {
        return new Result(acc, i);
      } else if (i == index) {
        acc = getDigit(ch);
      }
      if (ch == '+') {
        if (expr.charAt(i + 1) == '(') {
          Result result = evaluateExpression(expr, i + 2);
          acc += result.getResult();
          i = result.getIndex();
        } else {
          acc += getDigit(expr.charAt(i + 1));
        }
      } else if (ch == '*') {
        if (expr.charAt(i + 1) == '(') {
          Result result = evaluateExpression(expr, i + 2);
          acc *= result.getResult();
          i = result.getIndex();
        } else {
          acc *= getDigit(expr.charAt(i + 1));
        }
      }
    }
    return new Result(acc, expr.length());
  }

  private int getDigit(char digit) {
    return digit - '0';
  }

  @Value
  public static class Result {

    long result;
    int index;
  }
}