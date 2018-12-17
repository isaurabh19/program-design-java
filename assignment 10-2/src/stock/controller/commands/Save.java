package stock.controller.commands;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Supplier;

import stock.model.PersistPortfolioJSON;
import stock.model.PersistPortfolioJSONImpl;
import stock.model.PersistentPortfolioManagerModel;

/**
 * A command to save a portfolio tp disk in text form such as JSON, XML, CSV etc.
 */
public class Save implements TradeCommand {
  private final Supplier<String> reader;
  private final Consumer<String> notify;

  /**
   * Constructs a command class object for saving.
   *
   * @param reader the Function object that provides data required.
   * @param notify notifies the view about feedback of their action or unusual events.
   */
  public Save(Supplier<String> reader, Consumer<String> notify) {
    this.reader = reader;
    this.notify = notify;
  }

  @Override
  public void execute(PersistentPortfolioManagerModel model) throws IOException {
    while (true) {
      String portfolioName = reader.get();
      try {
        PersistPortfolioJSON portfolioJSON = new PersistPortfolioJSONImpl(Paths.get("res",
                "persisted", "portfolio").toString());
        model.savePortfolio(portfolioName, portfolioJSON);
        notify.accept("Portfolio saved successfully.\n");
        break;
      } catch (IllegalArgumentException e) {
        notify.accept("File Name Already exists. Enter a unique name.\n");
      }
    }
  }
}

