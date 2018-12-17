package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import stock.model.DollarCostStrategy;
import stock.model.PersistentPortfolioManagerModel;
import stock.model.Strategy;

/**
 * A class that implements Dollar Cost strategy command. It accepts one or many ticker symbols on
 * new line. Weights for each symbol can be specified equally by command 'e' or custom by 'c'. It
 * prompts the user to enter weights for a ticker symbol. User has to specify a start date and
 * optional end date, if no end date is preferred then input 'current' should be given. Amount to be
 * invested, commission and frequency of investment are required.
 */
public class CreateStrategyDollarCost extends AbstractCreateByStrategy implements
        CreateStrategyType {

  private final Supplier<String> reader;
  private final Consumer<String> writer;
  private final Consumer<String> notify;
  private final PersistentPortfolioManagerModel model;

  /**
   * Constructs an object of dollar cost command.
   * @param model model to which the strategy object is passed.
   * @param reader the Function object that provides data required.
   * @param writer the function object to prompt messages on the view for asking user input.
   * @param notify notifies the view about feedback of their action or unusual events.
   */
  public CreateStrategyDollarCost(PersistentPortfolioManagerModel model, Supplier<String> reader,
                                  Consumer<String> writer,Consumer<String> notify) {
    this.model = model;
    this.reader = reader;
    this.writer = writer;
    this.notify = notify;
  }

  private List<String> getTickerSymbols() throws IOException {
    writer.accept("To create dollar cost strategy,Start by entering ticker symbols on new " +
            "line. Enter d or D when done.\n");
    List<String> tickerSymbols = new ArrayList<>();
    while (true) {
      String ticker = reader.get();
      if (ticker.equalsIgnoreCase("d")) {
        return tickerSymbols;
      }
      if (isValid(ticker)) {
        tickerSymbols.add(ticker);
      } else {
        notify.accept("Ticker symbol has to be alphabets only. Enter again.\n");
      }
    }
  }

  private String getWeightScheme() throws IOException {
    writer.accept("Choose weighting scheme: Enter e for equal or c for custom.\n");
    while (true) {
      String scheme = reader.get();
      if (scheme.equalsIgnoreCase("e") || scheme.equalsIgnoreCase("c")) {
        return scheme;
      } else {
        notify.accept("Invalid input. Enter e for equal or c for custom.\n ");
      }
    }
  }

  private double getWeight() throws IOException {
    while (true) {
      String weightString = reader.get();
      try {
        double weight = Double.parseDouble(weightString);
        if (weight > 0.0 && weight <= 100.00) {
          return weight;
        } else {
          notify.accept("Value must be between 1-100.\n");
        }
      } catch (NumberFormatException e) {
        notify.accept("Expecting decimal number. Found non decimal value. Enter again\n");
      }
    }
  }

  private List<Double> getCustomWeights(List<String> tickerSymbols) throws IOException {
    List<Double> weights = new ArrayList<>();
    writer.accept("Enter weight as percent value between 0-100 for each symbol.");
    double remaining = 100.00;
    double weight;
    for (String ticker : tickerSymbols) {
      writer.accept("Enter weights for:" + ticker + "\n");
      weight = getWeight();
      if (remaining - weight >= 0.0) {
        remaining -= weight;
        weights.add(weight);
      } else {
        notify.accept("Only allowed value for this weight is " + remaining + " hence taken " +
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
    writer.accept("Enter end date as asked, if no end date then enter 'current'.\n");
    LocalDateTime dateTime = DateHelper.getDateTime(reader, writer,notify, false);
    while (dateTime != null && dateTime.isBefore(startDate)) {
      notify.accept("End date cannot be before start date.\n");
      writer.accept("Enter end date as asked, if no end date then enter 'current'.\n");
      dateTime = DateHelper.getDateTime(reader, writer, notify,false);
    }
    return dateTime;
  }


  @Override
  public void createStrategyType(String strategyName) throws IOException {
    List<String> tickerSymbols = getTickerSymbols();
    String weightScheme = getWeightScheme();
    List<Double> weights = getWeights(tickerSymbols, weightScheme);
    Map<String, Double> tickerWeights = getTickerWeightMapping(tickerSymbols, weights);
    writer.accept("Enter start date as asked.\n");
    LocalDateTime startDate = DateHelper.getDateTime(reader, writer, notify,false);
    LocalDateTime endDate = getEndDate(startDate);
    writer.accept("Enter the amount.\n");
    double amount = getAmount(reader, notify, d -> (d > 0.0));
    writer.accept("Enter commission fees as absolute value.\n");
    double commission = getAmount(reader, notify, d -> (d >= 0.0));
    writer.accept("Enter the frequency of execution as number of days.\n");
    int frequency = (int) getAmount(reader, notify, d -> (d > 0.0));
    Strategy dollarCostStrategy;
    if (endDate == null) {
      dollarCostStrategy = new DollarCostStrategy(tickerWeights, amount, startDate, frequency,
              commission, strategyName);
    } else {
      dollarCostStrategy = new DollarCostStrategy(tickerWeights, amount, startDate, endDate,
              frequency, commission, strategyName);
    }
    persistStrategy(dollarCostStrategy, reader, writer);
    applyStrategy(dollarCostStrategy, reader, writer, model);
    //model.addStrategy(dollarCostStrategy);
  }

}
