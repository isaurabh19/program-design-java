package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;

import stock.StrategyFactory;
import stock.model.IPortfolioManagerModel;
import stock.model.Strategy;
import stock.view.IStockView;

/**
 * A class that implements command to add stocks in a regular way one by one. It takes in the
 * ticker symbol of the stock, amount of stocks to be bought, commission fee for this transaction
 * and date-time.
 */
public class AddToPortfolioRegular extends AbstractAddToPortfolioByStrategy implements
        AddToPortfolioByStrategy {

  private final IStockView view;
  private final IPortfolioManagerModel model;

  /**
   * Constructs an object of regular strategy command.
   * @param model model to which strategy is passed.
   * @param view the view to which model output will be given.
   */

  public AddToPortfolioRegular(IPortfolioManagerModel model, IStockView view) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void executeStrategyCommand(String portfolioName) throws IOException {
    view.write("To add via a regular strategy, enter the ticker symbol of the stock.\n");
    String ticker = view.read();
    while (!isValid(ticker)) {
      view.write("Ticker symbol has to be alphabets only. Enter again.\n");
      ticker = view.read();
    }
    view.write("Enter the amount.\n");
    double amount = getAmount(view);
    view.write("Enter commission fees as absolute value.\n");
    double commission = getAmount(view);
    LocalDateTime dateTime = DateHelper.getDateTime(view,true);
    Strategy strategy = StrategyFactory.getRegularStrategyObject(ticker, amount,commission,
            dateTime);
    model.addToPortfolio(portfolioName,strategy);
  }
}
