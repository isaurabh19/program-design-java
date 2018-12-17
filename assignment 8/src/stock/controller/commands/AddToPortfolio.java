package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import stock.model.IPortfolioManagerModel;
import stock.view.IStockView;

/**
 * A class that implements the adding stock to portfolio command. The syntax for the command is
 * 'add_stock' [yourPortfolioName][space][ticker symbol][space][dollar amount][space] [date as
 * yyyy-mm-dd,hh:mm][new line]. The dollar amount has to be positive non zero decimal. Any error in
 * parameter asks for re-entry of the parameter. Incomplete number of parameters asks to re-enter
 * the command with correct number and sequence of parameters.
 */
public class AddToPortfolio implements TradeCommand {
  private final IStockView view;

  /**
   * Constructs an object for the command that adds stocks a portfolio.
   *
   * @param view the view from which user input is obtained.
   */
  public AddToPortfolio(IStockView view) {
    this.view = view;
  }

  private boolean isValid(String pattern) {
    return pattern.chars().allMatch(Character::isLetter);
  }

  private double getAmount() throws IOException {
    double amount;

    while (true) {
      String value = view.read();
      try {
        amount = Double.parseDouble(value);
        if (amount > 0.0) {
          return amount;
        } else {
          view.flush();
          throw new IllegalArgumentException("Expecting non zero amount as decimals.\n");
        }
      } catch (NumberFormatException e) {
        view.write("Expecting decimal number. Found non decimal value");
      }
    }
  }

  @Override
  public void execute(IPortfolioManagerModel model) throws IOException {
    try {
      String portfolioName = view.read();
      String ticker = view.read();
      if (!isValid(ticker)) {
        throw new IllegalArgumentException("Ticker symbol has to be alphabets only. Enter the " +
                "command again.\n");
        //ticker = view.read();
      }
      double amount = getAmount();
      LocalDateTime dateTime = AbstractGetDateHelper.getDateTime(view);
      model.addToPortfolio(portfolioName, ticker, amount, dateTime);
      String[] contents = model.getPortfolioContent(portfolioName).split("\n");
      view.write(contents[contents.length - 1] + "\n");
    } catch (NoSuchElementException e) {
      view.write("\nIncomplete number of parameters for the command. Refer documentation " +
              "for help.\n");
    }
  }
}
