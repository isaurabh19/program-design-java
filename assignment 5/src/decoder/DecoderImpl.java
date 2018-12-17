package decoder;

import java.util.HashSet;
import java.util.Set;

/**
 * A class that implements the Decoder interface. It creates a decoder object and implements the
 * decoding operations as operations on tree. It needs to know the coding symbols before performing
 * any decoding operation. This decoder expects encoding to be done in prefix codes and hence can
 * add only prefix codes to its coding tree.
 */
public class DecoderImpl implements Decoder {
  private CodingTree root;
  private Set<Character> codingSymbol;

  /**
   * Constructs a decoder object that would only work on the given coding symbols. If coding symbols
   * contain duplicates then unique symbols are taken.
   *
   * @param codingSymbols the string of distinct coding symbols that occur in encoded message.
   */
  public DecoderImpl(String codingSymbols) {
    codingSymbol = new HashSet<>();
    for (int i = 0; i < codingSymbols.length(); i++) {
      codingSymbol.add(codingSymbols.charAt(i));
    }
    root = new Node();
  }

  @Override
  public void addCode(char characrterSymbol, String code) throws IllegalStateException {
    for (int i = 0; i < code.length(); i++) {
      if (!this.codingSymbol.contains(code.charAt(i))) {
        throw new IllegalStateException("Found symbol not in coding symbols");
      }
    }
    this.root = root.addCode(characrterSymbol, code, 0);
  }

  @Override
  public String decode(String encodedString) throws IllegalStateException {
    if (encodedString.isEmpty()) {
      throw new IllegalStateException("Empty or null string not allowed");
    }
    LeafMeta decodedData;
    StringBuilder decodedString = new StringBuilder();
    for (int i = 0; i < encodedString.length(); i++) {
      decodedData = this.root.decode(encodedString, i);
      decodedString.append(decodedData.getLeafChar());
      i = decodedData.getIndex();
    }
    return decodedString.toString();
  }

  @Override
  public String allCodes() {
    return this.root.allCodes("");
  }

  @Override
  public boolean isCodeComplete() {
    return this.root.isCodeComplete(this.codingSymbol.size());
  }
}
