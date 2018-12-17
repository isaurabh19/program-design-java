package stock.model;

import java.time.LocalDateTime;

/**
 * A Strategy class that implements a regular investment strategy of buying one stock at a given
 * amount on a given day. It takes a commission parameter which will be factored in the cost basis
 * of the stock. Allows to add stocks only on business day and time.
 */
public class RegularStrategy implements Strategy {
  private final String ticker;
  private final double amount;
  private final double commission;
  private final LocalDateTime dateTime;
  private final String strategyName;
  private final String TYPE = "Regular";

  /**
   * Constructs a new regular strategy object with the given parameters.
   *
   * @param ticker     the symbol of the stock.
   * @param amount     the amount worth of stocks to be bought.
   * @param commission the commission fee applied for this transaction.
   * @param dateTime   the date and time at which the stock is to be bought.
   */
  public RegularStrategy(String ticker, double amount, double commission, LocalDateTime dateTime,
                         String strategyName) {
    this.ticker = ticker;
    this.amount = amount;
    this.dateTime = dateTime;
    this.commission = commission;
    this.strategyName = strategyName;
  }

  @Override
  public void execute(IPortfolio portfolio) {
    portfolio.add(ticker, amount, dateTime, commission);
  }

  @Override
  public String getType() {
    return TYPE;
  }

  @Override
  public String toString() {
    return strategyName;
  }
}
