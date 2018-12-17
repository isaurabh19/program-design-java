package stock.controller.commands;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Supplier;

import stock.model.PersistPortfolioJSON;
import stock.model.PersistPortfolioJSONImpl;
import stock.model.PersistentPortfolioManagerModel;

/**
 * A class that implements a command to get the cost basis of the portfolio. The syntax is:
 * cost_basis[space][portfolio name|path] and date as asked. The cost basis of portfolio is the
 * total buying cost of its composition. It will ask to re-enter the command if incorrect portfolio
 * name is passed.
 */
public class CostBasis implements TradeCommand {
  private final Supplier<String> reader;
  private final Consumer<String> writer;
  private final Consumer<String> notify;

  /**
   * Constructs an object for the command that gets cost basis of a portfolio.
   *
   * @param reader the Function object that provides data required.
   * @param writer the function object to prompt messages on the view for asking user input.
   * @param notify notifies the view about feedback of their action or unusual events.
   */
  public CostBasis(Supplier<String> reader, Consumer<String> writer, Consumer<String>
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

  @Override
  public void execute(PersistentPortfolioManagerModel model) throws IOException {
    String portfolio = getPortfolioName(model);
    while (true) {
      LocalDateTime dateTime = DateHelper.getDateTime(reader, writer, notify, false);
      if (dateTime != null) {
        double costBasis = model.getPortfolioCostBasis(portfolio, dateTime);
        String output = "Cost basis for " + portfolio + "on " + dateTime.toString() + ": " +
                costBasis + "\n";
        notify.accept(output);
        break;
      } else {
        notify.accept("Invalid value for date. Enter date.\n");
      }
    }
  }
}
