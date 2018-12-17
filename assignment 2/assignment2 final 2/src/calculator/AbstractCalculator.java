package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract calculator class that includes common operations done by various calculators. It also
 * includes various helper methods that can be used by other calculators to avoid repeated
 * implementation of those features.
 */
abstract class AbstractCalculator implements Calculator {
  protected String currentString;
  protected boolean equalsSeen;


  /**
   * Returns the second operand of a valid arithmetic string.
   *
   * @param match Matcher object that complies a regex pattern to check.
   * @return Integer operand if found else returns default 0.
   */
  abstract int getLastOperand(Matcher match);

  /**
   * Resets the calculator back to it's default state. Usually operates on input 'C'.
   *
   * @return A new calculator instance with default settings for display.
   */
  abstract Calculator resetCalculator();

  /**
   * Checks if the resulting sequence on adding the new input will result in a valid sequence as
   * defined for the calculator.
   *
   * @param sequence The current input of the calculator so far.
   * @param newInput the new character that will be added to the sequence.
   * @return a boolean denoting if the addition of new input will result in valid sequence or no.
   */
  abstract boolean isValidSequence(String sequence, char newInput);

  /**
   * Filters the string in order to ensure correct arithmetic operations can be performed.
   *
   * @param sequence Current input sequence of the calculator.
   * @param newInput New input given to the calculator.
   * @return Filtered sequence of the calculator as String.
   */
  abstract String filterInputs(String sequence, char newInput);

  /**
   * Performs the arithmetic operations on the string by successfully parsing the operands.
   *
   * @param sequence Current input sequence of the calculator.
   * @param newInput New input given to the calculator.
   * @return String representation of calculated value upto new input of the sequence.
   */
  abstract String calculate(String sequence, char newInput);

  /**
   * A factory method that returns the instance of called type of calculator.
   *
   * @param sequence Result sequence of calculator so far.
   * @return A new instance of called calculator.
   */
  abstract Calculator getCalculatorInstance(String sequence);

  /**
   * Checks if the passed input is of operator type.
   *
   * @param input character that has to be checked.
   * @return a boolean that tells if input was operator or no.
   */
  protected boolean isOperator(char input) {
    return input == '+' || input == '-' || input == '*';
  }

  /**
   * Performs a regex group capture to detect operands and operators in the sequence.
   *
   * @param sequence current input sequence of the calculator.
   * @return Matcher object with matched groups.
   */
  protected Matcher getMatches(String sequence) {
    String regex = "(\\-?\\d+)([+\\-*])*(\\d+)*";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(sequence);
    matcher.find();
    return matcher;
  }

  /**
   * parses an integer operand from the string.
   *
   * @param operandString String representation of integer operand.
   * @return Parsed value of operand as int.
   */
  protected int getOperand(String operandString) {
    int operand = 0;
    try {
      operand = Integer.parseInt(operandString);
    } catch (NumberFormatException e) {
      throw new RuntimeException("Operand Overflow");
    }
    return operand;
  }

  /**
   * Evaluates the expression to pass to arithmetic calculations by adding missing operators or
   * operands if applicable. Capturing operators and operands by regex matching.
   *
   * @param newSequence Current input sequence of the calculator.
   * @return Evaluated string that represents the string after any arithmetic operations if they
   * were applicable.
   * @throws RuntimeException if the result of arithmetic operation causes an Integer overflow.
   */
  protected String evaluateExpression(String newSequence) throws RuntimeException {
    Matcher match = getMatches(newSequence);
    int operand1 = getOperand(match.group(1));
    int operand2 = 0;
    String operator;
    operand2 = this.getLastOperand(match);
    if (match.group(2) != null) {
      operator = match.group(2);
      String result = solve(operand1, operand2, operator);
      return result;
    }
    return newSequence;
  }

  /**
   * Checks the operator type and then performs the corresponding arithmetic.
   *
   * @param operand1 first operand.
   * @param operand2 second operator.
   * @param operator operator as either +,-,*.
   */
  protected String solve(int operand1, int operand2, String operator) {
    String resultString = "";
    switch (operator) {
      case "+": {
        if (operand1 != 0 && operand2 != 0 && (operand1 + operand2) < 0) {
          operand1 = 0;
          operand2 = 0;
        }
        resultString = operand1 + operand2 + "";
        break;
      }
      case "-":
        if (operand1 < 0 && (operand1 - operand2) > 0) {
          operand1 = 0;
          operand2 = 0;
        }
        resultString = operand1 - operand2 + "";
        break;
      case "*": {
        if (operand1 != 0 && operand2 > (Integer.MAX_VALUE / Math.abs(operand1))) {
          operand1 = 0;
          operand2 = 0;
        }
        resultString = operand1 * operand2 + "";
        break;
      }
      default:
        resultString = "";
        break;
    }
    return resultString;
  }

  /**
   * Checks if the resulting input sequence can cause operand to overflow.
   *
   * @param sequence Current input sequence of the calculator.
   * @param newInput new input given to the calculator.
   */
  protected void checkOperandOverflow(String sequence, char newInput) {

    Matcher m = getMatches(sequence + newInput);
    try {
      int operand1 = Integer.parseInt(m.group(1));
      if (m.group(3) != null) {
        int operand2 = Integer.parseInt(m.group(3));
      }
    } catch (NumberFormatException e) {
      throw new RuntimeException("Operand Overflow");
    }

  }


  @Override
  public Calculator input(char newInput) throws IllegalArgumentException, RuntimeException {
    if (newInput == 'C') {
      return this.resetCalculator();
    }
    if (!isValidSequence(this.currentString, newInput)) {
      throw new IllegalArgumentException("Invalid Input Sequence");
    }
    String newSequence = filterInputs(this.currentString, newInput);
    if (isOperator(newInput) || newInput == '=') {
      newSequence = calculate(newSequence, newInput);
    }
    return this.getCalculatorInstance(newSequence);
  }

  @Override
  public String getResult() {
    if (!this.currentString.isEmpty()) {
      StringBuilder filteredSequence = new StringBuilder();
      Matcher matcher = getMatches(this.currentString);
      if (matcher.group(1) != null) {
        int op1 = getOperand(matcher.group(1));
        filteredSequence.append(op1);
        if (matcher.group(2) != null) {
          String opr = matcher.group(2);
          filteredSequence.append(opr);
          if (matcher.group(3) != null) {
            int op2 = getOperand(matcher.group(3));
            filteredSequence.append(op2);
          }
        }
      }
      return filteredSequence.toString();
    }
    return this.currentString;
  }
}

