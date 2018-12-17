package stock.model;

import java.io.IOException;

/**
 * This interface is an enhanced version of IPortfolioManagerModel. It extends that interface while
 * providing new features to save and load portfolio objects.
 */
public interface PersistentPortfolioManagerModel extends IPortfolioManagerModel {

  /**
   * Saves a portfolio object by delegating the task to PersistPortfolioJSON.
   *
   * @param portfolioName the name of the portfolio.
   * @param persist       the object that is responsible for IO operation.
   * @throws IOException if input source throws error.
   */
  void savePortfolio(String portfolioName, PersistPortfolioJSON persist) throws IOException;

  /**
   * Loads a portfolio object from the disk.
   *
   * @param portfolioPath the path where the object resides in serialized form.
   * @param persist       the object responsible for persist operations.
   * @return A string denoting the portfolio unique name.
   */
  String loadPortfolio(String portfolioPath, PersistPortfolioJSON persist);
}
