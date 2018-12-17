package stock.controller.commands;

import java.io.IOException;

import stock.model.IPortfolioManagerModel;

/**
 * An interface that represents a command for this program. Any new command that has to be added
 * must implement this interface.
 */
public interface TradeCommand {
  /**
   * A method to execute the command by passing the model on which the command is to be worked.
   *
   * @param model the model on which the command will operate.
   * @throws IOException if controller cannot read or write to sources correctly.
   */
  void execute(IPortfolioManagerModel model) throws IOException;
}
