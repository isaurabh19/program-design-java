package decoder;

/**
 * A class that represents a leaf of the coding tree. Leaves hold the data as the symbol character.
 * A leaf cannot have any children and marks the end of the path of the tree.
 */
public class Leaf implements CodingTree {

  private final char symbol;

  /**
   * Constructs a new Leaf object with the passed character as it's data.
   *
   * @param symbol the character that would be stored in the leaf as end of path.
   */
  public Leaf(char symbol) {
    this.symbol = symbol;
  }

  @Override
  public CodingTree addCode(char character, String symbol, int indexOfSymbol) {
    throw new IllegalStateException("Non Prefix code possibly passed");
  }

  @Override
  public LeafMeta decode(String encodedString, int indexAt) {
    return new LeafMeta(this.symbol + "", indexAt - 1);
  }

  @Override
  public String allCodes(String accumulator) {
    return this.symbol + ":" + accumulator + "\n";
  }

  @Override
  public boolean isCodeComplete(int codingSymbolSize) {
    return true;
  }
}
