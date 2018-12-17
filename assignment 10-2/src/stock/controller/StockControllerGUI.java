package stock.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import stock.model.DollarCostStrategy;
import stock.model.PersistPortfolioJSON;
import stock.model.PersistPortfolioJSONImpl;
import stock.model.PersistStrategyJSON;
import stock.model.PersistStrategyJSONImpl;
import stock.model.PersistentPortfolioManagerModel;
import stock.model.RegularStrategy;
import stock.model.Strategy;
import stock.view.GUIStockView;

/**
 * This class implements the interface for a GUI. It support features that are various call back
 * methods for the view to send data to the controller.
 */
public class StockControllerGUI implements IStockController {
  private final PersistentPortfolioManagerModel model;
  private final GUIStockView view;

  /**
   * Constructs a controller object with the given model and view.
   *
   * @param model the model for the controller to pass data to.
   * @param view  the view which will display the results.
   */
  public StockControllerGUI(PersistentPortfolioManagerModel model, GUIStockView view) {
    this.model = model;
    this.view = view;
  }


  @Override
  public void execute() throws IOException {
    view.addFeatures(new FeaturesImpl());
  }

  /**
   * Implements the feature interface to provide callback methods for the view. Each operation is
   * mapped to a method providing the capability to read inputs as method arguments and pass those
   * inputs to the model.
   */
  public class FeaturesImpl implements Features {

    private LocalDateTime getDateTime(LocalDate date) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm");
      return LocalDateTime.parse(date.toString() + ",16:00"
              , formatter);
    }

    private boolean isValidDate(LocalDateTime dateTime1, LocalDateTime dateTime2) {
      return dateTime1.isBefore(dateTime2);
    }

    private boolean isValidParams(double amount, double commission, int frequency) {
      return amount > 0 && commission >= 0 && frequency > 0;
    }

    private String getPortfolioName(String portfolioInfo, boolean load) {
      String portfolioName = portfolioInfo;
      if (load) {
        PersistPortfolioJSON portfolioJSON = new PersistPortfolioJSONImpl(Paths.get("res",
                "persisted", "portfolio").toString());
        portfolioName = model.loadPortfolio(portfolioInfo, portfolioJSON);
      }
      return portfolioName;
    }

    private void persist(boolean save, Strategy strategy) throws IOException {
      if (save) {
        PersistStrategyJSON persistStrategyJSON = new PersistStrategyJSONImpl(Paths.get("res",
                "persisted", "strategy").toString());
        persistStrategyJSON.saveStrategy(strategy);
      }
    }

