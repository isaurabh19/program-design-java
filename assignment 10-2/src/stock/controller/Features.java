package stock.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An interface that represents various features of the program. Each method in this interface acts
 * like a callback for the view. The view pushes any data it receives through any of these methods.
 * These methods in turn call the corresponding model methods.
 */
public interface Features {


  /**
   * Creates a portfolio with the given name.
   *
   * @param portfolioName the unique name for the portfolio.
   */
  void createPortfolio(String portfolioName);

  /**
   * Creates a dollar cost strategy with all possible parameters present.
   *
   * @param strategyName  name of the strategy
   * @param tickerWeight  the weight ticker mapping for each symbol.
   * @param start         the start date to begin investment from.
   * @param end           the end date when the investment ends.
   * @param amount        the amount to be invested in each investment.
   * @param commission    the commission per transaction.
   * @param frequency     the intervals in which strategy is executed.
   * @param save          whether user wants to save this strategy.
   * @param apply         whether user wants to execute this strategy.
   * @param load          whether portfolio is loaded from disk
   * @param portfolioInfo the name or path of the portfolio.
   * @param savePort      whether portfolio is to be saved after execution
   */
  void createStrategyDollarCost(String strategyName, Map<String, Double> tickerWeight,
                                LocalDate start, LocalDate end,
                                double amount, double commission, int frequency, boolean save,
                                boolean apply, boolean load, String portfolioInfo,
                                boolean savePort);

  /**
   * Creates a dollar cost strategy with all equal weights for distribution.
   *
   * @param strategyName  name of the strategy
   * @param start         the start date to begin investment from.
   * @param end           the end date when the investment ends.
   * @param amount        the amount to be invested in each investment.
   * @param commission    the commission per transaction.
   * @param frequency     the intervals in which strategy is executed.
   * @param save          whether user wants to save this strategy.
   * @param apply         whether user wants to execute this strategy.
   * @param load          whether portfolio is loaded from disk
   * @param portfolioInfo the name or path of the portfolio.
   * @param savePort      whether portfolio is to be saved after execution
   */
  void createStrategyDollarCost(String strategyName, List<String> tickerSymbols,
                                LocalDate start, LocalDate end, double amount,
                                double commission, int frequency, boolean save, boolean apply,
                                boolean load, String portfolioInfo, boolean savePort);

  /**
   * Creates a dollar cost strategy with no end date passed.
   *
   * @param strategyName  name of the strategy
   * @param tickerWeight  the weight ticker mapping for each symbol.
   * @param startDate     the start date to begin investment from.
   * @param amount        the amount to be invested in each investment.
   * @param commission    the commission per transaction.
   * @param freqDays      the intervals in which strategy is executed.
   * @param save          whether user wants to save this strategy.
   * @param apply         whether user wants to execute this strategy.
   * @param load          whether portfolio is loaded from disk
   * @param portfolioInfo the name or path of the portfolio.
   * @param savePort      whether portfolio is to be saved after execution
   */
  void createStrategyDollarCost(String strategyName, Map<String, Double> tickerWeight,
                                LocalDate startDate, double amount, double commission,
                                int freqDays, boolean save, boolean apply,
                                boolean load, String portfolioInfo, boolean savePort);

  /**
   * Creates a dollar cost strategy with equal weights and no end date.
   *
   * @param strategyName  name of the strategy
   * @param startDate     the start date to begin investment from.
   * @param amount        the amount to be invested in each investment.
   * @param commission    the commission per transaction.
   * @param freqDays      the intervals in which strategy is executed.
   * @param save          whether user wants to save this strategy.
   * @param apply         whether user wants to execute this strategy.
   * @param load          whether portfolio is loaded from disk
   * @param portfolioInfo the name or path of the portfolio.
   * @param savePort      whether portfolio is to be saved after execution
   */
  void createStrategyDollarCost(String strategyName, List<String> tickerList,
                                LocalDate startDate, double amount, double commission, int freqDays,
                                boolean save, boolean apply, boolean load, String portfolioInfo,
                                boolean savePort);

  /**
   * Creates a regular buying strategy for the portfolio.
   *
   * @param tickerSymbol  the symbol of the stock
   * @param amount        the amount worth of stocks to be bought.
   * @param commission    the commission per transaction
   * @param dateTime      datetime at which it is bought.
   * @param save          whether user wants to save this strategy.
   * @param apply         whether user wants to execute this strategy.
   * @param load          whether portfolio is loaded from disk
   * @param portfolioInfo the name or path of the portfolio.
   * @param strategyName  unique name of the strategy.
   * @param savePort      whether portfolio is to be saved after execution
   */
  void createStrategyRegular(String tickerSymbol, double amount, double commission,
                             LocalDate dateTime, boolean save, boolean apply, boolean load,
                             String portfolioInfo, String strategyName, boolean savePort);

  /**
   * Returns the cost basis of a portfolio on a given date.
   * @param loadPortfolio whether portfolio is loaded from the disk.
   * @param portfolioNamePath the path or name of the portfolio.
   * @param dateTime the date time at which cost basis is requested.
   * @return Optional as double if value is present, empty otherwise.
   */
  Optional<Double> getCostBasis(boolean loadPortfolio, String portfolioNamePath,
                                LocalDate dateTime);

  /**
   * Returns the current value of a portfolio on a given date.
   * @param loadPortfolio whether portfolio is loaded from the disk.
   * @param portfolioNamePath the path or name of the portfolio.
   * @param dateTime the date time at which current value is requested.
   * @return Optional as double if value is present, empty otherwise.
   */
  Optional<Double> getCurrentValue(boolean loadPortfolio, String portfolioNamePath,
                                   LocalDate dateTime);

  /**
   * Buys a stock or stocks based on the strategy passed into the given portfolio.
   * @param loadPortfolio whether portfolio is loaded from the disk.
   * @param portfolioNamePath the path or name of portfolio.
   * @param strategyPath the path of strategy file.
   * @param shouldPort whether to save portfolio after the transaction.
   */
  void addStock(boolean loadPortfolio, String portfolioNamePath, String strategyPath,
                boolean shouldPort);

  /**
   * Get the cost basis and current value data points for given range of date.
   * @param load whether portfolio is loaded from disk.
   * @param portfolioName the name of the portfolio.
   * @param start the start date for the graph.
   * @param end the end date for the graph.
   * @return A bean class of cost basis and current value maps.
   */
  CostValues getGraphData(boolean load, String portfolioName, LocalDateTime start,
                          LocalDateTime end);
}

