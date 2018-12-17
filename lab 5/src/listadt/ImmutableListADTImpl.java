package listadt;

import java.util.function.Function;

/**
 * This class implements the ImmutableListADT interface. It allows to create an initial ListADT via
 * its constructor. It implements all observer methods and can create a mutable copy of itself.
 *
 * @param <T> the type of data the list holds.
 */
public class ImmutableListADTImpl<T> implements ImmutableListADT<T> {
  private final ListADT<T> list;

  /**
   * Constructs an immutable list with initial list as the passed list. The immutable list created
   * from this passed list remains immutable thereafter even if the passed list mutates.
   *
   * @param mutableList the list with which the immutable will be initialized.
   */
  public ImmutableListADTImpl(ListADT<T> mutableList) {
    this.list = new ListADTImpl<>();
    for (int i = 0; i < mutableList.getSize(); i++) {
      list.add(i, mutableList.get(i));
    }
  }

  @Override
  public int getSize() {
    return this.list.getSize();
  }

  @Override
  public T get(int index) throws IllegalArgumentException {
    return this.list.get(index);
  }

  @Override
  public <R> ListADT<R> map(Function<T, R> converter) {
    return this.list.map(converter);
  }

  @Override
  public EnhancedListADT<T> getMutableVersion() {
    return ListADTFactory.getMutableList(this.list);
  }
}
