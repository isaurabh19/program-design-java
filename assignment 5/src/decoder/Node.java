package decoder;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents a node of a tree. The nodes of coding tree do not hold any data. The node
 * keeps a track of all it's children and end of the path of this node is a leaf. The path from node
 * to leaf is the code for the symbol at it's leaf.
 */
public class Node implements CodingTree {
  private Map<Character, CodingTree> children;

  /**
   * Constructs a new Node object with no children initially.
   */
  public Node() {
    children = new HashMap<>();
  }

  @Override
  public CodingTree addCode(char character, String symbol, int indexOfSymbol) {
    if (symbol.length() == indexOfSymbol + 1) {
      this.children.put(symbol.charAt(indexOfSymbol), new Leaf(character));
      return this;
    }
    if (this.children.containsKey(symbol.charAt(indexOfSymbol))) {
      this.children.get(symbol.charAt(indexOfSymbol)).addCode(character, symbol,
              indexOfSymbol + 1);
    } else {
      this.children.put(symbol.charAt(indexOfSymbol), new Node().addCode(character, symbol,
              indexOfSymbol + 1));
    }
    return this;
  }

  @Override
  public LeafMeta decode(String encodedString, int indexAt) throws IllegalStateException {
    try {
      if (this.children.containsKey(encodedString.charAt(indexAt))) {
        return this.children.get(encodedString.charAt(indexAt)).decode(encodedString,
                indexAt + 1);
      } else {
        throw new IllegalStateException("Encoded string is incorrect");
      }
    } catch (StringIndexOutOfBoundsException e) {
      throw new IllegalStateException("Code doesn't exist");
    }

  }

  @Override
  public String allCodes(String accumulator) {
    StringBuilder allCodesString = new StringBuilder();
    for (Map.Entry<Character, CodingTree> child : children.entrySet()) {
      allCodesString.append(child.getValue().allCodes(accumulator + child.getKey()
              .toString()));
    }
    return allCodesString.toString();
  }

  @Override
  public boolean isCodeComplete(int codingSymbolSize) {
    if (this.children.size() != codingSymbolSize) {
      return false;
    }
    boolean areChildrenPresent;
    for (Map.Entry<Character, CodingTree> child : children.entrySet()) {
      areChildrenPresent = child.getValue().isCodeComplete(codingSymbolSize);
      if (!areChildrenPresent) {
        return areChildrenPresent;
      }
    }
    return true;
  }
}
