package decoder;

/**
 * This is a interface for decoding strings into their original messages. It provides methods to
 * create a coding tree by giving a code dictionary and also decodes a given encoded message. The
 * codes that are fed into the coding tree should be prefix codes. Coding symbols of any type such
 * as binary, hexadecimal and other types are allowed. However, empty or null values for any
 * parameter of any method can raise exceptions.
 */
public interface Decoder {

  /**
   * It adds a given code into the coding tree. It takes a pair of symbol and code as character and
   * string respectively. If a new symbol is passed for an existing code, the old code will be
   * replaced with the new code. If same symbol is passed with new code, the tree will contain both
   * the codes as different tree paths.
   *
   * @param symbol the symbol that would be represented by the code.
   * @param code   the code made up of sequence of encoding symbols.
   * @throws IllegalStateException if the code contains symbols other than the coding symbols.
   */
  void addCode(char symbol, String code) throws IllegalStateException;

  /**
   * Decodes the given encoded string back to the original message.
   *
   * @param encodedString the encoded string.
   * @return decoded output as string.
   * @throws IllegalStateException if decoding fails due to incomplete code or non existent code.
   */
  String decode(String encodedString) throws IllegalStateException;

  /**
   * Returns string representation of all codes that have been entered in the coding tree thus far.
   * The format of the codes is x:yyy, where x is the symbol and yyy is the code, with two codes
   * separated by new line.
   *
   * @return all codes in the tree as string.
   */
  String allCodes();

  /**
   * Returns whether the tree is a complete tree. Complete tree is defined has one where all nodes
   * have children equal to the number of distinct code symbols.
   *
   * @return boolean indicating if the tree is complete.
   */
  boolean isCodeComplete();
}
