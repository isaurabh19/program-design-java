package stock.model;

import java.io.IOException;

/**
 * The interface that handles the serializing and deserializing of portfolio objects.
 */
public interface PersistPortfolioJSON {
  /**
   * Saves the given portfolio object as JSON.
   * @param portfolio the object to be serialized.
   * @throws IOException if input source throws error.
   */
  void savePortfolio(IPortfolio portfolio) throws IOException;

  /**
   * Loads a portfolio object on the disk by deserializing the object from the JSON representation.
   * @param portfolioName the name of the portfolio file or path.
   * @return the object converted from the JSON.
   */
  IPortfolio loadPortfolio(String portfolioName);
}
