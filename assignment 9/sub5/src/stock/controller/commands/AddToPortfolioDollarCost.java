package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stock.StrategyFactory;
import stock.model.IPortfolioManagerModel;
import stock.model.Strategy;
import stock.view.IStockView;

/**
 * A class that implements Dollar Cost strategy command. It accepts one or many ticker symbols on
 * new line. Weights for each symbol can be specified equally by command 'e' or custom by 'c'.
 * It prompts the user to enter weights for a ticker symbol. User has to specify a start date
 * and optional end date, if no end date is preferred then input 'current' should be given.
 * Amount to be invested, commission and frequency of investment are required.
 */
public class AddToPortfolioDollarCost extends AbstractAddToPortfolioByStrategy implements
        AddToPortfolioByStrategy {

  private final IStockView view;
  private final IPortfolioManagerModel model;

  /**
   * Constructs an object of dollar cost command.
   * @param model model to which strategy is passed.
   * @param view the view to which model output will be given.
   */
  public AddToPortfolioDollarCost(IPortfolioManagerModel model, IStockView view) {
    this.model = model;
    this.view = view;
  }

  private List<String> getTickerSymbols() throws IOException {
    List<String> tickerSymbols = new ArrayList<>();
    while (true) {
      String ticker = view.read();
      if (ticker.equalsIgnoreCase("d")) {
        return tickerSymbols;
      }
      if (isValid(ticker)) {
        tickerSymbols.add(ticker);
      } else {
        view.write("Ticker symbol has to be alphabets only. Enter again.\n");
      }
    }
  }

  private String getWeightScheme() throws IOException {

    while (true) {
      String scheme = view.read();
      if (scheme.equalsIgnoreCase("e") || scheme.equalsIgnoreCase("c")) {
        return scheme;
      } else {
        view.write("Invalid input. Enter e for equal or c for custom.\n ");
      }
    }
  }

  private double getWeight() throws IOException {
    while (true) {
      String weightString = view.read();
      try {
        double weight = Double.parseDouble(weightString);
        if (weight > 0.0 && weight <= 100.00) {
          return weight;
        } else {
          view.write("Value must be between 1-100.\n");
        }
      } catch (NumberFormatException e) {
        view.write("Expecting decimal number. Found non decimal value. Enter again\n");
      }
    }
  }

  private List<Double> getCustomWeights(List<String> tickerSymbols) throws IOException {
    List<Double> weights = new ArrayList<>();
    view.write("Enter weight as percent value between 0-100 for each symbol.");
    double remaining = 100.00;
    double weight;
    for (String ticker : tickerSymbols) {
      view.write("Enter weights for:" + ticker + "\n");
      weight = getWeight();
      if (remaining - weight >= 0.0) {
        remaining -= weight;
        weights.add(weight);
      } else {
        view.write("Only allowed value for this weight is " + remaining + " hence taken " +
                "that" + "as the value.\n");
        weights.add(remaining);
      }
    }
    return weights;
  }

  private List<Double> getEqualWeights(List<String> tickerSymbols) {
    List<Double> weights = new ArrayList<>();
    double weight;
    weight = 100.0 / tickerSymbols.size();
    for (int i = 0; i < tickerSymbols.size(); i++) {
      weights.add(weight);
    }
    return weights;
  }

  private List<Double> getWeights(List<String> tickerSymbols, String weightScheme) throws
          IOException {
    List<Double> weights;
    switch (weightScheme) {
      case "e":
        weights = getEqualWeights(tickerSymbols);
        return weights;
      case "c":
        weights = getCustomWeights(tickerSymbols);
        return weights;
      default:
        throw new IllegalArgumentException("Invalid weight scheme symbol.\n");
    }
  }

  private Map<String, Double> getTickerWeightMapping(List<String> tickers, List<Double> weight) {
    Map<String, Double> tickerWeightMap = new HashMap<>();
    for (int i = 0; i < weight.size(); i++) {
      tickerWeightMap.put(tickers.get(i), weight.get(i));
    }
    return tickerWeightMap;
  }

  private LocalDateTime getEndDate(LocalDateTime startDate) throws IOException {
    view.write("Enter end date as asked, if no end date then enter 'current'.\n");
    LocalDateTime dateTime = DateHelper.getDateTime(view, false);
    while (dateTime.isBefore(startDate)) {
      view.write("End date cannot be before start date.\n");
      view.write("Enter end date as asked, if no end date then enter 'current'.\n");
      dateTime = DateHelper.getDateTime(view, false);
    }
    return dateTime;
  }

  @Override
  public void executeStrategyCommand(String portfolioName) throws IOException {

    view.write("To add via dollar cost stratgey,Start by entering ticker symbols on new " +
            "line. Enter d or D when done.\n");
    List<String> tickerSymbols = getTickerSymbols();
    view.write("Choose weighting scheme: Enter e for equal or c for custom.\n");
    String weightScheme = getWeightScheme();
    List<Double> weights = getWeights(tickerSymbols, weightScheme);
    Map<String, Double> tickerWeights = getTickerWeightMapping(tickerSymbols, weights);
    view.write("Enter start date as asked.\n");
    LocalDateTime startDate = DateHelper.getDateTime(view, false);
    LocalDateTime endDate = getEndDate(startDate);
    view.write("Enter the amount.\n");
    double amount = getAmount(view);
    view.write("Enter commission fees as absolute value.\n");
    double commission = getAmount(view);
    view.write("Enter the frequency of execution as number of days.\n");
    int frequency = (int) getAmount(view);
    Strategy dollarCostStrategy = StrategyFactory.getDollarCostStrategyObject(tickerWeights,
            startDate, endDate, frequency, amount, commission);
    model.addToPortfolio(portfolioName, dollarCostStrategy);
  }
}
