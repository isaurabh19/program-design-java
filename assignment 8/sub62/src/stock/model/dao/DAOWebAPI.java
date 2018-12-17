package stock.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;

import stock.model.IStock;

/**
 * The class extends the DAOAbstract which implements the DAOInterface. It fetches data from the
 * alphavantage API. Please look at documentation here: https://www.alphavantage.co/documentation/
 * for further details about API. This implementation maintains a local storage for ease of access.
 * When a query is to be made, the local storage is checked first. However, if the data is outdated,
 * a 'refresh' takes place which updates the data.
 */

public class DAOWebAPI extends DAOAbstract {
  @Override
  public Map<IStock, Integer> buyStockFromMarket(String tickerSymbol, Double
          amount, LocalDateTime dateOfPurchase) {
    updateCacheIfRequired(tickerSymbol, dateOfPurchase);
    return buyFromCSV(tickerSymbol, amount, dateOfPurchase);
  }

  private void updateCacheIfRequired(String tickerSymbol, LocalDateTime dateOfPurchase) {
    if (!isPresentInCacheMemory(tickerSymbol)) {
      storeFromAPIToMemory(tickerSymbol);
    } else {
      if (!isCacheUpdated(tickerSymbol, dateOfPurchase)) {
        try {
          Files.deleteIfExists(Paths.get("stockInfo/" + tickerSymbol + ".csv"));
          storeFromAPIToMemory(tickerSymbol);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private boolean isCacheUpdated(String tickerSymbol, LocalDateTime date) {
    try {
      Scanner scan = new Scanner(new File("stockInfo/" + tickerSymbol + ".csv"));
      scan.useDelimiter("\n");
      scan.next();
      String line = scan.next();
      String[] outputArr = line.split(",");
      String lastEntry = outputArr[0];
      LocalDate lastDate = LocalDate.parse(lastEntry);
      scan.close();
      return lastDate.compareTo(date.toLocalDate()) >= 0;
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Enter valid ticker symbol.\n");
    }
  }

  private void storeFromAPIToMemory(String tickerSymbol) {
    URL url = generateURL(tickerSymbol);
    createCSVFile(url, tickerSymbol);
  }

  private void createCSVFile(URL url, String tickerSymbol) {
    try {
      String outputFilePath = "stockInfo/" + tickerSymbol + ".csv";
      InputStream in = url.openStream();

      Files.copy(in, Paths.get(outputFilePath));

      new File(outputFilePath);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public double valueOfStockOnDate(String tickerSymbol, LocalDateTime date) {
    updateCacheIfRequired(tickerSymbol, date);
    DAOInterface csvdao = new DAOcsv<>();
    return csvdao.valueOfStockOnDate(tickerSymbol, date);
  }

  private boolean isPresentInCacheMemory(String tickerSymbol) {
    return new File("stockInfo/" + tickerSymbol + ".csv").exists();
  }

  private URL generateURL(String tickerSymbol) {
    String[] apiKeyArray = {"LR3R9I717PRYUY7M", "DFEAI2EQ5WWPNIZH", "CYQ57ZIMPZ867WKP"};

    int apiKeyNumber = 0;
    String apiKey = apiKeyArray[apiKeyNumber];

    try {
      return new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + tickerSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new IllegalStateException("Could not form the URL");
    }
  }

  private Map<IStock, Integer> buyFromCSV(String tickerSymbol, double amount, LocalDateTime
          dateOfPurchase) {
    DAOInterface csvdao = new DAOcsv<>();
    return csvdao.buyStockFromMarket(tickerSymbol, amount, dateOfPurchase);
  }
}
