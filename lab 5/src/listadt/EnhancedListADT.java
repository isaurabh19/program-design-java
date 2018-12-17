package listadt;

/**
 * An interface that adds enhanced operation of converting the mutable ListADT to it's immutable
 * version. This will be the type of list to be used if a mutable list is required.
 */
public interface EnhancedListADT<T> extends ListADT<T> {

  /**
   * Returns a new immutable version of itself with identical structure and same data.
   *
   * @param <T> the type of data the resulting list will hold.
   * @return an immutable list of identical structure and same data.
   */
  <T> ImmutableListADT<T> getImmutableVersion();
}
