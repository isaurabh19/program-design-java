package polynomial;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * An abstract data type that represents a list of objects of polynomials. Represents the what the
 * polynomial operations can be performed on this ADT. The ADT consists of nodes with each node
 * holding an object of PolynomialTerm and the reference to its next node.
 *
 * @param <T> The type of object the list will hold. Currently hold PolynomialTerm objects.
 */
public interface ListADTNode<T> {
  /**
   * Adds a term to the entire polynomial and returns the new list as a result of adding a new ]
   * term. The terms are always added such that the list is ordered in decreasing values of power
   * after addition.
   *
   * @param term the polynomial term to be added.
   * @return a new ListADTNode after the added term.
   */
  ListADTNode<T> addTerm(PolynomialTerm term);

  /**
   * A higher order function that applies a function to convert each node in the list from
   * PolynomialTerm object to another PolynomialTerm object.
   *
   * @param converter the function that will be applied on each element.
   * @return A new ListADTNode of PolynomialTerm objects.
   */
  ListADTNode<PolynomialTerm> map(Function<PolynomialTerm, PolynomialTerm> converter);

  /**
   * Returns the coefficient of the term whose power matches the given power value.
   *
   * @param power power of the term whose coefficient is to be returned.
   * @return coefficient of the polynomial term.
   */
  int getCoefficient(int power);

  /**
   * Returns the degree i.e. the highest power of non-zero coefficient term of the polynomial.
   *
   * @return degree of the polynomial.
   */
  int getDegree();

  /**
   * Adds two ListADTNodes and returns a new ListADTNode of the polynomials.
   *
   * @param head the starting reference of the ListADTNode to be added.
   * @return a new ListADTNode of PolynomialTerm objects.
   */
  ListADTNode<PolynomialTerm> add(ListADTNode head);

  /**
   * Returns the PolynomialTerm object of the node.
   *
   * @return a PolynomialTerm object of this node.
   */
  PolynomialTerm getTerm();

  /**
   * Returns the rest of the ListADTNode for the current node.
   *
   * @return a reference to the remaining list after the current node.
   */
  ListADTNode<PolynomialTerm> getRest();

  /**
   * A higher order function that applies a function taking two arguments and returns the result of
   * the applied function. The passed function is applied on each element of this list. This higher
   * order function works as an accumulator that works on each element of the list, applies the
   * passed function and passes the result as the new accumulated value. After the end of the list,
   * returns a single value answer denoting the accumulated value.
   *
   * @param init       the initial value or the accumulated value so far.
   * @param biFunction the function that will be applied to each node.
   * @param <R>        the type of result that will be returned.
   * @return the single value answer as a result of applying the function on the passed arguments.
   */
  <R> R reduce(R init, BiFunction<R, PolynomialTerm, R> biFunction);
}
