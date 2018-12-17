package stock.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import stock.controller.commands.AddToPortfolio;
import stock.controller.commands.CostBasis;
import stock.controller.commands.CreatePortfolio;
import stock.controller.commands.CreateStrategy;
import stock.controller.commands.CurrentValue;
import stock.controller.commands.Save;
import stock.controller.commands.TradeCommand;
import stock.model.PersistentPortfolioManagerModel;
import stock.view.IStockView;

/**
 * This class implements the controller interface IStockController. It implements method to control
 * the full working of the program. It parses commands and delegates the processing to their
 * respective command classes. Entering 'q' or 'quit' terminates the program. Asks to re-enter a
 * command if syntax is incorrect.
 */
public class StockController implements IStockController {
  private final PersistentPortfolioManagerModel model;
  private final IStockView view;

  /**
   * Constructs a controller with given model and view.
   *
   * @param model the input source from which user input is taken.
   * @param view  the output source to which model output is passed.
   */
  public StockController(PersistentPortfolioManagerModel model, IStockView view) {
    this.model = model;
    this.view = view;
  }

  private static final String instructions = "1. To create a portfolio, type 'create_portfolio' " +
          "followed by " +
          "unique portfolio name of your choice.\n 2. To add stock use 'add_stock' followed" +
          " by portfolio name. Enter further details as asked.\n3. To examine portfolio, enter "
          + "'examine_portfolio' followed by portfolio name. Enter further details as asked.\n" +
          "4. To view cost basis or current value, enter 'cost_basis' or 'current_value' " +
          "followed by portfolio name. Enter further details as asked.\n";
  private static final String welcome = "Welcome to Virtual Trading!\nPlease refer the " +
          "documentation for using " +
          "commands.\n";

  private Map<String, TradeCommand> getCommandMap(IStockView view) throws IOException {
    Map<String, TradeCommand> knownCommands = new HashMap<>();
    //needs notify only
    knownCommands.put("create_portfolio", new CreatePortfolio(view::read, view::write));
    //knownCommands.put("examine_portfolio", new ExaminePortfolio(view::read,view::write));
    knownCommands.put("create_strategy", new CreateStrategy(view::read, view::write, view::write));
    knownCommands.put("add_stock", new AddToPortfolio(view::read, view::write, view::write));
    knownCommands.put("cost_basis", new CostBasis(view::read, view::write, view::write));
    knownCommands.put("current_value", new CurrentValue(view::read, view::write, view::write));
    //needs notify only
    knownCommands.put("save_portfolio", new Save(view::read, view::write));
    return knownCommands;
  }

  @Override
  public void execute() throws IOException {
    Map<String, TradeCommand> knownCommands = getCommandMap(view);
    view.write(welcome);
    while (true) {
      view.write(instructions);
      String commandString = view.read();
      if (commandString.equalsIgnoreCase("q") || commandString
              .equalsIgnoreCase("quit")) {
        return;
      }
      TradeCommand command = knownCommands.getOrDefault(commandString, null);
      if (command != null) {
        try {
          command.execute(model);
        } catch (IllegalArgumentException | IllegalStateException e) {
          view.write(e.getMessage());
        }
      } else {
        view.write("Incorrect command. Please refer documentation for " +
                "correct syntax.\n");
        view.flush();
      }
    }
  }
}
