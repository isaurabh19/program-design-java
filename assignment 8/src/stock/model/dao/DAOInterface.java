package stock.model.dao;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * This generic interface represents the Data Access Objects. It fetches the information from the
 * specified data source. It defines operations to buy stock from market and to view its value on
 * specified date. It can access various sources and return the required information.
 *
 * @param <T> is the type of object to be returned.
 */
public interface DAOInterface<T> {
  /**
   * This method is used to buy the stock from the specified stock market.
   *
   * @param tickerSymbol is unique symbol given to each company for trading purposes.
   * @param amount       the amount of money spent to buy this stock.
   * @param <T>          the information to be returned in the form of object.
   * @return Map of T and the number of T bought for the specified amount.
   */
  <T> Map<T, Integer> buyStockFromMarket(String tickerSymbol, Double amount,
                                         LocalDateTime dateOfPurchase);

  /**
   * Fetches the value of the stock on specified date in the format of 2 digits after decimal,
   * $x.yz.
   *
   * @param tickerSymbol is unique symbol given to each company for trading purposes.
   * @param date         is the date at which the value is to be fetched.
   * @return the amount of the stock at specified date.
   */
  Double valueOfStockOnDate(String tickerSymbol, LocalDateTime date);
}
