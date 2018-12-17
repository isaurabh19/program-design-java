import org.junit.Before;
import org.junit.Test;

import listadt.EnhancedListADT;
import listadt.EnhancedListADTImpl;
import listadt.ImmutableListADT;
import listadt.ListADT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EnhancedListADTImplTest {
  private EnhancedListADT<Integer> enhancedListADT;

  /**
   * Sets up mutable list.
   */
  @Before
  public void setEnhancedListADT() {
    enhancedListADT = new EnhancedListADTImpl();
    enhancedListADT.addFront(1);
    enhancedListADT.addFront(2);
    enhancedListADT.addFront(3);

  }

  @Test
  public void testAddWorks() {
    enhancedListADT.add(0, 1);
    assertEquals(new Integer(1), enhancedListADT.get(0));
  }

  @Test
  public void testAddFrontBackWorks() {
    enhancedListADT.addBack(2);
    assertEquals(new Integer(2), enhancedListADT.get(enhancedListADT.getSize() - 1));
    enhancedListADT.addFront(3);
    assertEquals(new Integer(3), enhancedListADT.get(0));
  }

  @Test
  public void testMapWorks() {
    ListADT<Integer> mapped = enhancedListADT.map(i -> i * i);
    assertEquals(new Integer(9), mapped.get(0));
    assertEquals(new Integer(4), mapped.get(1));
    assertEquals(new Integer(1), mapped.get(2));
    assertEquals(3, mapped.getSize());
  }

  @Test
  public void testImmutableListDoesntMutate() {
    ImmutableListADT<Integer> immutableListADT = enhancedListADT.getImmutableVersion();
    enhancedListADT.addFront(9);
    assertEquals(new Integer(9), enhancedListADT.get(0));
    assertNotEquals(new Integer(9), immutableListADT.get(0));
  }

  @Test
  public void testImmutateMapWorks() {
    ImmutableListADT<Integer> immutableListADT = enhancedListADT.getImmutableVersion();
    ListADT<Integer> listADT = immutableListADT.map(i -> i + 10);
    assertEquals(new Integer(13), listADT.get(0));
    assertEquals(new Integer(12), listADT.get(1));
    assertEquals(new Integer(11), listADT.get(2));
    assertEquals(listADT.getSize(), enhancedListADT.getSize());
  }

  @Test
  public void testImmutateMutateNoChangeInMutate() {
    // list->immutable->mutable->modify->no change in immutable and original
    ImmutableListADT<Integer> immutableListADT = enhancedListADT.getImmutableVersion();
    EnhancedListADT<Integer> enhancedListADT2 = immutableListADT.getMutableVersion();
    enhancedListADT2.addFront(12);
    assertNotEquals(enhancedListADT2.get(0), enhancedListADT.get(0));
  }
}