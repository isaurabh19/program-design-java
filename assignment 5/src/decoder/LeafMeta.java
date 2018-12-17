package decoder;

/**
 * A class that encapsulates the information about the leaf in terms of the data and it's depth from
 * the root. It provides methods to access these information fields.
 */
public class LeafMeta {
  private final int index;
  private final String leafChar;

  /**
   * Constructs a new LeafMeta object with the data as leaf character and depth as the number of
   * nodes traversed from root to this leaf.
   *
   * @param leafChar the character at the leaf node.
   * @param index    the depth of the leaf node from root.
   */
  public LeafMeta(String leafChar, int index) {
    this.index = index;
    this.leafChar = leafChar;
  }

  /**
   * Returns the depth of this leaf from root.
   *
   * @return depth of the leaf as integer.
   */
  public int getIndex() {
    return this.index;
  }

  /**
   * Returns the symbol stored at the leaf.
   *
   * @return the symbol at leaf as string.
   */
  public String getLeafChar() {
    return this.leafChar;
  }
}
