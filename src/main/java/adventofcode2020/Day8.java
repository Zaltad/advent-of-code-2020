package adventofcode2020;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

public class Day8 {

  public enum OperationType {
    NOP, ACC, JMP
  }

  public Integer solve(InputStream inputStream) throws FileNotFoundException {
    List<String> lines = Utils.readLines(inputStream);
    List<Operation> operations = lines.stream().map(line -> {
      String[] parts = line.split(" ");
      OperationType operationType = OperationType.valueOf(parts[0].toUpperCase());
      int argument = Integer.parseInt(parts[1]);
      return new Operation(operationType, argument);
    }).collect(Collectors.toList());
    return findNonLoopingAccumulator(operations);
  }

  private Integer findNonLoopingAccumulator(List<Operation> operations) {
    for (Operation operation : operations) {
      ExecutionResult executionResult;
      if (operation.getOperationType() == OperationType.NOP) {
        operation.setOperationType(OperationType.JMP);
        executionResult = executeOperations(operations);
        operation.setOperationType(OperationType.NOP);
      } else if (operation.getOperationType() == OperationType.JMP) {
        operation.setOperationType(OperationType.NOP);
        executionResult = executeOperations(operations);
        operation.setOperationType(OperationType.JMP);
      } else {
        continue;
      }
      if (!executionResult.isLoopFound()) {
        return executionResult.getAcc();
      }
    }
    return null;
  }

  private ExecutionResult executeOperations(List<Operation> operations) {
    Set<Integer> executedOps = new HashSet<>();
    int index = 0;
    int acc = 0;
    while (true) {
      if (index == operations.size()) {
        return new ExecutionResult(false, acc);
      }
      if (executedOps.contains(index)) {
        break;
      }
      executedOps.add(index);
      Operation currentOperation = operations.get(index);
      switch (currentOperation.getOperationType()) {
        case NOP: {
          index++;
          break;
        }
        case ACC: {
          acc += currentOperation.getArgument();
          index++;
          break;
        }
        case JMP: {
          index += currentOperation.getArgument();
          break;
        }
      }
    }
    return new ExecutionResult(true, acc);
  }

  @Value
  public static class ExecutionResult {

    boolean loopFound;
    int acc;
  }

  @Data
  @AllArgsConstructor
  public static class Operation {

    private OperationType operationType;
    private int argument;
  }
}