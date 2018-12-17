import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import stock.model.DollarCostStrategy;
import stock.model.IPortfolioManagerModel;
import stock.model.PortfolioManagerModel;
import stock.model.Strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PortfolioManagerModelTestNewFeaturesNachiket {
  private Map<String, Double> tickerWeight = new HashMap<>();
  private Strategy strategy;
  private IPortfolioManagerModel manager = new PortfolioManagerModel();
  private String portfolioName = "samplePortfolio";
  private final LocalDateTime jan8 =
          LocalDateTime.of(2018, 1, 8, 10, 30);
  private final LocalDateTime feb8 = jan8.plusMonths(1);
  private final LocalDateTime march8 = jan8.plusMonths(2);

  @Before
  public void setup() {
    tickerWeight.put("AMZN", 30d);
    tickerWeight.put("FB", 50d);
  }

  @Test
  public void testSimpleRecurring() {
    tickerWeight.put("MSFT", 10d);
    tickerWeight.put("GOOG", 10d);
    Strategy strategy = new DollarCostStrategy(tickerWeight, 50000d, jan8, jan8,
            15, 5d);
    manager.createPortfolio(portfolioName);
    manager.addToPortfolio(portfolioName, strategy);

    //assertEquals("{MSFT=56, GOOG=4, FB=132, AMZN=12}",
    // manager.getContentsOn(portfolioName, LocalDateTime.now()).toString());
    assertEquals("{}", manager.getContentsOn(portfolioName,
            jan8.minusDays(1)).toString());
  }

  @Test
  public void testExceptionWeightsMoreThan100() {
    tickerWeight.put("GOOG", 30d);

    try {
      Strategy strategy = new DollarCostStrategy(tickerWeight, 50000d, jan8, feb8,
              15, 5d);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input to the constructor.\n", e.getMessage());
    }
  }

  @Test
  public void testExceptionNullMap() {
    try {
      Strategy strategy = new DollarCostStrategy(null, 50000d, jan8, feb8,
              15, 5d);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input to the constructor.\n", e.getMessage());
    }
  }


  @Test
  public void testMultipleStocks() {
    manager.createPortfolio(portfolioName);
    Map<String, Double> tickerWeight = new HashMap<>();
    tickerWeight.put("MSFT", 100 / 3d);
    tickerWeight.put("AMZN", 100 / 3d);
    tickerWeight.put("GOOG", 100 / 3d);
    Strategy strategy = new DollarCostStrategy(tickerWeight, 50000d,
            LocalDateTime.of(2016, 11, 12, 10, 30),
            LocalDateTime.of(2018, 11, 12, 10, 30),
            30, 5d);
    manager.addToPortfolio(portfolioName, strategy);
    String[] test = {"AMZN=356", "GOOG=415", "MSFT=5222"};
    for (String stock : test) {
      assertTrue(manager.getContentsOn(portfolioName, LocalDateTime.now()).toString()
              .contains(stock));
    }
  }

  @Test
  public void testMapRecursive() {
    tickerWeight.put("GOOG", 10d);
    tickerWeight.put("MSFT", 10d);
    manager.createPortfolio(portfolioName);
    Strategy strategy = new DollarCostStrategy(tickerWeight, 50000d, jan8, march8,
            30, 5d);
    manager.addToPortfolio(portfolioName, strategy);

    assertTrue(manager.getContentsOn(portfolioName, jan8.minusDays(1)).isEmpty());

    Map<String, Integer> testMap = new LinkedHashMap<>();
    testMap.put("MSFT", 56);
    testMap.put("GOOG", 4);
    testMap.put("FB", 132);
    testMap.put("AMZN", 12);

    assertEquals(testMap.toString(), manager.getContentsOn(portfolioName,
            jan8.plusDays(1)).toString());

    assertEquals(testMap.toString(), manager.getContentsOn(portfolioName,
            feb8.minusDays(10)).toString());

    testMap.replace("MSFT", 111);
    testMap.replace("GOOG", 8);
    testMap.replace("FB", 270);
    testMap.replace("AMZN", 22);

    assertEquals(testMap.toString(), manager.getContentsOn(portfolioName,
            feb8.plusDays(5)).toString());

    assertEquals(testMap.toString(), manager.getContentsOn(portfolioName,
            march8.minusDays(15)).toString());

  }
}