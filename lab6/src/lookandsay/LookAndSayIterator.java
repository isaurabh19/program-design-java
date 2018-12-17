package lookandsay;

import java.math.BigInteger;

/**
 * This class implements the RIterator for a Look and Say sequence. Each element of the sequence is
 * of type BigInteger. The sequence can be generated either from a given seed till a given limit, or
 * by given seed and default limit or default limit and seed. The default seed is 1 and default end
 * limit is a number with 100 9's.
 */
public class LookAndSayIterator implements RIterator<BigInteger> {

  //private final BigInteger seed;
  private final BigInteger end;
  private BigInteger current;

  private void validateSeed(BigInteger seed, BigInteger end) {
    if (seed.compareTo(BigInteger.ZERO) < 0 || seed.compareTo(end) >= 0) {
      throw new IllegalArgumentException("Seed not valid");
    }
  }

  /**
   * Constructs an object with given seed and end limit.
   *
   * @param seed the number from which the sequence starts.
   * @param end  the number up to which sequence will be generated.
   * @throws IllegalArgumentException if seed is less than or equal to end or if seed is negative.
   */
  public LookAndSayIterator(BigInteger seed, BigInteger end) throws IllegalArgumentException {
    validateSeed(seed, end);
    this.current = seed;
    this.end = end;
  }

  /**
   * Constructs an object with the given seed and default limit.
   *
   * @param seed the number from which the sequence starts.
   * @throws IllegalArgumentException if seed is less than or equal to end or if seed is negative.
   */
  public LookAndSayIterator(BigInteger seed) throws IllegalArgumentException {
    StringBuilder number = new StringBuilder();
    for (int i = 0; i < 100; i++) {
      number.append("9");
    }
    validateSeed(seed, new BigInteger(number.toString()));
    this.end = new BigInteger(number.toString());
    this.current = seed;
  }

  /**
   * Constructs an object with default seed and limit.
   *
   * @throws IllegalArgumentException if seed is less than or equal to end or if seed is negative.
   */
  public LookAndSayIterator() throws IllegalArgumentException {
    this.current = BigInteger.ONE;
    StringBuilder number = new StringBuilder();
    for (int i = 0; i < 100; i++) {
      number.append("9");
    }
    this.end = new BigInteger(number.toString());
  }

  private BigInteger generatePrev(BigInteger current) {
    String currentNum = current.toString();
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < currentNum.length(); i += 2) {
      int count = Integer.parseInt(currentNum.charAt(i) + "");
      char second = currentNum.charAt(i + 1);
      StringBuilder number = new StringBuilder();
      for (int j = 0; j < count; j++) {
        number.append(second);
      }
      result.append(number);
    }
    return new BigInteger(result.toString());
  }

  @Override
  public BigInteger prev() {
    BigInteger result = current;
    if (hasPrevious()) {
      current = generatePrev(current);
    }
    return result;
  }

  @Override
  public boolean hasPrevious() {
    return this.current.toString().length() % 2 == 0;
  }

  @Override
  public boolean hasNext() {
    return this.current.compareTo(end) < 1;
  }

  private BigInteger generateNext(BigInteger current) {
    String currentNum = current.toString();
    StringBuilder result = new StringBuilder();
    int count = 1;
    char currentChar;
    for (int i = 1; i < currentNum.length(); i++) {
      if (currentNum.charAt(i) == currentNum.charAt(i - 1)) {
        //currentChar = currentNum.charAt(0);
        count++;
      } else {
        currentChar = currentNum.charAt(i - 1);
        result.append(count).append(currentChar);
        count = 1;
      }
    }
    result.append(count).append(currentNum.charAt(currentNum.length() - 1));
    return new BigInteger(result.toString());
  }

  @Override
  public BigInteger next() {
    BigInteger result = current;
    if (hasNext()) {
      current = generateNext(current);
    }
    return result;
  }
}