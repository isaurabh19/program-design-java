package polynomial;

/**
 * A class that represents a single polynomial term and encapsulates it's attributes such as
 * coefficient and power. It provides operations that operate on a single term.
 */
public class PolynomialTerm {
  private final int coefficient;
  private final int power;
  private final String variable = "x^";

  /**
   * Constructs a polynomial term object with its power and coefficient set to the passed values.
   *
   * @param coefficient coefficient of the term as integer.
   * @param power       power of the term as an integer.
   */
  public PolynomialTerm(int coefficient, int power) {
    this.coefficient = coefficient;
    this.power = power;
  }

  /**
   * Returns the coefficient of the called term.
   *
   * @return coefficient as an integer.
   */
  public int getCoefficient() {
    return this.coefficient;
  }

  /**
   * Returns the power of the called term.
   *
   * @return power of the term as integer.
   */
  public int getPower() {
    return this.power;
  }

  /**
   * String representation of the term using it's attributes. e.g 3x^2.
   *
   * @return string representing this term.
   */
  @Override
  public String toString() {
    String coefficientString = coefficient + "";
    String powerString = power + "";
    String variableString = variable;
    if (coefficient == 0) {
      coefficientString = "";
      powerString = "";
      variableString = "";
    } else if (coefficient > 0) {
      coefficientString = "+" + coefficient;
    }
    if (power <= 0) {
      powerString = "";
      variableString = "";
    }
    return coefficientString + variableString + powerString;
  }

  /**
   * Evaluates the term by assigning the passed value to the variable in the term.
   *
   * @param value the value for the variable of the term.
   * @return evaluated answer of the term as double.
   */
  public double evaluate(double value) {
    if (power == 0) {
      return coefficient;
    }
    return coefficient * Math.pow(value, power);
  }

  /**
   * Calculates the derivative of this term as per the rules of calculus.
   *
   * @return a new term which is the derivative of the called term.
   */
  public PolynomialTerm derivative() {
    int newPower = power;
    int newCoefficient = coefficient;
    if (power > 0) {
      newCoefficient *= newPower;
      newPower -= 1;
    }
    if (power == 0) {
      newCoefficient = 0;
    }
    return new PolynomialTerm(newCoefficient, newPower);
  }
}
