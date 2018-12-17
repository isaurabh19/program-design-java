import org.junit.Before;
import org.junit.Test;

import listadt.EnhancedListADT;
import listadt.ImmutableListADT;
import listadt.ImmutableListADTImpl;
import listadt.ListADT;
import listadt.ListADTImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ImmutableListADTImplTest {

  private ImmutableListADT<Integer> immutableListADT;

  /**
   * Sets up immutable list.
   */
  @Before
  public void setImmutableListADT() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    listADT.addFront(9);
    listADT.addFront(11);
    listADT.addBack(10);
    immutableListADT = new ImmutableListADTImpl<>(listADT);
  }

  @Test
  public void testMap() {
    ListADT<Integer> listADT = immutableListADT.map(i -> i * i);
    assertEquals(listADT.getSize(), immutableListADT.getSize());
  }

  @Test
  public void testToMutable() {
    EnhancedListADT<Integer> enhancedListADT = immutableListADT.getMutableVersion();
    assertEquals(enhancedListADT.getSize(), immutableListADT.getSize());
    for (int i = 0; i < enhancedListADT.getSize(); i++) {
      assertEquals(enhancedListADT.get(i), immutableListADT.get(i));
    }

  }

  @Test
  public void testMutableChangeNotReflected() {
    EnhancedListADT<Integer> enhancedListADT = immutableListADT.getMutableVersion();
    enhancedListADT.addFront(99);
    enhancedListADT.addBack(100);
    assertNotEquals(new Integer(99), immutableListADT.get(0));
    enhancedListADT.remove(9);
    assertNotEquals(enhancedListADT.getSize(), immutableListADT.getSize());
  }
}