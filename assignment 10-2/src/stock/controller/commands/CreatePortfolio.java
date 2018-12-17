package stock.controller.commands;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import stock.model.PersistentPortfolioManagerModel;

/**
 * A class that implements a command to create a portfolio. The syntax for the command is:
 * create_portfolio[space][portfolio name]. A portfolio will be a collection of stocks, whose cost
 * value and current value at a certain date can be obtained. Portfolio name must be unique and
 * command will not allow to create portfolios with duplicate names. Portfolio name cannot contain
 * spaces.
 */

public class CreatePortfolio implements TradeCommand {

  private final Supplier<String> reader;
  private final Consumer<String> notify;

  /**
   * Constructs an object for the command that creates a portfolio.
   *
   * @param reader the Function object that provides data required.
   * @param notify notifies the view about feedback of their action or unusual events.
   * @throws IOException if the input source throws any error.
   */
  public CreatePortfolio(Supplier<String> reader, Consumer<String> notify) throws IOException {
    this.reader = reader;
    this.notify = notify;
  }

  @Override
  public void execute(PersistentPortfolioManagerModel model) throws IllegalArgumentException {
    String name = reader.get();
    model.createPortfolio(name);
    notify.accept("Portfolio created: " + name + ".\n");
  }
}
