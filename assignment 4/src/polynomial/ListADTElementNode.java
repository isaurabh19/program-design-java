package polynomial;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A class that implements the ListADTNode. It represents a node in the list that hold data of any
 * value and performs operations that work with stored data. Currently, it stores an object of
 * PolynomialTerm and the reference to the remaining list after this node.
 */
public class ListADTElementNode implements ListADTNode<PolynomialTerm> {
  private PolynomialTerm term;
  private ListADTNode<PolynomialTerm> rest;

  protected ListADTElementNode(PolynomialTerm term, ListADTNode<PolynomialTerm> rest) {
    this.term = term;
    this.rest = rest;
  }

  @Override
  public ListADTNode<PolynomialTerm> addTerm(PolynomialTerm term) {

    if (term.getPower() == this.term.getPower()) {
      return new ListADTElementNode(new PolynomialTerm(term.getCoefficient() + this.term
              .getCoefficient(), term.getPower()), this.rest);
    }
    if (term.getPower() > this.term.getPower()) {
      return new ListADTElementNode(term, this);
    } else {
      this.rest = this.rest.addTerm(term);
    }
    return this;
  }

  @Override
  public ListADTNode<PolynomialTerm> map(Function<PolynomialTerm, PolynomialTerm> converter) {
    return new ListADTElementNode(converter.apply(this.term), this.rest.map(converter));
  }

  @Override
  public int getCoefficient(int power) {
    if (this.term.getPower() == power) {
      return this.term.getCoefficient();
    }
    return this.rest.getCoefficient(power);
  }

  @Override
  public int getDegree() {
    if (this.term.getCoefficient() == 0) {
      return this.rest.getDegree();
    }
    return this.term.getPower();
  }


  @Override
  public ListADTNode<PolynomialTerm> add(ListADTNode head) {
    if (this.term.getPower() == head.getTerm().getPower()) {
      PolynomialTerm newTerm = new PolynomialTerm(this.term.getCoefficient() + head
              .getTerm().getCoefficient(), this.term.getPower());
      return new ListADTElementNode(newTerm, this.rest.add(head.getRest()));
    } else if (this.term.getPower() > head.getTerm().getPower()) {
      return new ListADTElementNode(this.term, this.rest.add(head));
    }
    return new ListADTElementNode(head.getTerm(), this.add(head.getRest()));
  }

  @Override
  public PolynomialTerm getTerm() {
    return this.term;
  }

  @Override
  public <R> R reduce(R init, BiFunction<R, PolynomialTerm, R> biFunction) {
    return this.rest.reduce(biFunction.apply(init, this.term), biFunction);
  }


  @Override
  public ListADTNode getRest() {
    return this.rest;
  }
}
