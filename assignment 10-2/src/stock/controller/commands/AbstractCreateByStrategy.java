package stock.controller.commands;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import stock.model.PersistStrategyJSON;
import stock.model.PersistStrategyJSONImpl;
import stock.model.PersistentPortfolioManagerModel;
import stock.model.Strategy;

/**
 * An abstract class to help factor out common functions among various CreateStrategyType
 * implementations.
 */
abstract public class AbstractCreateByStrategy implements CreateStrategyType {
  protected boolean isValid(String pattern) {
    return pattern.chars().allMatch(Character::isLetter);
  }

  protected void persistStrategy(Strategy dollarCostStrategy, Supplier<String> reader,
                                 Consumer<String> writer) throws IOException {
    writer.accept("Enter y if you want to save strategy. Enter any other key if otherwise.\n");
    if (reader.get().equalsIgnoreCase("y")) {
      PersistStrategyJSON persist = new PersistStrategyJSONImpl(Paths.get("res",
              "persisted", "strategy").toString());
      persist.saveStrategy(dollarCostStrategy);
    }
  }

  protected void applyStrategy(Strategy dollarCostStrategy, Supplier<String> reader,
                               Consumer<String> writer, PersistentPortfolioManagerModel model) {
    writer.accept("Enter y if you want to apply the strategy.\n");
    if (reader.get().equalsIgnoreCase("y")) {
      writer.accept("Enter portfolioName");
      model.addToPortfolio(reader.get(), dollarCostStrategy);
    }
  }

  protected double getAmount(Supplier<String> reader, Consumer<String>
          notify, Predicate<Double> test) throws IOException {
    double amount;
    while (true) {
      String value = reader.get();
      try {
        amount = Double.parseDouble(value);
        if (test.test(amount)) {
          return amount;
        } else {
          notify.accept("Expecting non zero amount as decimals. Enter again\n");
        }
      } catch (NumberFormatException e) {
        notify.accept("Expecting number. Found non number value. Enter again\n");
      }
    }
  }

  @Override
  abstract public void createStrategyType(String strategyName) throws IOException;
}
