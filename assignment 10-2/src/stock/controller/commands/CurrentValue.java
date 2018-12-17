package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Supplier;

import stock.model.PersistentPortfolioManagerModel;

/**
 * A class that implements a command to get the current value of a portfolio. The current value of a
 * portfolio on a given date will be the sum of current values of the individual stocks held in the
 * portfolio on that date. The syntax for the command is: current_value[space][portfolio name| path]
 * and date as prompted individually in year, month and day format.
 */

public class CurrentValue implements TradeCommand {
  private final Supplier<String> reader;
  private final Consumer<String> writer;
  private final Consumer<String> notify;

  /**
   * Constructs an object for the command that gets current value of a portfolio.
   *
   * @param reader the Function object that provides data required.
   * @param writer the function object to prompt messages on the view for asking user input.
   * @param notify notifies the view about feedback of their action or unusual events.
   */
  public CurrentValue(Supplier<String> reader, Consumer<String> writer, Consumer<String>
          notify) {
    this.reader = reader;
    this.writer = writer;
    this.notify = notify;
  }

  @Override
  public void execute(PersistentPortfolioManagerModel model) throws IOException {

    String portfolio = reader.get();
    while (true) {
      LocalDateTime dateTime = DateHelper.getDateTime(reader, writer, notify, false);
      if (dateTime != null) {
        double currentValue = model.getPortfolioCurrentValue(portfolio, dateTime);
        String output = "Current Value for " + portfolio + "on " + dateTime.toString() + ": "
                + currentValue + "\n";
        notify.accept(output);
        break;
      } else {
        notify.accept("Invalid value for date. Enter date.\n");
      }

    }

  }
}
