package stock.controller.commands;

import java.io.IOException;

import stock.model.IPortfolioManagerModel;
import stock.view.IStockView;

/**
 * A class that implements a command to examine the composition of a portfolio. The syntax is:
 * examine_portfolio[space][-all] to view all portfolio names. examine_portfolio[space][portfolio
 * name] to view all stocks information in a portfolio. Re-enter the command if portfolio name
 * doesn't exist or wrong syntax.
 * If a portfolio is empty, it will be shown as empty string on the output.
 */

public class ExaminePortfolio implements TradeCommand {
  private final IStockView view;

  /**
   * Constructs an object for the command that gets current value of a portfolio.
   *
   * @param view the view from which user input is obtained.
   */
  public ExaminePortfolio(IStockView view) {
    this.view = view;
  }


  @Override
  public void execute(IPortfolioManagerModel model) throws IOException {
    String names = view.read();
    String result;
    if (names.equalsIgnoreCase("-all")) {
      result = model.toString();
    } else {
      result = model.getPortfolioContent(names);
    }
    view.write(result);
  }
}
