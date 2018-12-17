package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;

import stock.model.IPortfolioManagerModel;
import stock.view.IStockView;

/**
 * A class that implements a command to get the current value of a portfolio. The current value of a
 * portfolio on a given date will be the sum of current values of the individual stocks held in the
 * portfolio on that date. The syntax for the command is: current_value[space][portfolio
 * name][space][date in yyyy-mm-dd, hh:mm]. It will ask to re-enter the command if wrong parameters
 * are passed.
 */

public class CurrentValue implements TradeCommand {
  private final IStockView view;

  /**
   * Constructs an object for the command that gets current value of a portfolio.
   *
   * @param view the view from which user input is obtained.
   */
  public CurrentValue(IStockView view) {
    this.view = view;
  }

  @Override
  public void execute(IPortfolioManagerModel model) throws IOException {
    String portfolio = view.read();
    LocalDateTime date = AbstractGetDateHelper.getDateTime(view);
    double currentValue = model.getPortfolioCurrentValue(portfolio, date);
    String output = "Current Value for " + portfolio + "on " + date.toString() + ": "
            + currentValue + "\n";
    view.write(output);
  }
}
