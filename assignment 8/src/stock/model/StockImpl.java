package stock.model;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import stock.model.dao.DAOInterface;
import stock.model.dao.DAOcsv;

public class StockImpl implements IStock {
  private final String tickerSymbol;
  private final Double valueOfPurchasedStock;
  private final LocalDateTime dateOfPurchase;

  /**
   * This class represents a single stock. It holds its ticker symbol, value at which the stock was
   * purchased and the date and time on which it was purchased. It communicates with the data source
   * to fetch the value on specified date.
   *
   * @param tickerSymbol          is unique symbol given to each company for trading purposes.
   * @param valueOfPurchasedStock the amount of money spent to buy this stock.
   * @param dateOfPurchase        the date on which the stock was bought.
   */
  public StockImpl(String tickerSymbol, Double valueOfPurchasedStock, LocalDateTime
          dateOfPurchase) {
    this.tickerSymbol = tickerSymbol;
    this.valueOfPurchasedStock = valueOfPurchasedStock;
    this.dateOfPurchase = dateOfPurchase;
  }

  @Override
  public LocalDateTime getDateOfPurchase() {
    return dateOfPurchase;
  }

  @Override
  public Double getCostBasisOfStock() {
    DecimalFormat deciFormat = new DecimalFormat("0.00");
    return Double.parseDouble(deciFormat.format(valueOfPurchasedStock));

  }

  @Override
  public Double getValueByDate(LocalDateTime date) {
    DAOInterface stockDao = new DAOcsv();
    return stockDao.valueOfStockOnDate(tickerSymbol, date);
  }

  /**
   * Returns the information about the stock in the format - "{ticker Symbol} bought at ${value at
   * which stock was purchased} each on {date and time of the purchase}".
   *
   * @return String of the stock.
   */
  @Override
  public String toString() {
    return tickerSymbol + " at $" + valueOfPurchasedStock
            + " each on " + dateOfPurchase.toString();
  }
}

