import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import decoder.Decoder;
import decoder.DecoderImpl;
import encoder.Encoder;
import encoder.EncoderImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DecoderImplTest {

  private Decoder uniDecoder;
  private Decoder binaryDecoder;
  private Decoder hexDecoder;
  private Decoder nDecoder;

  /**
   * Setup binary, hexadecimal and an arbitrary N decoder objects.
   */
  @Before
  public void setup() {
    binaryDecoder = new DecoderImpl("01");
    hexDecoder = new DecoderImpl("0123456789abcdef");
    uniDecoder = new DecoderImpl("1");
    nDecoder = new DecoderImpl("123abcxyz");

  }

  @Test
  public void testDuplicateCodingSymbols() {
    binaryDecoder.addCode('o', "1011");
    binaryDecoder.addCode('4', "1010");
    binaryDecoder.addCode('o', "11");
    assertEquals("4:1010\no:1011\no:11\n", binaryDecoder.allCodes());
    hexDecoder.addCode('o', "12bc3");
    hexDecoder.addCode('p', "123bb");
    hexDecoder.addCode('i', "aaaa");
    hexDecoder.addCode('o', "1a");
    hexDecoder.addCode('p', "1b");
    assertEquals("o:1a\no:12bc3\np:123bb\np:1b\ni:aaaa\n", hexDecoder.allCodes());
    nDecoder.addCode('1', "123");
    nDecoder.addCode('c', "xyz");
    nDecoder.addCode('c', "123");
    assertEquals("c:123\nc:xyz\n", nDecoder.allCodes());
  }


  @Test(expected = IllegalStateException.class)
  public void testAddCodeIllegalCodeSymbol() {
    binaryDecoder.addCode('a', "1012");
    hexDecoder.addCode('1', "12349");
    uniDecoder.addCode('0', "0");
    nDecoder.addCode('3', "4123");
  }

  @Test
  public void testFirstCodeAddCode() {
    uniDecoder.addCode('a', "1");
    assertEquals("a:1\n", uniDecoder.allCodes());
    binaryDecoder.addCode('a', "01");
    assertEquals("a:01\n", binaryDecoder.allCodes());
    hexDecoder.addCode('c', "ac21f8");
    assertEquals("c:ac21f8\n", hexDecoder.allCodes());
    nDecoder.addCode('2', "12bc");
    assertEquals("2:12bc\n", nDecoder.allCodes());
  }

  @Test
  public void testAddCodeSpecialCharacters() {
    uniDecoder.addCode('.', "1");
    assertEquals(".:1\n", uniDecoder.allCodes());
    binaryDecoder.addCode(' ', "10");
    binaryDecoder.addCode(',', "11");
    assertEquals(" :10\n,:11\n", binaryDecoder.allCodes());
    hexDecoder.addCode('#', "12bcf");
    hexDecoder.addCode('/', "0128a");
    hexDecoder.addCode('.', "2222");
    assertEquals("/:0128a\n#:12bcf\n.:2222\n", hexDecoder.allCodes());
  }

  @Test
  public void testAddCodeSameBranch() {
    //100 , 101
    // 123bcd 123bce
    //123bc1 123bc2
    binaryDecoder.addCode(' ', "10");
    binaryDecoder.addCode(':', "11");
    assertEquals(" :10\n::11\n", binaryDecoder.allCodes());
    hexDecoder.addCode('1', "8bce");
    hexDecoder.addCode('2', "8bc2");
    hexDecoder.addCode('3', "8bc3");
    hexDecoder.addCode('4', "8bc4");
    assertEquals("2:8bc2\n3:8bc3\n4:8bc4\n1:8bce\n", hexDecoder.allCodes());
    nDecoder.addCode('/', "12abc");
    nDecoder.addCode('?', "12ab1");
    nDecoder.addCode('\'', "12ab2");
    assertEquals("?:12ab1\n\':12ab2\n/:12abc\n", nDecoder.allCodes());
  }

  @Test
  public void testAddCodeNewBranch() {
    //100 101
    //abcd abdd
    //88f 8af
    binaryDecoder.addCode('[', "100");
    binaryDecoder.addCode('\\', "101");
    assertEquals("[:100\n\\:101\n", binaryDecoder.allCodes());
    nDecoder.addCode('a', "abc");
    nDecoder.addCode('b', "aab");
    assertEquals("b:aab\na:abc\n", nDecoder.allCodes());
    hexDecoder.addCode('f', "88ff");
    hexDecoder.addCode('a', "8aff");
    hexDecoder.addCode('c', "88af");
    hexDecoder.addCode('t', "88f1");
    hexDecoder.addCode('1', "18ff");
    assertEquals("1:18ff\na:8aff\nc:88af\nt:88f1\nf:88ff\n", hexDecoder.allCodes());
  }

  @Test
  public void testAddCodeSameCodeNewSymbol() {
    binaryDecoder.addCode('c', "101");
    binaryDecoder.addCode('d', "110");
    binaryDecoder.addCode('a', "101");
    assertEquals("a:101\nd:110\n", binaryDecoder.allCodes());
    uniDecoder.addCode('a', "1");
    uniDecoder.addCode('b', "1");
    assertEquals("b:1\n", uniDecoder.allCodes());
    hexDecoder.addCode('@', "8fc");
    hexDecoder.addCode('*', "6c");
    hexDecoder.addCode('^', "6c");
    hexDecoder.addCode('(', "8fc");
    assertEquals("^:6c\n(:8fc\n", hexDecoder.allCodes());
  }

  @Test
  public void testSingleElementCodeAddCode() {
    // a->0
    //0->a
    binaryDecoder.addCode('a', "1");
    assertEquals("a:1\n", binaryDecoder.allCodes());
    binaryDecoder.addCode('b', "0");
    assertEquals("b:0\na:1\n", binaryDecoder.allCodes());
    hexDecoder.addCode('c', "c");
    assertEquals("c:c\n", hexDecoder.allCodes());
    hexDecoder.addCode('a', "b");
    assertEquals("a:b\nc:c\n", hexDecoder.allCodes());
    hexDecoder.addCode('d', "d");
    assertEquals("a:b\nc:c\nd:d\n", hexDecoder.allCodes());
  }

  @Test
  public void testDecodeSingleElementCode() {
    //0
    uniDecoder = new DecoderImpl("0");
    uniDecoder.addCode('c', "0");
    assertEquals("c", uniDecoder.decode("0"));

    binaryDecoder = new DecoderImpl("10");
    binaryDecoder.addCode('a', "1");
    assertEquals("a", binaryDecoder.decode("1"));
    binaryDecoder.addCode('b', "0");
    assertEquals("b", binaryDecoder.decode("0"));
    hexDecoder.addCode('4', "f");
    assertEquals("4", hexDecoder.decode("f"));
  }

  @Test
  public void testDecodeBinary() {
    binaryDecoder.addCode('a', "000");
    binaryDecoder.addCode('b', "011");
    binaryDecoder.addCode('c', "11");
    binaryDecoder.addCode('d', "100");
    binaryDecoder.addCode('e', "1010");
    binaryDecoder.addCode('f', "1011");

    assertEquals("badbeddaffec", binaryDecoder.decode(
            "011000100011101010010000010111011101011"));
    //
  }

  @Test
  public void testDecodeHex() {
    Encoder encoder = new EncoderImpl();
    String encodedString = encoder.encode("This will be a nice paragraph in english." +
                    "lot of special char's such as $30.23 : thirty dollars / 30 dollars",
            "12345678abcdef");
    Map<Character, String> codeDictionary = encoder.getCodeDictionary();
    for (Map.Entry<Character, String> entry : codeDictionary.entrySet()) {
      hexDecoder.addCode(entry.getKey(), entry.getValue());
    }
    assertEquals("This will be a nice paragraph in english.lot of special char's such as" +
            " $30.23 : thirty dollars / 30 dollars", hexDecoder.decode(encodedString));

  }

  @Test
  public void testDecodeN() {
    Encoder encoder = new EncoderImpl();
    String encodedString = encoder.encode("This will be a nice paragraph in english." +
                    "lot of special char's such as $30.23 : thirty dollars / 30 dollars",
            "123abcxyz");
    Map<Character, String> codeDictionary = encoder.getCodeDictionary();
    for (Map.Entry<Character, String> entry : codeDictionary.entrySet()) {
      nDecoder.addCode(entry.getKey(), entry.getValue());
    }
    assertEquals("This will be a nice paragraph in english.lot of special char's such as" +
            " $30.23 : thirty dollars / 30 dollars", nDecoder.decode(encodedString));

  }

  @Test
  public void testDecodeUnary() {
    uniDecoder.addCode('c', "1");
    assertEquals("cccccccccc", uniDecoder.decode("1111111111"));

  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeIncompleteCodeBinary() {
    // reached a node (incomplete message)
    //code doesnt exist in the tree
    binaryDecoder.addCode('c', "011");
    binaryDecoder.addCode('x', "11");
    binaryDecoder.decode("010");

  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeIncompleteCodeHex() {
    hexDecoder.addCode(';', "12cc");
    hexDecoder.addCode('p', "1233");
    hexDecoder.addCode('k', "acdc");
    hexDecoder.decode("123");
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeIncompleteCodeN() {
    nDecoder.addCode('.', "xbc");
    nDecoder.addCode('[', "xyzc");
    nDecoder.addCode('i', "xyy1");
    nDecoder.addCode('i', "xyx1");
    nDecoder.addCode('i', "xyc1");
    nDecoder.addCode('i', "xyb1");
    nDecoder.decode("xyz");
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeCodeNotExist() {
    //tree -> 1011
    //message -> 1010 doesn't exist
    binaryDecoder.addCode('a', "11");
    binaryDecoder.addCode('3', "0101");
    binaryDecoder.decode("011");
    hexDecoder.addCode('0', "123c");
    hexDecoder.addCode('9', "123e");
    hexDecoder.decode("123d");
    nDecoder.addCode('8', "12xy");
    nDecoder.addCode('7', "12zy");
    nDecoder.decode("12yy");
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeEmptyCodeEmptyString() {
    //null as well
    binaryDecoder.addCode('1', "100");
    binaryDecoder.decode("");
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeEmptyCodeHex() {
    hexDecoder.addCode('9', "12ccd");
    hexDecoder.decode("");
  }

  @Test(expected = NullPointerException.class)
  public void testDecodeEmptyCodeHexNull() {
    hexDecoder.addCode('9', "12ccd");
    hexDecoder.decode(null);
  }

  @Test(expected = NullPointerException.class)
  public void testDecodeEmptyCodeNull() {
    binaryDecoder.addCode('1', "100");
    binaryDecoder.decode(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeOnEmptyTree() {
    binaryDecoder.decode("10");
    hexDecoder.decode("ff");
    nDecoder.decode("xybc");

  }

  // write test for illegal argument of decode()? e.g illegal char out of symbols?
  @Test
  public void testDecodeOnCompleteTreeHex() {
    hexDecoder.addCode('a', "0");
    hexDecoder.addCode('1', "1");
    hexDecoder.addCode('b', "2");
    hexDecoder.addCode('2', "3");
    hexDecoder.addCode('c', "4");
    hexDecoder.addCode('3', "5");
    hexDecoder.addCode('d', "6");
    hexDecoder.addCode('4', "7");
    hexDecoder.addCode('e', "8");
    hexDecoder.addCode('5', "9");
    hexDecoder.addCode('f', "a");
    hexDecoder.addCode('6', "b");
    hexDecoder.addCode('g', "c");
    hexDecoder.addCode('7', "d");
    hexDecoder.addCode('h', "e");
    hexDecoder.addCode('8', "f");
    assertEquals("a1b2c3d4e5f6g7h8", hexDecoder.decode("0123456789abcdef"));

  }

  @Test
  public void testAllCodesOnCompleteTree() {
    hexDecoder.addCode('a', "0");
    hexDecoder.addCode('b', "1");
    hexDecoder.addCode('c', "2");
    hexDecoder.addCode('d', "3");
    hexDecoder.addCode('e', "4");
    hexDecoder.addCode('f', "5");
    hexDecoder.addCode('g', "6");
    hexDecoder.addCode('h', "7");
    hexDecoder.addCode('i', "8");
    hexDecoder.addCode('j', "9");
    hexDecoder.addCode('k', "a");
    hexDecoder.addCode('l', "b");
    hexDecoder.addCode('m', "c");
    hexDecoder.addCode('n', "d");
    hexDecoder.addCode('o', "e");
    hexDecoder.addCode('p', "f");
    assertEquals("k:a\nl:b\nm:c\nn:d\no:e\np:f\na:0\nb:1\nc:2\nd:3\ne:4\nf:5\ng:6\nh:7\n" +
            "i:8\nj:9\n", hexDecoder.allCodes());
  }

  @Test
  public void testAllCodesOnCompleteTreeBinary() {
    binaryDecoder.addCode('a', "10");
    binaryDecoder.addCode('b', "01");
    binaryDecoder.addCode('2', "11");
    binaryDecoder.addCode('1', "00");
    assertEquals("1:00\nb:01\na:10\n2:11\n", binaryDecoder.allCodes());
  }

  @Test
  public void testDecodeOnCompleteTreeBinary() {
    binaryDecoder.addCode('a', "10");
    binaryDecoder.addCode('1', "00");
    binaryDecoder.addCode('b', "01");
    binaryDecoder.addCode('2', "11");
    assertEquals("2b1a", binaryDecoder.decode("11010010"));
  }

  @Test
  public void testDecodeOnCompleteTreeN() {
    nDecoder.addCode('a', "1");
    nDecoder.addCode('1', "2");
    nDecoder.addCode('b', "3");
    nDecoder.addCode('2', "a");
    nDecoder.addCode('a', "b");
    nDecoder.addCode('1', "c");
    nDecoder.addCode('b', "x");
    nDecoder.addCode('2', "y");
    nDecoder.addCode('2', "z");
    assertEquals("aa1b", nDecoder.decode("1123"));
  }

  @Test
  public void testIsCodeCompleteUnary() {
    uniDecoder.addCode('x', "1");
    assertTrue(uniDecoder.isCodeComplete());
  }

  @Test
  public void testIsCodeCompleteBinary() {
    binaryDecoder.addCode('a', "10");
    binaryDecoder.addCode('1', "00");
    binaryDecoder.addCode('b', "01");
    binaryDecoder.addCode('2', "11");
    assertTrue(binaryDecoder.isCodeComplete());
  }

  @Test
  public void testIsCodeCompleteHex() {
    hexDecoder.addCode('a', "0");
    hexDecoder.addCode('1', "1");
    hexDecoder.addCode('b', "2");
    hexDecoder.addCode('2', "3");
    hexDecoder.addCode('c', "4");
    hexDecoder.addCode('3', "5");
    hexDecoder.addCode('d', "6");
    hexDecoder.addCode('4', "7");
    hexDecoder.addCode('e', "8");
    hexDecoder.addCode('5', "9");
    hexDecoder.addCode('f', "a");
    hexDecoder.addCode('6', "b");
    hexDecoder.addCode('g', "c");
    hexDecoder.addCode('7', "d");
    hexDecoder.addCode('h', "e");
    hexDecoder.addCode('8', "f");
    assertTrue(hexDecoder.isCodeComplete());

  }

  @Test
  public void testIsCodeCompleteSingleChild() {
    binaryDecoder.addCode('a', "1");
    assertFalse(binaryDecoder.isCodeComplete());
    hexDecoder.addCode('a', "d");
    assertFalse(hexDecoder.isCodeComplete());
    nDecoder.addCode('x', "x");
    assertFalse(nDecoder.isCodeComplete());
  }

  @Test
  public void testIsCodeCompleteOneIncompleteBinary() {
    binaryDecoder.addCode('a', "10");
    binaryDecoder.addCode('b', "11");
    binaryDecoder.addCode('c', "00");
    assertFalse(binaryDecoder.isCodeComplete());
  }

  @Test
  public void testIsCodeCompleteOneIncompleteHex() {
    hexDecoder.addCode('a', "0");
    hexDecoder.addCode('b', "1");
    hexDecoder.addCode('c', "2");
    hexDecoder.addCode('d', "3");
    hexDecoder.addCode('b', "4");
    hexDecoder.addCode('c', "5");
    hexDecoder.addCode('d', "6");
    hexDecoder.addCode('b', "7");
    hexDecoder.addCode('c', "8");
    hexDecoder.addCode('d', "9");
    hexDecoder.addCode('b', "a");
    hexDecoder.addCode('c', "b");
    hexDecoder.addCode('d', "c");
    hexDecoder.addCode('b', "d");
    hexDecoder.addCode('c', "e");
    assertFalse(hexDecoder.isCodeComplete());
  }

  @Test
  public void testIsCodeCompleteOneIncompleteN() {
    nDecoder.addCode('a', "1");
    nDecoder.addCode('b', "2");
    nDecoder.addCode('c', "3");
    nDecoder.addCode('d', "a");
    nDecoder.addCode('b', "b");
    nDecoder.addCode('c', "c");
    nDecoder.addCode('d', "x");
    nDecoder.addCode('b', "y");
    assertFalse(nDecoder.isCodeComplete());
  }


  @Test
  public void testIsCodeCompleteLastLevel() {
    binaryDecoder.addCode('a', "000");
    binaryDecoder.addCode('b', "011");
    binaryDecoder.addCode('c', "11");
    binaryDecoder.addCode('d', "100");
    binaryDecoder.addCode('e', "1010");
    binaryDecoder.addCode('f', "1011");
    binaryDecoder.addCode('g', "001");
    assertFalse(binaryDecoder.isCodeComplete());
  }
}