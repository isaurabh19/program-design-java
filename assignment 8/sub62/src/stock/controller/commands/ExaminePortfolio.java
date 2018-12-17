package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import stock.model.IPortfolioManagerModel;
import stock.view.IStockView;

/**
 * A class that implements a command to examine the composition of a portfolio. The syntax is:
 * examine_portfolio[space][-all] to view all portfolio names. examine_portfolio[space][portfolio
 * name]  and date to view all stocks information in a portfolio for that date. Re-enter the command
 * if portfolio name doesn't exist or wrong syntax.
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
    LocalDateTime dateTime = DateHelper.getDateTime(view, false);
    String result;
    String header;
    if (names.equalsIgnoreCase("-all")) {
      result = model.toString();
      header = "Names of Portfolios\n==================================================\n";
    } else {
      result = model.getContentsOn(names, dateTime).entrySet().stream().map(e ->
              e.getKey() + "\t\t\t" + e.getValue()).collect(Collectors.joining("\n"));
      header = "Stock\t\t\tQuantity\n====================================================\n";
    }
    view.write(header + result + "\n");
  }
}
