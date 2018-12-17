package stock.controller.commands;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import stock.model.PersistPortfolioJSON;
import stock.model.PersistPortfolioJSONImpl;
import stock.model.PersistStrategyJSON;
import stock.model.PersistStrategyJSONImpl;
import stock.model.PersistentPortfolioManagerModel;
import stock.model.Strategy;

/**
 * A class that implements adding stock to portfolio command. The syntax for the command is
 * 'add_stock'. Then based on the strategy command, inputs will be prompted. Any error in parameter
 * asks for re-entry of the parameter. This operation results in stocks being added to the
 * portfolio.
 */
public class AddToPortfolio implements TradeCommand {
  private final Supplier<String> reader;
  private final Consumer<String> writer;
  private final Consumer<String> notify;

  /**
   * Constructs an object for the command that adds stocks a portfolio.
   *
   * @param reader the Function object that provides data required.
   * @param writer the function object to prompt messages on the view for asking user input.
   * @param notify notifies the view about feedback of their action or unusual events.
   */
  public AddToPortfolio(Supplier<String> reader, Consumer<String> writer, Consumer<String>
          notify) {
    this.reader = reader;
    this.writer = writer;
    this.notify = notify;
  }

  private String getPortfolioName(PersistentPortfolioManagerModel model) {
    writer.accept("Enter y to load a portfolio. Enter n otherwise.\n");
    String portfolioName;
    if (reader.get().equalsIgnoreCase("y")) {
      writer.accept("Enter the path for portfolio.\n");
      PersistPortfolioJSON persistPortfolio = new PersistPortfolioJSONImpl(Paths.get("res",
              "persisted", "portfolio").toString());
      portfolioName = model.loadPortfolio(reader.get(), persistPortfolio);
    } else {
      writer.accept("Enter portfolio name.\n");
      portfolioName = reader.get();
    }
    return portfolioName;
  }

  private Strategy getStrategy() {
    writer.accept("Enter strategy file path.\n");
    String strategyPath = reader.get();
    PersistStrategyJSON persist = new PersistStrategyJSONImpl(Paths.get("res",
            "persisted", "strategy").toString());
    return persist.loadStrategy(strategyPath);
  }

  @Override
  public void execute(PersistentPortfolioManagerModel model) throws IOException {
    //Map<String, AddToPortfolioByStrategyPersistent> strategyCommands = getStrategyMap(model);
    try {
      String portfolioName = getPortfolioName(model);
      Strategy strategy = getStrategy();
      model.addToPortfolio(portfolioName, strategy);
      notify.accept("Stocks added successfully. You can examine the portfolio to" +
              " see the " + "stocks.\n");
    } catch (NoSuchElementException e) {
      notify.accept("\nIncomplete number of parameters for the command. Refer " +
              "documentation " + "for help.\n");
    }
  }

}
