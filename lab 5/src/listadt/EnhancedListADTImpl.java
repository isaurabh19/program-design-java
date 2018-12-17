package listadt;

/**
 * This class implements the EnhancedListADT interface. It also extends functions from ListADTImpl
 * and just implements getting an immutable version. The immutable version has the same structure
 * and data as of its mutable version. Map operation on the immutable list will maintain the
 * structure of the list. However, the data is not made immutable and would behave as per its
 * expected behavior if attempted to modify.
 *
 * @param <T> the type of data the list holds.
 */
public class EnhancedListADTImpl<T> extends ListADTImpl<T> implements EnhancedListADT<T> {

  /**
   * Constructs a new enhanced list capable to providing its immutable version.
   */
  public EnhancedListADTImpl() {
    super();
  }

  @Override
  public ImmutableListADT<T> getImmutableVersion() {
    return ListADTFactory.getImmutableList(this);
  }
}
