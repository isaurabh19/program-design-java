package polynomial;

import java.util.Objects;
import java.util.Scanner;

/**
 * A class that implements a polynomial and the operations performed on them. It represents the
 * polynomial with a data structure called ListADTNode. It provides ways to create a polynomial
 * object, compare two polynomial objects and represent the polynomial object as a string.
 */
public class PolynomialImpl implements Polynomial {
  private ListADTNode<PolynomialTerm> head;

  private PolynomialImpl(ListADTNode<PolynomialTerm> node) {
    this.head = node;
  }

  /**
   * Constructs an empty polynomial object. An empty object has coefficient and power as 0.
   */
  public PolynomialImpl() {
    head = new ListADTElementNode(new PolynomialTerm(0, 0),
            new ListADTEmptyNode());
  }

  /**
   * Constructs a polynomial from a string expression. Each term has to be separated by spaces.
   * Accepts a single variable x' and correct format of a term is, for example, -3x^2, for a term
   * with coefficient as -3 and power as 2. An example string: 3x^3 -x^2 +x^1 -5.
   *
   * @param expression a string that denotes a valid polynomial expression.
   * @throws IllegalArgumentException if any invalid character or incorrect format of the string.
   */
  public PolynomialImpl(String expression) throws IllegalArgumentException {
    head = new ListADTElementNode(new PolynomialTerm(0, 0),
            new ListADTEmptyNode());
    Scanner scanner = new Scanner(expression);
    int coefficient;
    int power;
    while (scanner.hasNext()) {
      String[] terms = scanner.next().split("[xX]\\^");
      if (!terms[0].isEmpty()) {
        try {
          coefficient = Integer.parseInt(terms[0]);
        } catch (NumberFormatException e) {
          switch (terms[0]) {
            case "-":
              coefficient = -1;
              break;
            case "+":
              coefficient = 1;
              break;
            default:
              throw new IllegalArgumentException("Invalid term in coefficient");
          }
        }
      } else {
        coefficient = 1;
      }
      if (terms.length == 1) {
        power = 0;
      } else {
        try {
          power = Integer.parseInt(terms[1]);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid term in power");
        }
      }
      addTerm(coefficient, power);
    }
  }

  /**
   * Returns a unique identifier for the object that works symmetric with equals method.
   *
   * @return unique integer for each unique object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.toString());
  }

  /**
   * Compares two objects based on all attributes of each object.
   *
   * @param obj Object to be compared.
   * @return boolean denoting if the called object is same as passed object.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Polynomial)) {
      return false;
    }
    Polynomial polynomial = (Polynomial) obj;
    return this.toString().equals(polynomial.toString());
  }

  /**
   * Represents a polynomial as string by representing each term in the polynomial in string
   * format.
   *
   * @return string representing the polynomial.
   */
  @Override
  public String toString() {
    //ListADTNode<String> strings = this.head.map(term -> term.toString());
    String polynomial = this.head.reduce("", (a, b) -> a + b.toString()).trim();
    if (polynomial.isEmpty()) {
      return "0";
    }
    if (polynomial.charAt(0) == '+') {
      polynomial = polynomial.substring(1);
    }
    return polynomial;
  }

  @Override
  public void addTerm(int coefficient, int power) throws IllegalArgumentException {
    if (power < 0) {
      throw new IllegalArgumentException("Negative powers are not allowed");
    }
    PolynomialTerm term = new PolynomialTerm(coefficient, power);
    this.head = this.head.addTerm(term);
  }

  @Override
  public int getDegree() {
    return this.head.getDegree();
  }

  @Override
  public int getCoefficient(int power) {
    return this.head.getCoefficient(power);
  }

  @Override
  public double evaluate(double variable) {
    return this.head.reduce(0.0, (a, b) -> a + b.evaluate(variable));
  }

  @Override
  public Polynomial add(Polynomial polynomial) {
    if (polynomial instanceof PolynomialImpl) {
      return new PolynomialImpl(this.head.add(((PolynomialImpl) polynomial).head));
    }
    throw new IllegalArgumentException("");
  }

  @Override
  public Polynomial derivative() {
    return new PolynomialImpl(this.head.map(PolynomialTerm::derivative));
  }
}
