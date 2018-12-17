package stock.model;

import java.time.LocalDateTime;

/**
 * This interface represents a single stock. It is a share in the company which is bought by the
 * investor by investing a particular amount in return for share in the company holding. A stock of
 * a company is simply a part of ownership in that company. Ownership is divided into shares, where
 * a share represents a fraction of the total ownership. An investor sends money to the company to
 * buy some of its stock, and gets part ownership in return.
 */
public interface IStock {
  /**
   * The total money invested in the stock (money spent buying it) is called the cost basis of the
   * purchase in format $x.yz, ie, 2 digits after decimal.
   *
   * @return the amount invested in the stock.
   */
  Double getCostBasisOfStock();

  /**
   * Returns the amount of money (that was invested) has grown (or shrunk) into as of the specified
   * date. It is the money that would be obtained if the stock is sold at particular date in format
   * $x.yz, ie, 2 digits after decimal from a data source.
   *
   * @param date at which the value of the stock is requested.
   * @return the amount of money received if the stock is sold.
   */
  Double getValueByDate(LocalDateTime date);

  /**
   * Returns the date of the purchase in the LocalDateTime format of YYYY-MM-DDTHH:MM:SS.
   *
   * @return the date on which the stock was purchased.
   */
  LocalDateTime getDateOfPurchase();

}
