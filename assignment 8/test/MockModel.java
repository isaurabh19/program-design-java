import java.time.LocalDateTime;

import stock.model.IPortfolioManagerModel;

//todo quit in the beginning, middle, end.
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
  public void addToPortfolio(String portfolio, String ticker, double amount, LocalDateTime date) {
    log.append("Add to portfolio: ").append(portfolio).append(", Ticker: ").append(ticker)
            .append(", with $").append(amount).append(" on ").append(date).append("\n");
  }

  @Override
  public String getPortfolioContent(String name) {
    log.append("Content of ").append(name).append("\n");
    return "Return portfolio content.\n";
  }

  @Override
  public double getPortfolioCostBasis(String portfolioName) {
    log.append("Cost basis of ").append(portfolioName).append("\n");
    return uniqueCode;
  }

  @Override
  public double getPortfolioCurrentValue(String portfolioName, LocalDateTime date) {
    log.append("Current value of ").append(portfolioName).append(" on ").append(date).append("\n");
    return uniqueCode;
  }

  @Override
  public String toString() {
    log.append("Called toString \n");
    return "Called toString";
  }
}
