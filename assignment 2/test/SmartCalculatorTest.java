import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;
import calculator.SmartCalculator;

import static org.junit.Assert.assertEquals;

public class SmartCalculatorTest {

  private Calculator calculator;

  @Before
  public void setup() {
    calculator = new SmartCalculator();
  }

  @Test
  public void testBeginWithOperator() {
    assertEquals(calculator.input('+').input('9').input('-').input('8').getResult(), "9-8");
    assertEquals(calculator.input('-').input('-').input('8').input('+').input('9').getResult(),
            "8+9");
  }

  @Test
  public void testConsecutiveOperators() {
    assertEquals(calculator.input('2').input('3').input('+').input('-').input('4').getResult()
            , "23-4");
    assertEquals(calculator.input('3').input('3').input('+').input('4').input('*').input('-')
            .getResult(), "37-");
    assertEquals(calculator.input('2').input('+').input('*').input('3').input('=').getResult(),
            2 * 3 + "");
    assertEquals(calculator.input('2').input('*').input('+').input('3').input('=').getResult(),
            2 + 3 + "");
    assertEquals(calculator.input('2').input('-').input('+').input('3').input('=').getResult(),
            2 + 3 + "");
    assertEquals(calculator.input('2').input('-').input('*').input('3').input('=').getResult(),
            2 * 3 + "");
    assertEquals(calculator.input('2').input('*').input('-').input('3').input('=').getResult(),
            2 - 3 + "");
  }

  @Test
  public void testValidEqualsSequence() {
    assertEquals(calculator.input('2').input('+').input('3').input('=').input('=').getResult()
            , "8");
  }

  @Test
  public void testMissingOperand() {
    assertEquals(calculator.input('2').input('3').input('+').input('=').getResult(), "46");
    assertEquals(calculator.input('2').input('3').input('-').input('=').getResult(), "0");
    assertEquals(calculator.input('2').input('3').input('*').input('=').getResult(),
            23 * 23 + "");
  }

  @Test
  public void testValidOperatorSequence() {
    assertEquals(calculator.input('3').input('-').input('+').input('3').input('=').getResult()
            , "6");
  }

  @Test
  public void testValidOperatorEqualsSequence() {
    assertEquals(calculator.input('9').input('+').input('=').input('=').getResult(), "27");
  }

  @Test
  public void testValidOperatorAtStartSequence() {
    assertEquals(calculator.input('+').input('8').input('-').input('2').getResult(), "8-2");
    assertEquals(calculator.input('-').input('9').input('-').input('2').getResult(),
            "9-2");
    assertEquals(calculator.input('*').input('-').input('9').input('-').input('2').input('=')
            .getResult(), "7");
  }

  @Test
  public void testComplexSequence() {
    assertEquals(calculator.input('-').input('5').input('*').input('+').input('1').input('0')
                    .input('-').input('*').input('4').input('+').input('=').input('=').input('+')
                    .input('5').input('*').input('1').input('0').input('=').input('=').getResult()
            , "18500");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEqualsSequencec() {
    calculator.input('9').input('=').input('9');
  }

  @Test(expected = RuntimeException.class)
  public void testOperandOverflow() {
    calculator.input('2').input('1').input('4').input('7').input('4').input('8').input('3')
            .input('6').input('4').input('7').input('9').input('9').input('+').input('3');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddition() {
    //calculator.input('2').input('3').input('+').input('=').input('3');
    calculator.input('2').input('+').input('+').input('=').input('3');
  }

  @Test
  public void testPrecedingZero() {
    assertEquals(calculator.input('0').input('8').getResult(), "8");
    assertEquals(calculator.input('0').input('8').input('+').input('=').input('+').
            input('0').input('9').getResult(), "16+9");
  }

  @Test
  public void testSample() {
    assertEquals(calculator.input('3').input('-').input('6').input('*').input('2').input('=')
            .getResult(), "-6");
    assertEquals(calculator.input('*').input('3').input('2').input('*').input('=').input('-')
            .input('=').getResult(), "0");
  }

  @Test
  public void testAllZeros() {
    assertEquals(calculator.input('0').input('+').input('0').input('0').input('=').getResult(),
            "0");
    assertEquals(calculator.input('0').input('-').input('0').input('0').input('=').getResult(),
            "0");
    assertEquals(calculator.input('0').input('*').input('0').input('0').input('=').getResult(),
            "0");
  }

  @Test
  public void testMultipleEquals() {
    assertEquals(calculator.input('9').input('+').input('=').input('=').getResult(), "27");
    assertEquals(calculator.input('9').input('-').input('=').input('=').getResult(), "-9");
    assertEquals(calculator.input('9').input('*').input('=').input('=').getResult(), "729");
    assertEquals(calculator.input('9').input('+').input('8').input('=').input('=').getResult(),
            "25");
    assertEquals(calculator.input('9').input('-').input('8').input('=').input('=').getResult(),
            "-7");
    assertEquals(calculator.input('9').input('*').input('8').input('=').input('=').getResult(),
            "576");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCharacter() {
    calculator.input('9').input('-').input('o');
    calculator.input('9').input('+').input('/');
    calculator.input('9').input('-').input('%');
    calculator.input('9').input('x').input('9');
  }

}