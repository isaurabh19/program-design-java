package stock.model;

import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import stock.model.dao.DAOFactory;
import stock.model.dao.DAOInterface;

/**
 * The class that implements a Portfolio. It holds information about the stock and their quantity.
 * Every portfolio is identified by a unique portfolio name. The String representation of a
 * portfolio contains the information of all the stocks in the portfolio.
 */
public class PortfolioImpl implements IPortfolio {
  @Expose
  private final String portfolioName;
  //private final DAOInterface stockDao;
  @Expose
  private Map<IStock, double[]> mapOfStocks;

  /**
   * Constructs a portfolio object with the given portfolio name.
   *
   * @param portfolioName the unique name for the portfolio.
   */
  public PortfolioImpl(String portfolioName) {
    this.portfolioName = portfolioName;
    this.mapOfStocks = new LinkedHashMap<>();
    //System.out.println(System.getProperty("user.dir"));
    File file = new File("config.properties");
    try {
      InputStream input = new FileInputStream(file.getAbsolutePath());
      Properties properties = new Properties();
      properties.load(input);
      String source = properties.getProperty("source");
      DAOInterface stockDao = DAOFactory.getDAOSource(source);//new DAOcsv<>();
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Data source config file not found.\n");
    } catch (IOException e) {
      throw new IllegalStateException("IO error.\n");
    }

  }

  private DAOInterface getStockDao() {
    File file = new File("config.properties");
    try {
      InputStream input = new FileInputStream(file.getAbsolutePath());
      Properties properties = new Properties();
      properties.load(input);
      String source = properties.getProperty("source");
      DAOInterface stockDao = DAOFactory.getDAOSource(source);//new DAOcsv<>();
      return stockDao;
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Data source config file not found.\n");
    } catch (IOException e) {
      throw new IllegalStateException("IO error.\n");
    }
  }

  @Override
  public Map<String, Integer> getContentsOn(LocalDateTime date) {

    return mapOfStocks.entrySet().stream()
            .filter(mapEntry -> mapEntry.getKey().getDateOfPurchase().compareTo(date) < 0)
            .collect(Collectors.toMap(x -> x.getKey().getTickerSymbol(),
              x -> (int) x.getValue()[0],
              (x, y) -> x + y, LinkedHashMap::new));
  }

  @Override
  public void add(String tickerSymbol, double amount, LocalDateTime date, double commissionFee) {
    DAOInterface stockDao = getStockDao();
    try {
      Map.Entry<IStock, Integer> tempMap = stockDao
              .buyStockFromMarket(tickerSymbol, amount - commissionFee, date).entrySet()
              .iterator().next();
      double[] tempArr = new double[2];
      tempArr[0] = tempMap.getValue();
      tempArr[1] = commissionFee;
      //todo check if symbol exists.
      mapOfStocks.put(tempMap.getKey(), tempArr);
    } catch (Exception e) {
      throw new IllegalArgumentException("Ticker symbol doesn't exist.\n");
    }
  }

  @Override
  public String getPortfolioName() {
    return portfolioName;
  }

  @Override
  public Double getCostBasis(LocalDateTime date) {
    return mapOfStocks.keySet().stream().filter(s -> s.getDateOfPurchase().compareTo(date) <= 0)
            .mapToDouble(s -> s.getCostBasisOfStock() * mapOfStocks.get(s)[0]).sum();
  }

  @Override
  public Double getValueByDate(LocalDateTime date) {
    return mapOfStocks.keySet().stream().filter(s -> s.getDateOfPurchase().compareTo(date) <= 0)
            .mapToDouble(s -> s.getValueByDate(date) * mapOfStocks.get(s)[0])
            .sum();
  }

  @Override
  public String toString() {
    return this.portfolioName;
  }
}