    private void applyOp(Strategy strategy, boolean apply, String portfolioInfo, boolean load,
                         boolean savePort) throws IOException {
      if (apply) {
        String portfolioName = getPortfolioName(portfolioInfo, load);
        model.addToPortfolio(portfolioName, strategy);
        if (savePort) {
          PersistPortfolioJSON portfolioJSON = new PersistPortfolioJSONImpl(Paths.get("res",
                  "persisted", "portfolio").toString());
          model.savePortfolio(portfolioName, portfolioJSON);
        }
      }
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

    private Map<String, Double> getTickerWeightMapping(List<String> tickers, List<Double> weight) {
      Map<String, Double> tickerWeightMap = new HashMap<>();
      for (int i = 0; i < weight.size(); i++) {
        tickerWeightMap.put(tickers.get(i), weight.get(i));
      }
      return tickerWeightMap;
    }

    private void operate(boolean save, boolean load, boolean apply, String portfolioInfo,
                         Strategy strategy, boolean savePort) throws IOException {
      persist(save, strategy);
      applyOp(strategy, apply, portfolioInfo, load, savePort);
    }

    @Override
    public void createPortfolio(String portfolioName) {

      model.createPortfolio(portfolioName);
      view.write("Portfolio created successfully.");
    }

    @Override
    public void createStrategyDollarCost(String strategyName, Map<String, Double> tickerWeight,
                                         LocalDate start, LocalDate end,
                                         double amount, double commission, int frequency,
                                         boolean save, boolean apply, boolean load,
                                         String portfolioInfo, boolean savePort) {
      if (!isValidDate(getDateTime(start), getDateTime(end))) {
        view.write("End date cannot be smaller than start date.\n");
      } else if (!isValidParams(amount, commission, frequency)) {
        view.write("Invalid values such as negative number or zero value in case of amount" +
                "and frequency.\n");
      } else {
        try {
          Strategy dollarCost = new DollarCostStrategy(tickerWeight, amount, getDateTime(start)
                  , getDateTime(end), frequency, commission, strategyName);
          operate(save, load, apply, portfolioInfo, dollarCost, savePort);
          view.write("Strategy executed successfully.");
        } catch (Exception e) {
          view.write(e.getMessage());
        }
      }
    }

    @Override
    public void createStrategyDollarCost(String strategyName, List<String> tickerSymbols,
                                         LocalDate start, LocalDate end, double amount,
                                         double commission, int frequency, boolean save,
                                         boolean apply, boolean load, String portfolioInfo
            , boolean savePort) {
      if (!isValidDate(getDateTime(start), getDateTime(end))) {
        view.write("End date cannot be smaller than start date.\n");
      } else if (!isValidParams(amount, commission, frequency)) {
        view.write("Invalid values such as negative number or zero value in case of amount" +
                "and frequency.\n");
      } else {
        try {
          List<Double> weights = getEqualWeights(tickerSymbols);
          Map<String, Double> map = getTickerWeightMapping(tickerSymbols, weights);
          Strategy dollarCost = new DollarCostStrategy(map, amount, getDateTime(start),
                  getDateTime(end)
                  , frequency, commission, strategyName);
          operate(save, load, apply, portfolioInfo, dollarCost, savePort);
          view.write("Strategy executed successfully.");
        } catch (Exception e) {
          view.write(e.getMessage());
        }
      }
    }

    @Override
    public void createStrategyDollarCost(String strategyName, Map<String, Double> tickerWeight,
                                         LocalDate start, double amount,
                                         double commission, int frequency, boolean save,
                                         boolean apply, boolean load, String portfolioInfo
            , boolean savePort) {

      if (!isValidParams(amount, commission, frequency)) {
        view.write("Invalid values such as negative number or zero value in case of amount" +
                "and frequency.\n");
      } else {
        try {
          Strategy dollarCost = new DollarCostStrategy(tickerWeight, amount, getDateTime(start)
                  , frequency, commission, strategyName);
          operate(save, load, apply, portfolioInfo, dollarCost, savePort);
          view.write("Strategy executed successfully.");
        } catch (Exception e) {
          view.write(e.getMessage());
        }
      }
    }

    @Override
    public void createStrategyDollarCost(String strategyName, List<String> tickerSymbols,
                                         LocalDate start, double amount, double commission,
                                         int frequency, boolean save, boolean apply, boolean load,
                                         String portfolioInfo, boolean savePort) {
      if (!isValidParams(amount, commission, frequency)) {
        view.write("Invalid values such as negative number or zero value in case of amount" +
                "and frequency.\n");
      } else {
        try {
          List<Double> weights = getEqualWeights(tickerSymbols);
          Map<String, Double> map = getTickerWeightMapping(tickerSymbols, weights);
          Strategy dollarCost = new DollarCostStrategy(map, amount, getDateTime(start), frequency,
                  commission, strategyName);
          operate(save, load, apply, portfolioInfo, dollarCost, savePort);
          view.write("Strategy executed successfully.");
        } catch (Exception e) {
          view.write(e.getMessage());
        }
      }
    }

    @Override
    public void createStrategyRegular(String tickerSymbol, double amount,
                                      double commission, LocalDate dateTime, boolean save,
                                      boolean apply, boolean load, String portfolioInfo,
                                      String strategyName, boolean savePort) {
      System.out.println(commission + ":comm");
      if (!isValidParams(amount, commission, 1)) {
        view.write("Invalid values such as negative number or zero value in case of amount" +
                "and frequency.\n");
      } else {
        try {
          Strategy regular = new RegularStrategy(tickerSymbol, amount, commission,
                  getDateTime(dateTime)
                  , strategyName);
          operate(save, load, apply, portfolioInfo, regular, savePort);
          view.write("Strategy executed successfully.");
        } catch (Exception e) {
          view.write(e.getMessage());
        }
      }
    }

    @Override
    public void addStock(boolean loadPortfolio, String portfolioNamePath, String strategyPath
            , boolean savePort) {
      try {
        String portfolioName = getPortfolioName(portfolioNamePath, loadPortfolio);
        PersistStrategyJSON persistStrategyJSON = new PersistStrategyJSONImpl(Paths.get("res",
                "persisted", "strategy").toString());
        Strategy strategy = persistStrategyJSON.loadStrategy(strategyPath);
        model.addToPortfolio(portfolioName, strategy);
        if (savePort) {
          PersistPortfolioJSON portfolioJSON = new PersistPortfolioJSONImpl(Paths.get("res",
                  "persisted", "portfolio").toString());
          model.savePortfolio(portfolioName, portfolioJSON);
        }
        view.write("Strategy executed successfully.");
      } catch (Exception e) {
        view.write(e.getMessage());
      }
    }

    private List<LocalDateTime> getListOfDatesToBuyOn(LocalDateTime startDate, LocalDateTime
            endDate, int intervalInDays) {
      List<LocalDateTime> returnList = new ArrayList<>();
      LocalDateTime presentDate = startDate;
      while (presentDate.compareTo(endDate) <= 0) {
        returnList.add(presentDate);
        presentDate = presentDate.plusDays(intervalInDays);
      }
      return returnList;
    }

    @Override
    public CostValues getGraphData(boolean load, String portfolioInfo, LocalDateTime start,
                                   LocalDateTime end) {
      List<LocalDateTime> listOfDays = getListOfDatesToBuyOn(start, end, 1);
      String portfolioName = getPortfolioName(portfolioInfo, load);
      Map<LocalDateTime, Double> dateCostBasisMap = new HashMap<>();
      Map<LocalDateTime, Double> dateCurrentValueMap = new HashMap<>();
      Map<LocalDateTime, Double> dateProfitMap = new HashMap<>();
      for (LocalDateTime dateTime : listOfDays) {
        double currentVal;
        try {
          currentVal = model.getPortfolioCurrentValue(portfolioName, dateTime);
          System.out.println(currentVal);
        } catch (Exception e) {
          System.out.println(e.getMessage());
          currentVal = 0.0;
        }
        if (currentVal != 0.0) {
          double costBasis = model.getPortfolioCurrentValue(portfolioName, dateTime);
          dateCostBasisMap.put(dateTime, costBasis);
          dateCurrentValueMap.put(dateTime, currentVal);
        }
      }
      return new CostValues(dateCostBasisMap, dateCurrentValueMap);
    }

    @Override
    public Optional<Double> getCostBasis(boolean loadPortfolio, String portfolioNamePath,
                                         LocalDate dateTime) {
      try {
        return Optional.of(model.getPortfolioCostBasis(getPortfolioName(portfolioNamePath,
                loadPortfolio),
                getDateTime(dateTime)));
      } catch (Exception e) {
        view.write(e.getMessage());
        return Optional.empty();
      }
    }

    @Override
    public Optional<Double> getCurrentValue(boolean loadPortfolio, String portfolioNamePath,
                                            LocalDate dateTime) {
      try {
        return Optional.of(model.getPortfolioCurrentValue(getPortfolioName(portfolioNamePath,
                loadPortfolio),
                getDateTime(dateTime)));
      } catch (Exception e) {
        view.write(e.getMessage());
        return Optional.empty();
      }
    }
  }
}
