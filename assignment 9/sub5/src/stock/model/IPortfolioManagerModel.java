package stock.model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * This interface represents the model of this program. A IPortfolioManagerModel is a collection of
 * portfolios of a user. It defines operations to create, add and view the contents of the
 * portfolio.
 */
public interface IPortfolioManagerModel {

  /**
   * Creates an empty portfolio with the given name and adds it to its collection. A portfolio name
   * has to be unique hence duplicate names will result in error.
   *
   * @param portfolioName the unique name for the portfolio.
   */
  void createPortfolio(String portfolioName);

  /**
   * Applies a strategy of adding stocks to this portfolio.
   * @param portfolio the portfolio name on which the strategy will be applied.
   * @param strategy the strategy object that defines how stocks will be added.
   */
  void addToPortfolio(String portfolio, Strategy strategy);


  /**
   * Returns the cost basis,i.e. the buying cost of the portfolio.
   *
   * @param portfolioName the name of the portfolio whose cost is to be determined.
   * @return the total cost it took to buy all the stocks in this portfolio.
   */
  double getPortfolioCostBasis(String portfolioName, LocalDateTime date);

  /**
   * Returns the current standing value of the portfolio at the given datetime.
   *
   * @param portfolioName the name of the portfolio whose cost is to be determined.
   * @param date          the date on which the value of the portfolio is to be determined.
   * @return the total cost of the portfolio as it is on this given date.
   */
  double getPortfolioCurrentValue(String portfolioName, LocalDateTime date);

  /**
   * Returns the contents of a portfolio as information of the stocks within the portfolio.
   *
   * @param name the name of the portfolio whose contents are required.
   * @param date the date at which the contents are required.
   * @return a map with key as ticker symbol and value as quantity of stocks.
   */
  Map<String, Integer> getContentsOn(String name, LocalDateTime date);
}
