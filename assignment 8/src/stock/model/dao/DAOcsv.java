package stock.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import stock.model.IStock;
import stock.model.StockImpl;

/**
 * This class is used to fetch data from csv files. It uses the closing time value to buy the
 * stocks. The amount is assumed to be in USD. This data was last refreshed on 15th Nov 2018 at
 * 3:55pm EST. Hence data after the fetched data will result in IllegalStateException.
 */
public class DAOcsv<T> extends DAOAbstract {

  /**
   * Fetches the closing value of the stock on the specified date in the required format (specified
   * in the interface).
   *
   * @param tickerSymbol is unique symbol given to each company for trading purposes.
   * @param date         is the date at which the value is to be fetched.
   * @return value of the stock on the date.
   * @throws IllegalArgumentException when the CSV file was not present.
   * @throws IllegalStateException    when the date is not present in the data.
   */
  @Override
  public Double valueOfStockOnDate(String tickerSymbol, LocalDateTime date) {
    String[] stockData = fetchStockData(tickerSymbol, date);
    String strClosingAmt = stockData[4];
    return Double.parseDouble(strClosingAmt);
  }

  /**
   * Used to buy the stocks from the market on specified date for the amount.
   *
   * @param tickerSymbol   is unique symbol given to each company for trading purposes.
   * @param amount         the amount of money spent to buy this stock.
   * @param dateOfPurchase is the date on which the value was bought.
   * @return a map of StockInterface and Integer where Integer represents the number of stocks
   *         bought for the specified value.
   * @throws IllegalArgumentException when the CSV file was not present.
   * @throws IllegalStateException    when the date is not present in the data.
   */
  @Override
  public Map<IStock, Integer> buyStockFromMarket(String tickerSymbol, Double amount,
                                                 LocalDateTime dateOfPurchase) {
    Double closingAmount = valueOfStockOnDate(tickerSymbol, dateOfPurchase);
    Map<IStock, Integer> tempMap = new LinkedHashMap<>();
    tempMap.put(new StockImpl(tickerSymbol, closingAmount, dateOfPurchase),
            numberOfStocksBought(closingAmount, amount));
    return tempMap;
  }

  private String[] fetchStockData(String tickerSymbol, LocalDateTime date) {
    String strDateOfPurchase = getDateInFormat(date);
    try {
      Scanner scan = new Scanner(new File("stockInfo/" + tickerSymbol + ".csv"));
      scan.useDelimiter("\n");
      while (scan.hasNext()) {
        String line = scan.next();
        if (line.contains(strDateOfPurchase)) {
          return line.split(",");
        }
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Enter valid ticker symbol.\n");
    }
    throw new IllegalStateException("Enter valid date.\n");
  }
}
