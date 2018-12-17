import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import decoder.Decoder;
import decoder.DecoderImpl;
import encoder.Encoder;
import encoder.EncoderImpl;

import static org.junit.Assert.assertEquals;

public class EncoderImplTest {

  private Encoder encoder;
  private Decoder decoder;

  @Test
  public void testInputBinary() {
    encoder = new EncoderImpl();
    String encodedString = encoder.encode("badbeddaffec", "01");
    Map<Character, String> codeDictionary = encoder.getCodeDictionary();
    decoder = new DecoderImpl("01");
    for (Map.Entry<Character, String> entry : codeDictionary.entrySet()) {
      decoder.addCode(entry.getKey(), entry.getValue());
    }
    assertEquals("badbeddaffec", decoder.decode(encodedString));
  }

  @Test
  public void testInputHex() {
    encoder = new EncoderImpl();
    String encodedString = encoder.encode("This will be a nice paragraph in english." +
                    "lot of special char's such as $30.23 : thirty dollars / 30 dollars",
            "0123456789abcdef");
    Map<Character, String> codeDictionary = encoder.getCodeDictionary();
    decoder = new DecoderImpl("0123456789abcdef");
    for (Map.Entry<Character, String> entry : codeDictionary.entrySet()) {
      decoder.addCode(entry.getKey(), entry.getValue());
    }
    assertEquals("This will be a nice paragraph in english.lot of special char's such as " +
            "$30.23 : thirty dollars / 30 dollars", decoder.decode(encodedString));
  }

  @Test
  public void testGetCodingDictionary() {
    encoder = new EncoderImpl();
    String encodedString = encoder.encode("badbeddaffec",
            "01");
    Map<Character, String> expectedCodeTable = new HashMap<>();
    expectedCodeTable.put('a', "011");
    expectedCodeTable.put('b', "110");
    expectedCodeTable.put('c', "010");
    expectedCodeTable.put('d', "10");
    expectedCodeTable.put('e', "111");
    expectedCodeTable.put('f', "00");
    assertEquals(expectedCodeTable, encoder.getCodeDictionary());

  }

  @Test
  public void testCreateEncodingFile() {
    String message = "(Taken from wikipedia)\n" +
            "\n" +
            "Grace Brewster Murray Hopper (née Murray; December 9, 1906 – January 1, 1992) was an"
            +
            " American computer scientist and United States Navy rear admiral.[1] One of the " +
            "first" +
            " programmers of the Harvard Mark I computer, she was a pioneer of computer " +
            "programming who invented one of the first compiler related tools. She popularized " +
            "the" +
            " idea of machine-independent programming languages, which led to the development " +
            "of " +
            "COBOL, an early high-level programming language still in use today.\n" +
            "\n" +
            "Hopper attempted to enlist in the Navy during World War II but was rejected because "
            +
            "she was 34 years old. She instead joined the Navy Reserves. Hopper began her " +
            "computing " +
            "career in 1944 when she worked on the Harvard Mark I team led by Howard H. Aiken. " +
            "In " +
            "1949, she joined the Eckert–Mauchly Computer Corporation and was part of the team" +
            " that" +
            " developed the UNIVAC I computer. At Eckert–Mauchly she began developing the " +
            "compiler." +
            " She believed that a programming language based on English was possible. Her " +
            "compiler" +
            " converted English terms into machine code understood by computers. By 1952, Hopper "
            +
            "had finished her program linker (originally called a compiler), which was written " +
            "for" +
            " the A-0 System.";

    Encoder encoder = new EncoderImpl();
    String encodedMessage = encoder.encode(message, "01");
    System.out.println(encodedMessage);
    Decoder decoder = new DecoderImpl("01");
    Map<Character, String> codeDictionary = encoder.getCodeDictionary();
    for (Map.Entry<Character, String> entry : codeDictionary.entrySet()) {
      decoder.addCode(entry.getKey(), entry.getValue());
    }
    assertEquals(message, decoder.decode(encodedMessage));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyStringCodeSymbols() {
    encoder = new EncoderImpl();
    encoder.encode("asd", "");
  }

  @Test
  public void testEmptyMessage() {
    encoder = new EncoderImpl();
    assertEquals("", encoder.encode("", "01"));
  }
}