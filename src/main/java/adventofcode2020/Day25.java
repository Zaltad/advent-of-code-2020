package adventofcode2020;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class Day25 {

  private static final int MOD = 20201227;

  public long solve(InputStream inputStream) throws FileNotFoundException {
    List<Integer> numbers = Utils.readNumbers(inputStream);
    long cardKey = numbers.get(0);
    long doorKey = numbers.get(1);
    return getEncryptionKey(cardKey, getLoopSize(doorKey, 7));
  }

  private int getLoopSize(long key, long subjectNumber) {
    long value = 1;
    int loopSize = 0;
    while (value != key) {
      value = (value * subjectNumber) % MOD;
      loopSize++;
    }
    return loopSize;
  }

  private long getEncryptionKey(long subjectNumber, int loopSize) {
    long value = 1;
    while (loopSize > 0) {
      value = (value * subjectNumber) % MOD;
      loopSize--;
    }
    return value;
  }
}