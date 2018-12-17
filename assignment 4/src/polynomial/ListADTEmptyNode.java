package polynomial;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A class that implements the ListADTNode. It represents a node in the list that holds no data of
 * any value and marks the end of the list.
 */
public class ListADTEmptyNode implements ListADTNode<PolynomialTerm> {

  @Override
  public ListADTNode<PolynomialTerm> addTerm(PolynomialTerm term) {
    return new ListADTElementNode(term, new ListADTEmptyNode());
  }

  @Override
  public ListADTNode<PolynomialTerm> map(Function<PolynomialTerm, PolynomialTerm> converter) {
    return new ListADTEmptyNode();
  }

  @Override
  public int getCoefficient(int power) {
    return 0;
  }

  @Override
  public int getDegree() {
    return 0;
  }

  @Override
  public ListADTNode add(ListADTNode head) {
    return new ListADTElementNode(head.getTerm(), head.getRest());
  }

  @Override
  public PolynomialTerm getTerm() {
    return new PolynomialTerm(0, 0);
  }

  @Override
  public <R> R reduce(R init, BiFunction<R, PolynomialTerm, R> biFunction) {
    return init;
  }

  @Override
  public ListADTNode getRest() {
    return new ListADTEmptyNode();
  }
}
