package decoder;

/**
 * This is an interface that implements a hierarchical tree structure for maintaining a coding tree
 * for decoding codes into their original symbols. It provides methods that can help in implementing
 * a decoder interface. A tree will consist of nodes who can contain other nodes or leaves.
 */
public interface CodingTree {

  /**
   * Adds the given character to end of the path which will be represented by the code symbol.
   *
   * @param character     the character that will be the leaf for the path.
   * @param symbol        string that will be represented as a path in the tree.
   * @param indexOfSymbol the index of the symbol to be looked at.
   * @return resulting CodingTree object.
   */
  CodingTree addCode(char character, String symbol, int indexOfSymbol);

  /**
   * Decodes the pattern from the start until a leaf is found and returns the data along with depth
   * information.
   *
   * @param encodedString the encoded message string
   * @param indexAt       index of the encoded message string to start with.
   * @return LeafMeta object containing the symbol found and the number of code symbols traversed.
   */
  LeafMeta decode(String encodedString, int indexAt);

  /**
   * Returns string representation of the tree from this point. The tree is traversed from the root
   * to the leaf and returned as 'leaf:path1path2path3..'.
   *
   * @param accumulator the string representation of the path so far.
   * @return the string representation of the whole path from root to leaf.
   */
  String allCodes(String accumulator);

  /**
   * Checks if the tree is complete from the current node.
   *
   * @param codingSymbolSize the value with which the children of any node have to be compared
   *                         with.
   * @return boolean indicating if the tree from this node is complete.
   */
  boolean isCodeComplete(int codingSymbolSize);
}
