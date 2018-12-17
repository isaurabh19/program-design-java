package stock.controller.commands;

import java.io.IOException;

/**
 * An interface that represents a CreateStrategy type operation for various strategies. It provides
 * method every create strategy command class will have to implement.
 */
public interface CreateStrategyType {
  /**
   * Creates a strategy in the command class with the given name.
   * @param strategyName the unique name for a strategy.
   * @throws IOException if the input source throws error.
   */
  void createStrategyType(String strategyName) throws IOException;
}
