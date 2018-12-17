package stock.controller.commands;

import java.io.IOException;

import stock.model.IPortfolioManagerModel;
import stock.view.IStockView;

/**
 * A class that implements a command to create a portfolio. The syntax for the command is:
 * create_portfolio[space][portfolio name]. A portfolio will be a collection of stocks, whose cost
 * value and current value at a certain date can be obtained. IPortfolio name must be unique and
 * command will not allow to create portfolios with duplicate names. IPortfolio name cannot contain
 * spaces.
 */

public class CreatePortfolio implements TradeCommand {
  private final IStockView view;

  /**
   * Constructs an object for the command that creates a portfolio.
   *
   * @param view the view from which user input is obtained.
   */
  public CreatePortfolio(IStockView view) {
    this.view = view;
  }

  @Override
  public void execute(IPortfolioManagerModel model) throws IllegalArgumentException, IOException {
    String name = view.read();
    model.createPortfolio(name);
    view.write("IPortfolio created: " + name + ".\n");
  }
}
