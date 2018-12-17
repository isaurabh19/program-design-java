package stock.model;

import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Properties;

import stock.model.dao.DAOFactory;
import stock.model.dao.DAOInterface;

public class StockImpl implements IStock {
  @Expose
  private final String tickerSymbol;
  @Expose
  private final Double valueOfPurchasedStock;
  @Expose
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
  public StockImpl(String tickerSymbol, Double valueOfPurchasedStock,
                   LocalDateTime dateOfPurchase) {
    this.tickerSymbol = tickerSymbol;
    this.valueOfPurchasedStock = valueOfPurchasedStock;
    this.dateOfPurchase = dateOfPurchase;
  }

  private StockImpl() {
    this(null, null, null);
  }

  @Override
  public LocalDateTime getDateOfPurchase() {
    return dateOfPurchase;
  }

  @Override
  public String getTickerSymbol() {
    return tickerSymbol;
  }

  @Override
  public Double getCostBasisOfStock() {
    DecimalFormat deciFormat = new DecimalFormat("0.00");
    return Double.parseDouble(deciFormat.format(valueOfPurchasedStock));
  }

  @Override
  public Double getValueByDate(LocalDateTime date) {
    DAOInterface stockDao;
    File file = new File("config.properties");
    try {
      InputStream input = new FileInputStream(file.getAbsolutePath());
      Properties properties = new Properties();
      properties.load(input);
      String source = properties.getProperty("source");
      stockDao = DAOFactory.getDAOSource(source);//new DAOcsv<>();
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Data source config file not found.\n");
    } catch (IOException e) {
      throw new IllegalStateException("IO error.\n");
    }
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
    return tickerSymbol + "@" + valueOfPurchasedStock + "on"
            + dateOfPurchase.toLocalDate().toString();
  }
}