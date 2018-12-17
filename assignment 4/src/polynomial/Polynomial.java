package polynomial;

/**
 * An interface that represents a polynomial and various operations that can be performed on a
 * polynomial. This polynomial currently supports only positive whole number powers and integral
 * coefficients. All terms in the polynomial are one variable. Two polynomials are considered same
 * if they have all terms with same coefficients and powers. 0 represents an empty polynomial. A
 * valid polynomial is represented as string, for example, as 3x^2-1x^1+4.
 */
public interface Polynomial {
  /**
   * Adds a new valid term to an existing polynomial. Terms with 0 coefficients are not added.
   *
   * @param coefficient Integer number representing coefficient of the term.
   * @param power       Positive whole number denoting the power of the term.
   * @throws IllegalArgumentException if either of the parameters are invalid.
   */
  void addTerm(int coefficient, int power) throws IllegalArgumentException;

  /**
   * Returns the degree of the polynomial i.e. the highest power of a non-zero coefficient term.
   *
   * @return degree of the polynomial as integer.
   */
  int getDegree();

  /**
   * Returns the coefficient of a term given the power of that term. If the power doesn't exist,
   * returns 0.
   *
   * @param power the power of the term to search for.
   * @return coefficient of the term with given power.
   */
  int getCoefficient(int power);

  /**
   * Evaluates the polynomial expression with a given value and returns the result.
   *
   * @param variable the value for the variable.
   * @return a double value representing the answer of the polynomial given the variable value.
   */
  double evaluate(double variable);

  /**
   * Adds two polynomials and returns a new polynomial obtained as a result of addition. Addition is
   * performed term by term. Terms with same power are added by addition of their coefficients.
   *
   * @param polynomial the valid polynomial to be added.
   * @return a new polynomial resulting from the addition.
   */
  Polynomial add(Polynomial polynomial);

  /**
   * A derivative of the polynomial. It is calculated as per the rules of calculus for computing
   * derivatives of polynomial terms.
   *
   * @return a polynomial with terms replaced by the result of derivative operation.
   */
  Polynomial derivative();
}
