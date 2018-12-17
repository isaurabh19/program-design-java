package encoder;

import java.util.Map;

/**
 * The interface for creating encoded messages from any sequence of string symbols into a fixed
 * sequence of coding symbols. Each distinct character in the message will be represented by a fixed
 * code made up of sequence of coding symbols.
 */
public interface Encoder {
  /**
   * Encodes a message into sequence of code symbols. Every unique character of message will have a
   * unique code. An empty coding symbol is not allowed. An empty string for encoding will return
   * back the same empty string.
   *
   * @param message       the message to be encoded.
   * @param codingSymbols the symbols in which the message will be encoded.
   * @return an encoded message string.
   */
  String encode(String message, String codingSymbols);

  /**
   * Returns a code dictionary where key is unique character from the message string and value is
   * the code generated for this key.
   *
   * @return a Map of Character: String representing message symbol and code respectively.
   */
  Map<Character, String> getCodeDictionary();
}
