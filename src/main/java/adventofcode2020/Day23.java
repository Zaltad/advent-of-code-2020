package adventofcode2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Day23 {

  @Data
  @AllArgsConstructor
  public static class Node {
    private int value;
    private Node next;
  }

  public long solve(String line) throws IOException {
    List<Integer> numbers = line.chars()
        .mapToObj(digit -> Character.digit(digit, 10))
        .collect(Collectors.toList());
    int n = 1000000;
    IntStream.range(10, n + 1).forEach(
        numbers::add);
    Map<Integer, Node> numberToNode = new HashMap<>();
    Node prevNode = new Node(numbers.get(0), null);
    numberToNode.put(numbers.get(0), prevNode);
    Node firstNode = prevNode;
    for (int i = 1; i < numbers.size(); i++) {
      Node node = new Node(numbers.get(i), null);
      numberToNode.put(numbers.get(i), node);
      prevNode.setNext(node);
      prevNode = node;
    }
    prevNode.setNext(firstNode);
    Node node = firstNode;
    for (int i = 0; i < 10000000; i++) {
      Node neigh = node.getNext();
      Set<Integer> values = new HashSet<>();
      for (int j = 0; j < 3; j++) {
        values.add(neigh.getValue());
        neigh = neigh.getNext();
      }
      Node firstNeighbor = node.getNext();
      Node lastNeighbor = firstNeighbor.getNext().getNext();
      node.setNext(lastNeighbor.getNext());
      int destValue = node.getValue() - 1 != 0 ? node.getValue() - 1 : n;
      while (values.contains(destValue)) {
        destValue = destValue - 1 != 0 ? destValue - 1 : n;
      }
      Node dest = numberToNode.get(destValue);
      Node destNeigh = dest.getNext();
      dest.setNext(firstNeighbor);
      lastNeighbor.setNext(destNeigh);
      node = node.getNext();
    }
    Node immediateNeighbor = numberToNode.get(1).getNext();
    return (long) immediateNeighbor.getValue() * immediateNeighbor.getNext().getValue();
  }
}