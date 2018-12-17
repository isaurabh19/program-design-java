package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Supplier;

import stock.StrategyFactory;
import stock.model.PersistentPortfolioManagerModel;
import stock.model.Strategy;

/**
 * A class that implements command to add stocks in a regular way one by one. It takes in the ticker
 * symbol of the stock, amount of stocks to be bought, commission fee for this transaction and
 * date-time.
 */
public class CreateStrategyRegular extends AbstractCreateByStrategy implements
        CreateStrategyType {

  private final Supplier<String> reader;
  private final Consumer<String> writer;
  private final Consumer<String> notify;
  private final PersistentPortfolioManagerModel model;

  /**
   * Constructs an object of regular strategy command.
   *
   * @param model  model to which strategy is passed.
   * @param reader the Function object that provides data required.
   * @param writer the function object to prompt messages on the view for asking user input.
   * @param notify notifies the view about feedback of their action or unusual events.
   */
  public CreateStrategyRegular(PersistentPortfolioManagerModel model, Supplier<String> reader,
                               Consumer<String> writer, Consumer<String> notify) {
    this.reader = reader;
    this.writer = writer;
    this.model = model;
    this.notify = notify;
  }

  @Override
  public void createStrategyType(String strategyName) throws IOException {
    writer.accept("To add via a regular strategy, enter the ticker symbol of the stock.\n");
    String ticker = reader.get();
    while (!isValid(ticker)) {
      notify.accept("Ticker symbol has to be alphabets only. Enter again.\n");
      ticker = reader.get();
    }
    writer.accept("Enter the amount.\n");
    double amount = getAmount(reader, notify, d -> d > 0.0);
    writer.accept("Enter commission fees as absolute value.\n");
    double commission = getAmount(reader, notify, d -> d >= 0);
    LocalDateTime dateTime = DateHelper.getDateTime(reader, writer, notify, true);
    Strategy strategy = StrategyFactory.getRegularStrategyObject(ticker, amount, commission,
            dateTime, strategyName);
    persistStrategy(strategy, reader, writer);
    applyStrategy(strategy, reader, writer, model);
    //model.addToPortfolio(portfolioName,strategy);
    //model.addStrategy(strategy);
  }
}
