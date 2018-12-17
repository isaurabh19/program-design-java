package stock.model;

import java.io.IOException;

/**
 * The interface that handles the serializing and deserializing of portfolio objects.
 */
public interface PersistStrategyJSON {

  /**
   * Saves the given strategy object as JSON.
   * @param strategy the object to be serialized.
   * @throws IOException if input source throws error.
   */
  void saveStrategy(Strategy strategy) throws IOException;

  /**
   * Loads a strategy object on the disk by deserializing the object from the JSON representation.
   * @param strategyName the name of the strategy file or path.
   * @return the object converted from the JSON.
   */
  Strategy loadStrategy(String strategyName);

}
