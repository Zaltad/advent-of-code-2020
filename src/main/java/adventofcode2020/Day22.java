package adventofcode2020;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Value;

public class Day22 {

  public int solve(InputStream inputStream) throws IOException {
    List<List<String>> lineGroups = Utils.readLineGroups(inputStream);
    Deque<Integer> deck1 = lineGroups.get(0).stream().skip(1).map(Integer::parseInt)
        .collect(Collectors.toCollection(ArrayDeque::new));
    Deque<Integer> deck2 = lineGroups.get(1).stream().skip(1).map(Integer::parseInt)
        .collect(Collectors.toCollection(ArrayDeque::new));
    Deque<Integer> winner = recursiveGame(deck1, deck2).getDeck();
    int total = 0;
    int multiplier = 1;
    while (!winner.isEmpty()) {
      total += winner.pollLast() * multiplier;
      multiplier++;
    }
    return total;
  }

  private Winner recursiveGame(Deque<Integer> deck1, Deque<Integer> deck2) {
    Set<Deque<Integer>> player1Decks = new HashSet<>();
    Set<Deque<Integer>> player2Decks = new HashSet<>();
    while (!deck1.isEmpty() && !deck2.isEmpty()) {
      Deque<Integer> player1Hand = new LinkedList<>(deck1);
      Deque<Integer> player2Hand = new LinkedList<>(deck2);
      if (player1Decks.contains(player1Hand) || player2Decks.contains(player2Hand)) {
        return new Winner(1, player1Hand);
      }
      player1Decks.add(player1Hand);
      player2Decks.add(player2Hand);
      int deck1Card = deck1.pollFirst();
      int deck2Card = deck2.pollFirst();
      int winner;
      if (deck1Card <= deck1.size() && deck2Card <= deck2.size()) {
        winner = recursiveGame(
            deck1.stream().limit(deck1Card).collect(Collectors.toCollection(ArrayDeque::new)),
            deck2.stream().limit(deck2Card).collect(Collectors.toCollection(ArrayDeque::new)))
            .getPlayer();
      } else {
        winner = deck1Card < deck2Card ? 2 : 1;
      }
      if (winner == 2) {
        deck2.addLast(deck2Card);
        deck2.addLast(deck1Card);
      } else {
        deck1.addLast(deck1Card);
        deck1.addLast(deck2Card);
      }
    }
    if (deck1.isEmpty()) {
      return new Winner(2, deck2);
    }
    return new Winner(1, deck1);
  }

  @Value
  public static class Winner {

    int player;
    Deque<Integer> deck;
  }
}