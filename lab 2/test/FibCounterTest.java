import org.junit.Before;
import org.junit.Test;

import fib.FibCounter;
import fib.FibonacciCounter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FibCounterTest {
  private FibonacciCounter f1;
  private FibonacciCounter f2;
  private FibonacciCounter f3;

  /**
   * Creates fib counter object for testing.
   */
  @Before
  public void setUp() {
    f1 = new FibCounter();
    f2 = new FibCounter();
    f3 = new FibCounter();
  }

  @Test
  public void testGetCount() {
    assertEquals(f1.getCount(), 1);
    assertEquals(f1.incrementCount().incrementCount().incrementCount().getCount(), 4);
    assertEquals(f1.incrementCount().getCount(), 3);
    assertEquals(f1.decrementCount().getCount(), 2);
  }

  @Test
  public void testGetFibNumber() {
    assertEquals(new FibCounter().getFibNumber(), 0);
    assertEquals(f3.incrementCount().getFibNumber(), 1);
    assertEquals(f2.incrementCount().incrementCount().getFibNumber(), 1);
    assertEquals(f2.incrementCount().incrementCount().incrementCount().getFibNumber(), 3);
    assertEquals(f2.incrementCount().incrementCount().incrementCount().incrementCount()
            .getFibNumber(), 8);
  }

  @Test
  public void testIncrementCount() {
    assertEquals(f1.incrementCount().incrementCount().getCount(), 3);
  }

  @Test
  public void testDecrementCount() {
    assertEquals(f1.incrementCount().incrementCount().decrementCount().getCount(), 2);
    assertEquals(f1.decrementCount().getCount(), 1);
  }

  @Test
  public void testDecrementNotAllowed() {
    try {
      assertEquals(f1.decrementCount().getCount(), 0);
      fail("This test should have failed");
    } catch (RuntimeException e) {
      //the test passed
    }
  }
}