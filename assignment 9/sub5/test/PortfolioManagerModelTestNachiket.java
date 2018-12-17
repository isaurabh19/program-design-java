import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import stock.model.DollarCostStrategy;
import stock.model.IPortfolioManagerModel;
import stock.model.PortfolioManagerModel;
import stock.model.RegularStrategy;
import stock.model.Strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PortfolioManagerModelTestNachiket {
  private IPortfolioManagerModel managerHighPay = new PortfolioManagerModel();
  private StringBuilder output = new StringBuilder();
  private StringBuilder output1 = new StringBuilder();
  private String portfolioName = "SamplePortfolioName";
  private String portfolioName1 = "SamplePortfolioName1";
  private LocalDateTime janEight2018 = LocalDateTime
          .of(2018, 1, 8, 10, 30);
  private LocalDateTime febEight2018 = LocalDateTime
          .of(2018, 2, 8, 10, 30);
  private LocalDateTime novNine2018 = LocalDateTime
          .of(2018, 11, 9, 10, 30);
  private LocalDateTime novTwentyThree2018 = LocalDateTime
          .of(2018, 11, 23, 10, 30);
  private Strategy strategy;
  private Map<String, Double> tickerWeightMap;

  @Test
  public void testCreatePortfolioNull() {
    managerHighPay.createPortfolio(portfolioName1);
    tickerWeightMap = new LinkedHashMap<>();
    tickerWeightMap.put("AMZN", 100d);
    strategy = new DollarCostStrategy(tickerWeightMap, 1500d,
            janEight2018, janEight2018, 1, 0.0d);
    managerHighPay.addToPortfolio(portfolioName1, strategy);
    output1.append(generateString("AMZN", 1500d, janEight2018));
    try {
      managerHighPay.createPortfolio(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.\n", e.getMessage());
      assertTrue(managerHighPay.getContentsOn(portfolioName1,
              LocalDateTime.now()).toString().contains(output1.toString()));

    }
    assertEquals(1246.87d, managerHighPay
            .getPortfolioCostBasis(portfolioName1, novTwentyThree2018), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testGetContentsBeforeInitialising() {
    try {
      managerHighPay.createPortfolio(portfolioName1);

      tickerWeightMap = new LinkedHashMap<>();
      tickerWeightMap.put("AMZN", 100d);
      strategy = new DollarCostStrategy(tickerWeightMap, 1500d,
              janEight2018, janEight2018, 1, 0.0d);
      managerHighPay.addToPortfolio(portfolioName1, strategy);

      output1.append(generateString("AMZN", 1500d, janEight2018));
    } catch (IllegalArgumentException e) {
      assertEquals("IPortfolio name does not exist.\n", e.getMessage());
      assertTrue(managerHighPay.getContentsOn(portfolioName1,
              LocalDateTime.now()).toString().contains(output1.toString()));

    }
    assertEquals(1246.87d, managerHighPay
            .getPortfolioCostBasis(portfolioName1, novTwentyThree2018), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }


  @Test
  public void testGetValue3SameStockMiddleDate() {

    managerHighPay.createPortfolio(portfolioName1);

    strategy = new RegularStrategy("AMZN", 1500d, 5.0d, janEight2018);
    managerHighPay.addToPortfolio(portfolioName1, strategy);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    strategy = new RegularStrategy("AMZN", 10000d, 5.0d, janEight2018);
    managerHighPay.addToPortfolio(portfolioName, strategy);
    output.append(generateString("AMZN", 10000d, janEight2018));


    assertTrue(managerHighPay.getContentsOn(portfolioName1, LocalDateTime.now()).toString()
            .contains(output1.toString()));

    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.1d);
    assertEquals(10804.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.plusMonths(1)), 0.0d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusMonths(1)), 0.0d);


    assertTrue(managerHighPay.getContentsOn(portfolioName, LocalDateTime.now()).toString()
            .contains(output.toString()));


    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(10804.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            febEight2018), 0.0d);
    assertEquals(13049.36d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            LocalDateTime.of(2018, 11, 13, 10, 30)),
            0.0d);


    strategy = new RegularStrategy("AMZN", 5000d, 5.0d, janEight2018);
    managerHighPay.addToPortfolio(portfolioName, strategy);


    assertTrue(managerHighPay.getContentsOn(portfolioName, LocalDateTime.now()).toString()
            .contains("AMZN=12"));

    assertEquals(14962.4399d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.1d);
    assertEquals(19574.04d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            LocalDateTime.of(2018, 11, 13, 10, 30)),
            0.01d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusDays(1)), 0.1d);

    assertTrue(managerHighPay.getContentsOn(portfolioName1, LocalDateTime.now()).toString()
            .contains(output1.toString()));

    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1,
            LocalDateTime.now()), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }


  @Test
  public void testMultiplePortfolioSimple() {
    //Create 2 portfolios - portfolioName and portfolioName1.
    managerHighPay.createPortfolio(portfolioName);

    //Add AMZN to portfolioName

    managerHighPay.createPortfolio(portfolioName1);
    tickerWeightMap = new LinkedHashMap<>();
    tickerWeightMap.put("AMZN", 100d);
    strategy = new DollarCostStrategy(tickerWeightMap, 10000d, janEight2018, janEight2018,
            1, 0.0d);
    managerHighPay.addToPortfolio(portfolioName1, strategy);
    managerHighPay.addToPortfolio(portfolioName, strategy);
    output1.append(generateString("AMZN", 10000d, janEight2018));


    output.append(generateString("AMZN", 10000d, janEight2018));
    assertTrue(managerHighPay.getContentsOn(portfolioName1, LocalDateTime.now()).toString()
            .contains(output1.toString()));

    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.1d);

    assertEquals(10804.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.plusMonths(1)), 0.0d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusMonths(1)), 0.0d);

    assertTrue(managerHighPay.getContentsOn(portfolioName1, LocalDateTime.now())
            .toString().contains(output1.toString()));

    assertEquals(0.0d, managerHighPay.getPortfolioCostBasis(portfolioName1,
            LocalDateTime.now().minusYears(1)), 0.1d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            LocalDateTime.now().minusYears(1)), 0.1d);


  }

  @Test
  public void testSimpleGetPortfolioContent() {
    managerHighPay.createPortfolio("College-Saving");



    tickerWeightMap = new LinkedHashMap<>();
    tickerWeightMap.put("AMZN", 100d);
    strategy = new DollarCostStrategy(tickerWeightMap, 2000d, janEight2018, janEight2018,
            1, 0.0d);
    managerHighPay.addToPortfolio("College-Saving", strategy);


    output.append(generateString("AMZN", 2000d,
            LocalDateTime.of(2008, 11, 13, 9, 0)));



    tickerWeightMap = new LinkedHashMap<>();
    tickerWeightMap.put("MSFT", 100d);
    strategy = new DollarCostStrategy(tickerWeightMap, 1000d, janEight2018, janEight2018,
            1, 0.0d);
    managerHighPay.addToPortfolio("College-Saving", strategy);

    output.append(generateString("MSFT", 1000d,
            LocalDateTime.of(2018, 11, 13, 10, 20)));

    assertTrue(managerHighPay.getContentsOn("College-Saving", LocalDateTime.now())
            .toString().contains(output1.toString()));

  }

  @Test
  public void testSimpleGetPortfolioContent1() {
    managerHighPay.createPortfolio("College-Saving");
    try {
      tickerWeightMap = new LinkedHashMap<>();
      tickerWeightMap.put("AMZN", 100d);
      strategy = new DollarCostStrategy(tickerWeightMap, -1500d, janEight2018,
              janEight2018, 1, 0.0d);
      managerHighPay.addToPortfolio("College-Saving", strategy);
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input to the constructor.\n", e.getMessage());
    }
    try {
      tickerWeightMap = new LinkedHashMap<>();
      tickerWeightMap.put("AMZN", 100d);
      strategy = new DollarCostStrategy(tickerWeightMap, 15d, janEight2018,
              janEight2018, 1, 0.0d);
      managerHighPay.addToPortfolio("College-Saving", strategy);
    } catch (IllegalStateException e) {
      assertEquals("Insufficient funds.\n", e.getMessage());
    }
    assertTrue(managerHighPay.getContentsOn("College-Saving", LocalDateTime.now())
            .toString().contains(output1.toString()));
  }


  private String generateString(String tickerSymbol, Double amount, LocalDateTime date) {
    Double closingAmount = valueOfStockOnDate(tickerSymbol, date);
    Integer numOfStocks = numberOfStocksBought(closingAmount, amount);
    return tickerSymbol + "=" + numOfStocks;
  }


  private Integer numberOfStocksBought(Double closeD, Double amount) {
    double numberOfStocks = Math.floor(amount / closeD);
    if (numberOfStocks == 0) {
      throw new IllegalArgumentException("Insufficient funds.");
    }
    return (int) numberOfStocks;
  }

  private Double valueOfStockOnDate(String tickerSymbol, LocalDateTime date) {
    String[] stockData = fetchStockData(tickerSymbol, date);
    String strClosingAmt = stockData[4];
    return Double.parseDouble(strClosingAmt);
  }

  private String[] fetchStockData(String tickerSymbol, LocalDateTime date) {
    String strDateOfPurchase = getDateInFormat(date);
    try {
      Scanner scan = new Scanner(new File("stockInfo/" + tickerSymbol + ".csv"));
      scan.useDelimiter("\n");
      while (scan.hasNext()) {
        String line = scan.next();
        if (line.contains(strDateOfPurchase)) {
          return line.split(",");
        }
      }
    } catch (FileNotFoundException e) {
      //Do nothing.
    }
    throw new IllegalArgumentException("In test: Date not found.");
  }

  private String getDateInFormat(LocalDateTime date) {
    if (date.getDayOfWeek() == DayOfWeek.SATURDAY
            || date.getDayOfWeek() == DayOfWeek.SUNDAY
            || date.toLocalTime().compareTo(LocalTime.of(8, 0)) < 0
            || date.toLocalTime().compareTo(LocalTime.of(16, 0)) > 0
    ) {
      throw new IllegalArgumentException("Test: Enter business times.");
    }
    DateTimeFormatter dateFormatForAPI = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return dateFormatForAPI.format(date);
  }
}
