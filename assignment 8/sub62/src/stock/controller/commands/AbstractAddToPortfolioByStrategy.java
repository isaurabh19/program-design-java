package stock.controller.commands;

import java.io.IOException;

import stock.view.IStockView;

/**
 * This abstract class houses all the abstract methods present for the Strategy. It consists of the
 * methods which are common for all the strategy objects.
 */
public abstract class AbstractAddToPortfolioByStrategy implements AddToPortfolioByStrategy {

  protected boolean isValid(String pattern) {
    return pattern.chars().allMatch(Character::isLetter);
  }

  protected double getAmount(IStockView view) throws IOException {
    double amount;

    while (true) {
      String value = view.read();
      try {
        amount = Double.parseDouble(value);
        if (amount > 0.0) {
          return amount;
        } else {
          view.write("Expecting non zero amount as decimals. Enter again\n");
        }
      } catch (NumberFormatException e) {
        view.write("Expecting number. Found non number value. Enter again\n");
      }
    }
  }

  @Override
  public abstract void executeStrategyCommand(String name) throws IOException;

}
