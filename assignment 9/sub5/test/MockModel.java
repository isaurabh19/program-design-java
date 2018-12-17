import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import stock.model.IPortfolioManagerModel;
import stock.model.Strategy;

public class MockModel implements IPortfolioManagerModel {
  private StringBuilder log;
  private final double uniqueCode;


  public MockModel(StringBuilder log, double uniqueCode) {
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void createPortfolio(String portfolioName) {
    log.append("Create portfolio: ").append(portfolioName).append("\n");
  }

  @Override
  public void addToPortfolio(String portfolio, Strategy strategy) {
    log.append("In addToPortfolio, " + "portfolio: ").
            append(portfolio).append(", Strategy: ").append(strategy);
  }


  @Override
  public double getPortfolioCostBasis(String portfolioName, LocalDateTime date) {
    log.append("Cost basis of ").append(portfolioName).append(" on ").append(date.toString())
            .append("\n");
    return uniqueCode;
  }

  @Override
  public double getPortfolioCurrentValue(String portfolioName, LocalDateTime date) {
    log.append("Current value of ").append(portfolioName).append(" on ").append(date).append("\n");
    return uniqueCode;
  }

  @Override
  public Map<String, Integer> getContentsOn(String name, LocalDateTime date) {
    log.append("Get contents of ").append(name).append(" on ").append(date);
    Map<String, Integer> toReturn = new LinkedHashMap<>();
    toReturn.put(name, 12);
    return toReturn;
  }

  @Override
  public String toString() {
    log.append("Called toString \n");
    return "Called toString";
  }
}
