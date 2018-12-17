package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;

import stock.model.IPortfolioManagerModel;
import stock.view.IStockView;

/**
 * A class that implements a command to get the cost basis of the portfolio. The syntax is:
 * cost_basis[space][portfolio name] and date as asked. The cost basis of portfolio is the total
 * buying cost of its composition. It will ask to re-enter the command if incorrect portfolio name
 * is passed.
 */
public class CostBasis implements TradeCommand {
  private final IStockView view;

  /**
   * Constructs an object for the command that gets cost basis of a portfolio.
   *
   * @param view the view from which user input is obtained.
   */
  public CostBasis(IStockView view) {
    this.view = view;
  }

  @Override
  public void execute(IPortfolioManagerModel model) throws IOException {
    String portfolio = view.read();
    LocalDateTime dateTime = DateHelper.getDateTime(view, false);
    double costBasis = model.getPortfolioCostBasis(portfolio, dateTime);
    String output = "Cost basis for " + portfolio + ": " + costBasis + "\n";
    view.write(output);
  }
}
