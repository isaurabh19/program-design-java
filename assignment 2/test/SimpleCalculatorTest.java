import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;
import calculator.SimpleCalculator;

import static org.junit.Assert.assertEquals;

public class SimpleCalculatorTest {

  Calculator cal;

  @Before
  public void setup() {
    cal = new SimpleCalculator();
  }

  @Test
  public void testCorrectSequence() {
    assertEquals(cal.input('2').input('4').input('3').input('-').input('1').input('2').input('=')
                    .getResult(),
            "231");
    assertEquals(cal.input('2').input('+').input('5').input('=').getResult(), "7");
    assertEquals(cal.input('4').input('*').input('5').input('=').getResult(), "20");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectSequence() {
    cal.input('+').input('2').input('*').input('8').input('=');
    cal.input('2').input('3').input('+').input('=');
    cal.input('2').input('3').input('-').input('=');
    cal.input('2').input('3').input('*').input('=');
    // cal.input('2').input('3').input('+').input('2').input('2');
    cal.input('2').input('=').input('3').input('=');
    cal.input('2').input('+').input('-').input('3').input('=');
    cal.input('=').input('2').input('9').input('+').input('1').input('2');
  }

  @Test
  public void testCurrentStatus() {
    assertEquals(cal.input('2').getResult(), "2");
    assertEquals(cal.input('2').input('4').getResult(), "24");
    assertEquals(cal.input('2').input('4').input('-').getResult(), "24-");
    assertEquals(cal.input('2').input('4').input('-').input('1').getResult(), "24-1");
    assertEquals(cal.input('2').input('4').input('-').input('1').input('0').getResult(),
            "24-10");
    assertEquals(cal.input('2').input('4').input('-').input('1').input('0').input('+')
                    .getResult(),
            "14+");
    assertEquals(cal.input('2').input('4').input('-').input('1').input('0').input('+').input('2')
            .getResult(), "14+2");
    assertEquals(cal.input('2').input('4').input('-').input('1').input('0').input('+').input('2')
            .input('=').getResult(), "16");
    assertEquals(cal.input('2').input('+').input('5').input('=').input('-').input('3').input('=')
            .getResult(), "4");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperandsAfterEquals() {
    cal.input('2').input('+').input('3').input('=').input('2');
  }

  @Test
  public void testMultipleEquals() {
    assertEquals(cal.input('2').input('+').input('3').input('=').input('=').input('=')
            .getResult(), "5");
    assertEquals(cal.input('9').input('+').input('8').input('=').input('=').getResult(),
            "17");
    assertEquals(cal.input('9').input('-').input('8').input('=').input('=').getResult(),
            "1");
    assertEquals(cal.input('9').input('*').input('8').input('=').input('=').getResult(),
            "72");
  }

  @Test
  public void testClearInput() {
    assertEquals(cal.input('2').input('3').input('-').input('C').getResult(), "");
  }

  @Test
  public void testNegativeResult() {
    assertEquals(cal.input('3').input('-').input('7').input('=').getResult(), "-4");
  }

  @Test
  public void testAddOverflow() {
    assertEquals(cal.input('2').input('1').input('4').input('7').input('4').input('8').input('3')
            .input('6').input('4').input('7').input('+').input('2').input('3').input('-')
            .input('5').input('=').getResult(), "-5");
    assertEquals(cal.input('0').input('-').input('2').input('1').input('4').input('7').input('4')
            .input('8').input('3').input('6')
            .input('4').input('7').input('-').input('2').input('3').input('-').input('5')
            .input('=').getResult(), "-5");
  }

  @Test(expected = RuntimeException.class)
  public void testOperandOverflow() {
    cal.input('2').input('1').input('4').input('7').input('4').input('8').input('3').input('6')
            .input('4').input('7').input('9').input('9').input('+').input('2').getResult();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCharacters() {
    cal.input('8').input('/').input('2').input('=');
    cal.input('i').input('+').input('l');

  }

  @Test
  public void testPrecedingZeros() {
    assertEquals(cal.input('0').input('+').input('0').getResult(), "0+0");
    assertEquals(cal.input('0').input('9').getResult(), "9");
    assertEquals(cal.input('9').input('+').input('0').input('8').input('0').input('=').getResult()
            , "89");
    assertEquals(cal.input('0').input('+').input('0').input('=').getResult(), "0");
  }

  @Test
  public void testClearInputOnSequence() {
    assertEquals(cal.input('2').input('3').input('+').input('7').input('C').input('2').input('+')
            .input('4').getResult(), "2+4");
    assertEquals(cal.input('2').input('C').input('5').getResult(), "5");
    assertEquals(cal.input('3').input('+').input('4').input('=').input('C').input('4').getResult(),
            "4");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testClearInputOperatorInvalidSequence() {
    cal.input('9').input('+').input('1').input('=').input('C').input('+').input('4');
  }

  @Test
  public void testAddSubtractMultiply() {
    assertEquals(cal.input('4').input('+').input('2').input('-').input('0').input('9').input('*')
            .input('0').input('8').input('=').getResult(), "-24");
  }

  @Test
  public void testSubtractAddMultiply() {
    assertEquals(cal.input('4').input('-').input('2').input('+').input('0').input('9').input('*')
            .input('0').input('8').input('=').getResult(), "88");
  }

  @Test
  public void testAddMultiplySubtract() {
    assertEquals(cal.input('4').input('+').input('2').input('*').input('0').input('9').input('-')
            .input('0').input('8').input('=').getResult(), "46");
  }

  @Test
  public void testSubtractMultiplyAdd() {
    assertEquals(cal.input('4').input('-').input('2').input('*').input('0').input('9').input('+')
            .input('0').input('8').input('=').getResult(), "26");
  }

  @Test
  public void testMultiplySubtractAdd() {
    assertEquals(cal.input('4').input('*').input('2').input('-').input('0').input('9').input('+')
            .input('0').input('8').input('=').getResult(), "7");
  }

  @Test
  public void testMultiplyAddSubtract() {
    assertEquals(cal.input('4').input('*').input('2').input('+').input('0').input('9').input('-')
            .input('0').input('8').getResult(), "17-8");
  }

  @Test
  public void testNegativeResultAfterOperation() {
    assertEquals(cal.input('3').input('-').input('6').input('*').input('4').input('=').getResult(),
            "-12");
    assertEquals(cal.input('3').input('-').input('9').input('+').input('1').input('9').input('=')
            .getResult(), "13");
  }

  @Test
  public void testAllSubtract() {
    assertEquals(cal.input('9').input('-').input('1').input('9').input('-').input('2').input('0')
            .input('=').getResult(), 9 - 19 - 20 + "");
  }

  @Test
  public void testAllMultiply() {
    assertEquals(cal.input('9').input('*').input('1').input('9').input('*').input('2').input('0')
            .input('=').getResult(), 9 * 19 * 20 + "");
  }

  @Test
  public void testAllAdd() {
    assertEquals(cal.input('9').input('+').input('1').input('9').input('+').input('2').input('0')
            .input('=').getResult(), 9 + 19 + 20 + "");
  }

  @Test
  public void testOperatorAfterEquals() {
    assertEquals(cal.input('2').input('+').input('3').input('=').input('+').input('4')
            .input('=').getResult(), 2 + 3 + 4 + "");
  }

  @Test
  public void testAllZeros() {
    assertEquals(cal.input('0').input('+').input('0').input('0').input('=').getResult(),
            "0");
    assertEquals(cal.input('0').input('*').input('0').input('0').input('=').getResult(),
            "0");
    assertEquals(cal.input('0').input('-').input('0').input('0').input('=').getResult(),
            "0");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeInput() {
    cal.input('-').input('9').input('+').input('8');
  }
}