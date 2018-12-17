package lookandsay;

import java.util.Iterator;

/**
 * This interface defines a generator of sequence. It provides both forward and
 * backward generation of sequence from current element.
 * @param <T> the type of data that makes the sequence.
 */
public interface RIterator<T> extends Iterator<T> {

  /**
   * Returns the previous element in the sequence of type T.
   * @return the type of data of the sequence.
   */
  T prev();

  /**
   * Returns whether the a previous element in the sequence from the current element can be
   * generated.
   * @return boolean indicating if going backwards is possible.
   */
  boolean hasPrevious();
}
