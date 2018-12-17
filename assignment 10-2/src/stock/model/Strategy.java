package stock.model;

/**
 * An interface that facilitates creation of new high level investment strategies that can be
 * applied on a portfolio. A portfolio can have many strategies applied on a subset of stocks.
 */
public interface Strategy {

  /**
   * Executes the strategy with its set of logic on this portfolio.
   *
   * @param portfolio the name of the portfolio on which the strategy is executed.
   */
  void execute(IPortfolio portfolio);

  /**
   * Returns the type of investment strategy.
   *
   * @return a string denoting the type.
   */
  String getType();
}
