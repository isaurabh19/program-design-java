package listadt;

import java.util.function.Function;

/**
 * An interface that represents an immutable list and operations that can be performed on it. Being
 * immutable, it only offers observer methods. It allows to get a mutable version of itself.
 * However, any changes made in that mutable version aren't reflected in original immutable list.
 *
 * @param <T> the type of data the list holds.
 */
public interface ImmutableListADT<T> {
  /**
   * Return the number of objects currently in this list.
   *
   * @return the size of the list.
   */
  int getSize();

  /**
   * Get the (index)th object in this list.
   *
   * @param index the index of the object to be returned.
   * @return the object at the given index.
   * @throws IllegalArgumentException if an invalid index is passed.
   */
  T get(int index) throws IllegalArgumentException;

  /**
   * A general purpose map higher order function on this list, that returns the corresponding list
   * of type R.
   *
   * @param converter the function that converts T into R.
   * @param <R>       the type of data in the resulting list.
   * @return the resulting list that is identical in structure to this list, but has data of type R.
   */
  <R> ListADT<R> map(Function<T, R> converter);

  /**
   * Returns a new mutable version of this list. Changes in mutable version don't have effect on
   * this immutable list.
   *
   * @param <T> the type of data in resulting list.
   * @return a mutable list identical in structure to this list.
   */
  <T> EnhancedListADT<T> getMutableVersion();
}
