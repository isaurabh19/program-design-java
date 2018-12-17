package stock.model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import stock.model.IStock;
import stock.model.StockImpl;

/**
 * The class extends the DAOAbstract which implements the DAOInterface. It fetches data from the
 * alphavantage API. Please look at documentation here: https://www.alphavantage.co/documentation/
 * for further details about API.
 */
public class DAOWebAPI extends DAOAbstract {
  private static int numberOfCalls = 0;

  /**
   * Fetches the closing value of the stock on the specified date in the required format (
   * specified in the interface).
   *
   * @param tickerSymbol is unique symbol given to each company for trading purposes.
   * @param date         is the date at which the value is to be fetched.
   * @return value of the stock on the date.
   * @throws IllegalArgumentException when the URL could not be formed, or error in outputStream.
   * @throws IllegalStateException    when the call to API fetches unexpected data.
   */
  @Override
  public Double valueOfStockOnDate(String tickerSymbol, LocalDateTime date) {
    URL url = generateURL(tickerSymbol);
    String[] stockData = fetchStockData(url, date);
    String close = stockData[4];
    return Double.parseDouble(close);
  }

  /**
   * Used to buy the stocks from the market on specified date for the amount.
   *
   * @param tickerSymbol   is unique symbol given to each company for trading purposes.
   * @param amount         the amount of money spent to buy this stock.
   * @param dateOfPurchase is the date on which the value was bought.
   * @return a map of IStock and Integer where Integer represents the number of stocks
   *         bought for the specified value.
   * @throws IllegalArgumentException when the URL could not be formed, or error in outputStream.
   * @throws IllegalStateException    when the call to API fetches unexpected data.
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

  private String[] fetchStockData(URL url, LocalDateTime dateOfPurchase) {
    InputStream in;
    InputStream inCopy;
    String strDateOfPurchase = getDateInFormat(dateOfPurchase);
    try {

      in = url.openStream();
      inCopy = url.openStream();
      numberOfCalls++;
      Scanner scan = new Scanner(in);
      scan.useDelimiter("\n");
      while (scan.hasNext()) {
        String line = scan.next();
        if (line.contains(strDateOfPurchase)) {
          return line.split(",");
        }
      }

      String exceptionMessage = generateException(inCopy);
      throw new IllegalStateException("Exception: " + exceptionMessage);
    } catch (IOException e) {
      throw new IllegalArgumentException("Check output stream.");
    }
  }

  private String generateException(InputStream in) throws IOException {
    StringBuilder output = new StringBuilder();
    int b;

    while ((b = in.read()) != -1) {
      output.append((char) b);
    }
    if (output.toString().contains("Our standard API call frequency is "
            + "5 calls per minute and 500 calls per day.")) {
      return "Kindly wait since the API does not support multiple requests. You have requested "
              + numberOfCalls + " times.";
    }
    return output.toString();
  }


  private URL generateURL(String tickerSymbol) {
    String[] apiKeyArray = {"LR3R9I717PRYUY7M", "DFEAI2EQ5WWPNIZH", "CYQ57ZIMPZ867WKP",
      "NR9YH165UH9NKPDQ", "PIHF7V8BX7PP6DVA", "781F7IF6BHECF4YA"};
    Random rand = new Random();
    int randomInt = rand.nextInt(5000);
    int apiKeyNumber = randomInt % 6;
    String apiKey = apiKeyArray[apiKeyNumber];
    try {
      return new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + tickerSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("Could not form the URL");
    }
  }
}

