import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lookandsay.LookAndSayIterator;
import lookandsay.RIterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LookAndSayIteratorTest {
  private RIterator<BigInteger> rIterator;
  private RIterator<BigInteger> rIterator1;
  private RIterator<BigInteger> rIterator2;

  /**
   * Sets up RIterator objects.
   */
  @Before
  public void setrIterator() {
    rIterator = new LookAndSayIterator();
    rIterator1 = new LookAndSayIterator(new BigInteger("7113"));
    rIterator2 = new LookAndSayIterator(BigInteger.TEN,
            new BigInteger("100998882982211211111111111111"));
  }

  @Test
  public void testDefaultIterator() {
    assertTrue(rIterator.hasNext());
    assertEquals("1", rIterator.next().toString());
    assertEquals("11", rIterator.next().toString());
    assertEquals("21", rIterator.prev().toString());
    List<BigInteger> bigIntegers = new ArrayList<>();
    int i = 10;
    while (i > 0) {
      bigIntegers.add(rIterator1.next());
      i--;
    }
    rIterator1.prev();
    for (int r = bigIntegers.size() - 1; r > 0; r--) {
      assertEquals(bigIntegers.get(r), rIterator1.prev());
    }
  }

  @Test
  public void testIterator1() {
    assertTrue(rIterator1.hasNext());
    assertEquals("7113", rIterator1.next().toString());
    assertEquals("172113", rIterator1.next().toString());
    assertEquals("1117122113", rIterator1.prev().toString());
    assertEquals("172113", rIterator1.prev().toString());
    assertEquals("7113", rIterator1.prev().toString());
    assertEquals("11111113", rIterator1.prev().toString());
    List<BigInteger> bigIntegers = new ArrayList<>();
    int i = 10;
    while (i > 0) {
      bigIntegers.add(rIterator1.next());
      i--;
    }
    rIterator1.prev();
    for (int r = bigIntegers.size() - 1; r > 0; r--) {
      assertEquals(bigIntegers.get(r), rIterator1.prev());
    }

  }

  @Test
  public void testIterator2() {
    List<BigInteger> bigIntegers = new ArrayList<>();
    while (rIterator2.hasNext()) {
      bigIntegers.add(rIterator2.next());
      //i--;
    }
    rIterator2.prev();
    for (int r = bigIntegers.size() - 1; r > 0; r--) {
      assertEquals(bigIntegers.get(r), rIterator2.prev());
    }


  }

  @Test
  public void testCountMoreThan9() {
    rIterator1 = new LookAndSayIterator(new BigInteger("1111111111111"));
    rIterator1.next();
    assertEquals("131", rIterator1.next().toString());
  }

  @Test
  public void testStartsWithZero() {
    rIterator1 = new LookAndSayIterator(new BigInteger("0988"));
    assertEquals("988", rIterator1.next().toString());
    assertEquals("1928", rIterator1.next().toString());
  }

  @Test
  public void testNextAfterLimitSameOutput() {
    rIterator2 = new LookAndSayIterator(BigInteger.ONE, new BigInteger("11"));
    assertEquals("1", rIterator2.next().toString());
    assertEquals("11", rIterator2.next().toString());
    assertEquals("21", rIterator2.next().toString());
    assertEquals("21", rIterator2.next().toString());
  }

  @Test
  public void testPrevAfterLimitSameOutput() {
    rIterator2 = new LookAndSayIterator(BigInteger.TEN, new BigInteger("1010"));
    assertEquals("10", rIterator2.prev().toString());
    assertEquals("0", rIterator2.prev().toString());
    rIterator2 = new LookAndSayIterator(new BigInteger("111111111111"),
            new BigInteger("19292929292929292929292"));
    assertEquals("111111111111", rIterator2.prev().toString());
    assertEquals("111111", rIterator2.prev().toString());
    assertEquals("111", rIterator2.prev().toString());
    assertEquals("111", rIterator2.prev().toString());
  }

  //ILAE cases
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSeed() {
    rIterator1 = new LookAndSayIterator(new BigInteger("-1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSeedNegative() {
    rIterator2 = new LookAndSayIterator(new BigInteger("-1"), BigInteger.TEN);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSeedBiggerthanEnd() {
    rIterator2 = new LookAndSayIterator(new BigInteger("100000"), BigInteger.TEN);
  }
}