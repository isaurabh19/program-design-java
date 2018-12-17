package calculator;

/**
 * A calculator that works with whole numbers only and supports three arithmetic operations :
 * addition(+), subtraction(-) and multiplication(*). The interface simulates a real time calculator
 * like input i.e. it takes one character at a time as input and displays the current state of
 * output so far.
 */
public interface Calculator {

  /**
   * It allows to input operators operands and special inputs like 'C' and '=' one character at a
   * time. It checks for validity of the input sequence and proceeds ahead if valid, otherwise
   * throws exceptions. It clears the input on input 'C'. On '=' displays the arithmetic result up
   * to that point.
   *
   * @param input A single character literal representing the input for calculator.
   * @return A new Calculator instance with current result stored in it.
   * @throws IllegalArgumentException on illegal sequence of characters or illegal character
   *                                  itself.
   * @throws RuntimeException         if the operand overflows either on input or on calculation.
   */
  Calculator input(char input) throws IllegalArgumentException, RuntimeException;

  /**
   * It displays the result of the calculator at the current state as a String. Formats the output
   * if it doesn't resemble a presentable format.
   *
   * @return a String containing the input of the calculator so far (at the time of calling).
   */
  String getResult();
}