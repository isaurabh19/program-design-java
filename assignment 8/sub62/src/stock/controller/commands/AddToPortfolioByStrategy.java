package stock.controller.commands;

import java.io.IOException;

/**
 * An interface that allows to define new investment strategy commands for adding stocks to a
 * portfolio. A portfolio needs to exist to apply any strategy.
 */
public interface AddToPortfolioByStrategy {
  /**
   * Given the portfolio name, executes the particular strategy command to add stocks in this
   * portfolio.
   *
   * @param portfolioName the name of the portfolio to which the strategy would be applied.
   * @throws IOException if the input source throws any errors.
   */
  void executeStrategyCommand(String portfolioName) throws IOException;
}
