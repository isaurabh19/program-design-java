package calculator;

import java.util.regex.Matcher;

/**
 * A SimpleCalculator implementation of Calculator. It extends AbstractCalculator. This type of
 * calculator takes straightforward inputs that follow the sequence "Operand -> Operator -> Operand
 * ->'='(Optional)" and combinations of such sequences. It throws an error if any sequence violates
 * this order. It cannot process inputs greater than 32 bits and works only with positive numbers
 * but can handle negative results.
 */
public class SimpleCalculator extends AbstractCalculator implements Calculator {

  public SimpleCalculator() {
    this.currentString = "";
    this.equalsSeen = false;
  }

  /**
   * Creates a new instance of simple calculator with it's instance variables set to the passed
   * arguments.
   *
   * @param currentString the new input sequence that calculator will begin with.
   * @param equalsSeen    flag that denotes if equals was seen before the creation of this object.
   */
  private SimpleCalculator(String currentString, boolean equalsSeen) {
    this.currentString = currentString;
    this.equalsSeen = equalsSeen;
  }

  protected boolean isValidSequence(String sequence, char newChar) {
    if (sequence.isEmpty()) {
      return Character.isDigit(newChar);
    } else if (!isOperator(newChar) && (!Character.isDigit(newChar)) && (newChar != '=')) {
      return false;
    } else if (Character.isDigit(newChar)) {
      if (this.equalsSeen) {
        return false;
      } else {
        checkOperandOverflow(sequence, newChar);
      }
    }
    char lastChar = sequence.charAt(sequence.length() - 1);
    return !isOperator(lastChar) || Character.isDigit(newChar);
  }

  protected int getLastOperand(Matcher match) {
    int operand2 = 0;
    if (match.group(3) != null) {
      operand2 = getOperand(match.group(3));
    }
    return operand2;
  }

  protected String filterInputs(String sequence, char newInput) {
    if (Character.isDigit(newInput)) {
      return sequence + newInput;
    }
    return sequence;
  }

  protected String calculate(String newSequence, char newInput) {
    newSequence = evaluateExpression(newSequence);
    if (newInput != '=') {
      this.equalsSeen = false;
      newSequence = newSequence + newInput;
    } else {
      this.equalsSeen = true;
    }
    return newSequence;
  }


  protected Calculator getCalculatorInstance(String sequence) {
    return new SimpleCalculator(sequence, this.equalsSeen);
  }

  protected Calculator resetCalculator() {
    return new SimpleCalculator("", false);
  }

}


