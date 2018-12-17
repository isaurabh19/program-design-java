package calculator;

import java.util.regex.Matcher;

/**
 * A SmartCalculator implementation of calculator is backwards compatible with any simple
 * calculator. It extends AbstractCalculator. However, this calculator provides additional
 * capabilities of handling 'smart' inputs described below: -> It infers a missing second operand
 * and takes first operand as second. -> On multiple '=' inputs, it infers previous operator and
 * second operand and continues the arithmetic. -> It ignores operator at the start and continues
 * ahead. It too works on inputs lesser than 32 bits and works with positive numbers.
 */
public class SmartCalculator extends AbstractCalculator implements Calculator {
  private int lastOperand;
  private String lastOperator;

  public SmartCalculator() {
    this.currentString = "";
  }

  /**
   * Creates a smart calculator object with instance variables set to the passed arguments.
   *
   * @param newSequence  new calculator sequence it will begin with.
   * @param equalsSeen   flag that denotes if equals operator preceded object creation.
   * @param lastOperand  the last operand known before creation of this object.
   * @param lastOperator the last operator known before creation of this object.
   */
  private SmartCalculator(String newSequence, boolean equalsSeen, int lastOperand,
                          String lastOperator) {
    this.currentString = newSequence;
    this.equalsSeen = equalsSeen;
    this.lastOperand = lastOperand;
    this.lastOperator = lastOperator;
  }

  protected int getLastOperand(Matcher match) {
    int operand2 = 0;
    if (match.group(3) != null) {
      operand2 = getOperand(match.group(3));
    } else if (equalsSeen) {
      operand2 = this.getOperand(match.group(1));
    }
    this.lastOperand = operand2;
    return operand2;
  }

  protected String filterInputs(String sequence, char newInput) {
    if (sequence.isEmpty() && !Character.isDigit(newInput)) {
      return sequence;
    }
    if (Character.isDigit(newInput)) {
      return sequence + newInput;
    }
    char lastChar = sequence.charAt(sequence.length() - 1);
    if (isOperator(newInput)) {
      this.lastOperator = newInput + "";
      this.equalsSeen = false;
      if (isOperator(lastChar)) {
        sequence = sequence.substring(0, sequence.length() - 1);
      }
    }
    return sequence;
  }

  protected boolean isValidSequence(String sequence, char newInput) {
    if (!isOperator(newInput) && (!Character.isDigit(newInput)) && (newInput != '=')) {
      return false;
    }
    if (!sequence.isEmpty()) {
      checkOperandOverflow(sequence, newInput);
    }
    if (this.equalsSeen) {
      return !Character.isDigit(newInput);
    }
    return true;
  }

  protected String calculate(String newSequence, char newInput) {
    if (!newSequence.isEmpty()) {
      if (this.equalsSeen) {
        newSequence = newSequence + this.lastOperator + this.lastOperand + "";
      }
      if (newInput == '=') {
        this.equalsSeen = true;//can do this in evaluate() as well
      }
      newSequence = evaluateExpression(newSequence);
      if (newInput != '=') {
        newSequence = newSequence + newInput;
      }
    }
    return newSequence;
  }

  protected Calculator getCalculatorInstance(String sequence) {
    return new SmartCalculator(sequence, this.equalsSeen, this.lastOperand, this.lastOperator);
  }

  protected Calculator resetCalculator() {
    return new SmartCalculator("", false, 0, "");
  }

}
