package stock.model;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import stock.model.dao.DAOInterface;
import stock.model.dao.DAOcsv;

/**
 * The class that implements a IPortfolio. It holds information about the stock and their quantity.
 * Every portfolio is identified by a unique portfolio name. The String representation of a
 * portfolio contains the information of all the stocks in the portfolio.
 */
public class PortfolioImpl implements IPortfolio {
  private String portfolioName;
  private Map<IStock, Integer> mapOfStocks;

  /**
   * Constructs a portfolio object with the given portfolio name.
   *
   * @param portfolioName the unique name for the portfolio.
   */
  public PortfolioImpl(String portfolioName) {
    this.portfolioName = portfolioName;
    this.mapOfStocks = new LinkedHashMap<>();
  }

  @Override
  public void add(String tickerSymbol, Double amount, LocalDateTime date) {
    DAOInterface<IStock> stockDao = new DAOcsv<>();
    mapOfStocks.putAll(stockDao.buyStockFromMarket(tickerSymbol, amount, date));
  }

  @Override
  public String getPortfolioName() {
    return portfolioName;
  }

  @Override
  public Double getCostBasis() {
    return mapOfStocks.keySet().stream()
            .mapToDouble(s -> s.getCostBasisOfStock() * mapOfStocks.get(s)).sum();
  }

  @Override
  public Double getValueByDate(LocalDateTime date) {
    return mapOfStocks.keySet().stream().filter(s -> s.getDateOfPurchase().compareTo(date) <= 0)
            .mapToDouble(s -> s.getValueByDate(date) * mapOfStocks.get(s))
            .sum();
  }

  @Override
  public String toString() {
    StringBuilder toReturn = new StringBuilder();
    for (IStock stock : mapOfStocks.keySet()) {
      toReturn.append("Bought ").append(mapOfStocks.get(stock)).append(" stocks of ")
              .append(stock.toString()).append("\n");
    }
    return toReturn.toString();
  }
}
