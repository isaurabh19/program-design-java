package listadt;

/**
 * A factory class that creates instances of mutable and immutable types of ListADT. Currently,
 * supports two types of lists: EnhancedListADT (mutable) and ImmutableListADT (immutable).
 */
public class ListADTFactory {

  /**
   * Creates an instance of ImmutableListADT type.
   *
   * @param list the ListADT object to be converted to an immutable list.
   * @param <T>  the type of data the list holds.
   * @return an immutable list of type ImmutableListADT.
   */
  public static <T> ImmutableListADT<T> getImmutableList(ListADT list) {
    return new ImmutableListADTImpl<>(list);
  }

  /**
   * Creates an instance of EnhancedListADT type.
   *
   * @param list the immutable list that is to be converted to a mutable type.
   * @param <T>  the type of data the list holds.
   * @return a mutable list of type EnhancedListADT.
   */
  public static <T> EnhancedListADT<T> getMutableList(ListADT<T> list) {
    EnhancedListADT<T> mutableList = new EnhancedListADTImpl<>();
    for (int i = 0; i < list.getSize(); i++) {
      mutableList.add(i, list.get(i));
    }
    return mutableList;
  }
}
