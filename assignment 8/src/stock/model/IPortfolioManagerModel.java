package stock.model;

import java.time.LocalDateTime;

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
   * Add a stock to the portfolio. On invalid date, exception will be thrown.
   *
   * @param portfolio the name of the portfolio to which a stock is to be added.
   * @param ticker    the unique identifier symbol of the stock.
   * @param amount    the amount worth of shares to be bought.
   * @param date      the date on which the shares are to be bought.
   */
  void addToPortfolio(String portfolio, String ticker, double amount, LocalDateTime date);

  /**
   * Returns the contents of a portfolio as information of the stocks within the portfolio.
   *
   * @param name the name of the portfolio whose contents are required.
   * @return String representing the contents of the portfolio.
   */
  String getPortfolioContent(String name);

  /**
   * Returns the cost basis,i.e. the buying cost of the portfolio.
   *
   * @param portfolioName the name of the portfolio whose cost is to be determined.
   * @return the total cost it took to buy all the stocks in this portfolio.
   */
  double getPortfolioCostBasis(String portfolioName);

  /**
   * Returns the current standing value of the portfolio at the given datetime.
   *
   * @param portfolioName the name of the portfolio whose cost is to be determined.
   * @param date          the date on which the value of the portfolio is to be determined.
   * @return the total cost of the portfolio as it is on this given date.
   */
  double getPortfolioCurrentValue(String portfolioName, LocalDateTime date);
}
