package stock.model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * This interface describes a single portfolio and operations that can be performed by a portfolio.
 * A portfolio is a collection of shares of stocks, ETF, Mutual funds etc. that exist on the stock
 * market.
 */
public interface IPortfolio {

  /**
   * Add a stock to this portfolio with the given parameters. Stocks can only be added on weekdays
   * and working days between trading hours of 8:00am to 4:00pm EDT.
   *
   * @param tickerSymbol the identifier symbol of the stock.
   * @param amount       the amount of worth of stocks to be bought.
   * @param date         the date on which the stocks are to be bought.
   */
  void add(String tickerSymbol, double amount, LocalDateTime date, double commissionFee);

  /**
   * Returns the name of the portfolio.
   *
   * @return the name of the portfolio as string.
   */
  String getPortfolioName();

  /**
   * Returns the cost basis of the portfolio, i.e. the cost it took to buy all stocks in the
   * portfolio.
   *
   * @return the amount it took to buy the contents of the portfolio.
   */
  Double getCostBasis(LocalDateTime date);

  /**
   * Returns the sum of current value of all the stocks in the portfolio on the given date.
   *
   * @param date the date on which the value is to be determined.
   * @return the value of the portfolio as double.
   */
  Double getValueByDate(LocalDateTime date);

  /**
   * Returns a mapping of each stock and it's quantity held in the portfolio.
   * @param date the date on which the contents are required.
   * @return a map of stock symbol to quantity.
   */
  Map<String, Integer> getContentsOn(LocalDateTime date);

}