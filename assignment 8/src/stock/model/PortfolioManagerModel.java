package stock.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class that implements the PortfolioManagerModel. It allows to create portfolios but ensures
 * uniqueness in the name of portfolios. An object of this class is represented in its string form
 * as the name of all portfolios in its collection.
 */
public class PortfolioManagerModel implements IPortfolioManagerModel {

  private List<IPortfolio> portfolios;

  /**
   * Constructs a portfolio manager object.
   */
  public PortfolioManagerModel() {
    this.portfolios = new ArrayList<>();
  }

  private IPortfolio getPortfolioObject(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Enter valid input.\n");
    }
    List<IPortfolio> result = portfolios.stream().filter(p -> p.getPortfolioName()
            .equals(name))
            .collect(Collectors.toList());
    if (!result.isEmpty()) {
      return result.get(0);
    }
    throw new IllegalArgumentException("IPortfolio name does not exist.\n");
  }

  @Override
  public void createPortfolio(String portfolioName) {
    if (portfolioName == null || portfolioName.isEmpty()) {
      throw new IllegalArgumentException("Enter valid input.\n");
    }
    List<IPortfolio> result = portfolios.stream().filter(p -> p.getPortfolioName()
            .equals(portfolioName))
            .collect(Collectors.toList());
    if (result.isEmpty()) {
      portfolios.add(new PortfolioImpl(portfolioName));
    } else {
      throw new IllegalArgumentException("IPortfolio exists. Enter a unique name.\n");
    }
  }

  @Override
  public void addToPortfolio(String portfolioName, String ticker, double amount,
                             LocalDateTime date) {
    if (portfolioName == null || ticker == null || date == null
            || portfolioName.isEmpty() || ticker.isEmpty()
            || amount <= 0) {
      throw new IllegalArgumentException("Enter valid input.");
    }
    IPortfolio portfolio = getPortfolioObject(portfolioName);
    portfolio.add(ticker, amount, date);
  }

  //  @Override
  //  public String getPortfolios(Predicate<IPortfolio> predicate) {
  //    List<String> result = portfolios.stream().filter(predicate).map(IPortfolio::
  // getPortfolioName).collect(Collectors.toList());
  //    return String.join("\n",result);
  //  }
  @Override
  public String getPortfolioContent(String name) {
    IPortfolio portfolio = getPortfolioObject(name);
    return portfolio.toString();
  }

  @Override
  public double getPortfolioCostBasis(String portfolioName) {
    return getPortfolioObject(portfolioName).getCostBasis();
  }

  @Override
  public double getPortfolioCurrentValue(String portfolioName, LocalDateTime date) {
    if (date == null) {
      throw new IllegalArgumentException("Enter valid date.\n");
    }
    return getPortfolioObject(portfolioName).getValueByDate(date);
  }

  @Override
  public String toString() {
    // + "\t" +((p.getValueByDate(LocalDateTime.now()) - p.getCostBasis()) / (p.getCostBasis()))
    // * 100)
    List<String> result = portfolios.stream().map(IPortfolio::getPortfolioName)
            .collect(Collectors.toList());

    return String.join("\n", result) + "\n";
  }
}
