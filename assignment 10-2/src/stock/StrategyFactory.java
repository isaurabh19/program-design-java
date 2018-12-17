package stock;

import java.time.LocalDateTime;
import java.util.Map;

import stock.model.DollarCostStrategy;
import stock.model.RegularStrategy;
import stock.model.Strategy;

/**
 * A factory that creates object of Strategy interface. Each method in this factory is responsible
 * for a single strategy class only.
 */
public class StrategyFactory {

  /**
   * Returns an object of RegularStrategy with the given parameters.
   *
   * @param tickerSymbol the ticker symbol of the stock.
   * @param amount       the amount worth of stock to be bought.
   * @param commission   the commission to be applied.
   * @param dateTime     the date and time of purchase.
   * @return an object of RegularStrategy.
   */
  public static Strategy getRegularStrategyObject(String tickerSymbol, double amount,
                                                  double commission, LocalDateTime dateTime,
                                                  String strategyName) {
    return new RegularStrategy(tickerSymbol, amount, commission, dateTime, strategyName);
  }

  /**
   * Returns an object of DollarCostStrategy.
   *
   * @param tickerWeightMap mapping of ticker symbol and its weight.
   * @param startDate       the date from which investment commences.
   * @param endDate         the date on which investment ceases.
   * @param frequency       the span of days between two investments.
   * @param amount          the amount for each investment.
   * @param commission      the commission for each transaction
   * @return an object of DollarCostStrategy.
   */
  public static Strategy getDollarCostStrategyObject(Map<String, Double> tickerWeightMap,
                                                     LocalDateTime startDate,
                                                     LocalDateTime endDate, int frequency,
                                                     double amount, double commission,
                                                     String strategyName) {
    return new DollarCostStrategy(tickerWeightMap, amount, startDate, endDate, frequency,
            commission, strategyName);
  }

}
