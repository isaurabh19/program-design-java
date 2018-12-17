package stock.controller.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import stock.model.IPortfolioManagerModel;
import stock.view.IStockView;

/**
 * A class that implements adding stock to portfolio command. The syntax for the command is
 * 'add_stock [portfolioName]'. Then based on the strategy command, delegates the job to that
 * strategy. Any error in parameter asks for re-entry of the parameter.
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


  private Map<String, AddToPortfolioByStrategy> getStrategyMap(IPortfolioManagerModel model) {
    Map<String, AddToPortfolioByStrategy> commandMap = new HashMap<>();
    commandMap.put("regular", new AddToPortfolioRegular(model, view));
    commandMap.put("dollar_cost", new AddToPortfolioDollarCost(model, view));
    return commandMap;
  }

  private void executeStrategy(Map<String, AddToPortfolioByStrategy> strategyCommands,
                               String portfolioName) throws IOException {
    while (true) {
      String strategy = view.read();
      try {
        strategyCommands.getOrDefault(strategy, null)
                .executeStrategyCommand(portfolioName);
        break;
      } catch (NullPointerException e) {
        view.write("Incorrect strategy command.\n Enter strategy command again.\n");
      }

    }
  }

  @Override
  public void execute(IPortfolioManagerModel model) throws IOException {
    Map<String, AddToPortfolioByStrategy> strategyCommands = getStrategyMap(model);
    try {
      String portfolioName = view.read();
      view.write("Enter strategy: 'regular' or 'dollar_cost'\n");
      executeStrategy(strategyCommands, portfolioName);
      view.write("Stocks added successfully. You can examine the portfolio to see the " +
              "stocks.\n");
    } catch (NoSuchElementException e) {
      view.write("\nIncomplete number of parameters for the command. Refer documentation " +
              "for help.\n");
    }
  }
}
