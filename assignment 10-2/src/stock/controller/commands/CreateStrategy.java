package stock.controller.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import stock.model.PersistentPortfolioManagerModel;

/**
 * A command class to create a strategy in order to be saved for later use or applied immediately
 * on a portfolio.
 */
public class CreateStrategy implements TradeCommand {
  private final Supplier<String> reader;
  private final Consumer<String> writer;
  private final Consumer<String> notificationWriter;

  /**
   * Constructs a object for create strategy command.
   * @param reader the Function object that provides data required.
   * @param writer the function object to prompt messages on the view for asking user input.
   * @param notificationWriter notifies the view about feedback of their action or unusual events.
   */
  public CreateStrategy(Supplier<String> reader, Consumer<String> writer, Consumer<String>
          notificationWriter) {
    this.reader = reader;
    this.writer = writer;
    this.notificationWriter = notificationWriter;
  }

  private Map<String, CreateStrategyType> getStrategyMap(PersistentPortfolioManagerModel model) {
    Map<String, CreateStrategyType> commandMap = new HashMap<>();
    commandMap.put("regular", new CreateStrategyRegular(model, reader, writer, notificationWriter));
    commandMap.put("dollar_cost", new CreateStrategyDollarCost(model, reader, writer,
            notificationWriter));
    return commandMap;
  }

  @Override
  public void execute(PersistentPortfolioManagerModel model) throws IOException {
    Map<String, CreateStrategyType> knownCommands = getStrategyMap(model);
    String strategyName = reader.get();
    while (true) {
      writer.accept("Enter strategy: 'regular' or 'dollar_cost'\n");
      String strategy = reader.get();
      try {
        knownCommands.getOrDefault(strategy, null).createStrategyType(strategyName);
        break;
      } catch (NullPointerException e) {
        notificationWriter.accept("Unknown strategy type. Enter again.\n");
      }
    }
    notificationWriter.accept("Created strategy: " + strategyName + ".\n");
  }
}
