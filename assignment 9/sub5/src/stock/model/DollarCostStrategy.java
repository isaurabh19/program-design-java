package stock.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A Strategy class that implements dollar cost averaging. For each ticker symbol, adds one stock
 * like a regular strategy by the each date as per the frequency. Accepts a non zero commission for
 * executing the transaction. If a day of buying stock falls on a holiday, the next working day is
 * chosen to buy the stock. All stocks are bought on end of day prices. The weights should sum up to
 * 100, otherwise chooses the remaining weight from 100 as the weight for final stock. If the funds
 * are insufficient, throws an error.
 */
public class DollarCostStrategy implements Strategy {
  private final Map<String, Double> tickerAndWeight;
  private final double amount;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final int intervalInDays;
  private final double commissionFee;

  /**
   * Constructs a new Dollar cost object with given parameters.
   *
   * @param tickerAndWeight a map with key as ticker symbol and value as weight in double.
   * @param amount          the amount of investment.
   * @param startDate       the start date from which investing is to be initiated.
   * @param endDate         the date at which the investment will cease to execute further.
   * @param intervalInDays  span of days after each a new investment will be made.
   * @param commissionFee   the commission fee for each transaction.
   */
  public DollarCostStrategy(Map<String, Double> tickerAndWeight, double amount,
                            LocalDateTime startDate, LocalDateTime endDate, int intervalInDays,
                            double commissionFee) {
    if (!isValidConstructorValuesPassed(tickerAndWeight, amount, startDate, endDate, intervalInDays,
            commissionFee)) {
      throw new IllegalArgumentException("Enter valid input to the constructor.\n");
    }
    this.tickerAndWeight = tickerAndWeight;
    this.amount = amount;
    this.startDate = startDate;
    this.endDate = endDate;
    this.intervalInDays = intervalInDays;
    this.commissionFee = commissionFee;
  }

  private boolean isValidConstructorValuesPassed(Map<String, Double> tickerAndWeight,
                                                 double amount, LocalDateTime startDate,
                                                 LocalDateTime endDate, int intervalInDays,
                                                 double commissionFee) {
    return tickerAndWeight != null
            && (amount >= 0)
            && startDate != null
            && endDate != null
            && intervalInDays >= 0
            && (commissionFee >= 0)
            && (amount - commissionFee >= 0)
            && !isWeightExceedingLimit(tickerAndWeight);
  }

  private boolean isWeightExceedingLimit(Map<String, Double> tickerAndWeight) {
    return tickerAndWeight.values().stream().mapToDouble(s -> s).sum() != 100;
  }

  @Override
  public void execute(IPortfolio portfolio) {
    List<LocalDateTime> listOfDates = getListOfDatesToBuyOn(startDate, endDate, intervalInDays);
    double commissionFeePerStock = commissionFee / tickerAndWeight.size();
    for (LocalDateTime purchaseDate : listOfDates) {
      for (String tickerSymbol : tickerAndWeight.keySet()) {
        while (true) {
          try {
            portfolio.add(tickerSymbol, amount * (tickerAndWeight.get(tickerSymbol) / 100),
                    purchaseDate, commissionFeePerStock);
            break;
          } catch (IllegalArgumentException e) {
            purchaseDate = purchaseDate.plusDays(1);
          }
        }
      }
    }
  }


  private List<LocalDateTime> getListOfDatesToBuyOn(LocalDateTime startDate, LocalDateTime endDate,
                                                    int intervalInDays) {
    List<LocalDateTime> returnList = new ArrayList<>();
    LocalDateTime presentDate = startDate;
    while (presentDate.compareTo(endDate) <= 0) {
      returnList.add(presentDate);
      presentDate = presentDate.plusDays(intervalInDays);
    }
    return returnList;
  }
}
