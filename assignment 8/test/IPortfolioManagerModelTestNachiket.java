import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import stock.model.IPortfolioManagerModel;
import stock.model.PortfolioManagerModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IPortfolioManagerModelTestNachiket {
  private IPortfolioManagerModel managerHighPay = new PortfolioManagerModel();
  private StringBuilder output = new StringBuilder();
  private StringBuilder output1 = new StringBuilder();
  private String portfolioName = "SamplePortfolioName";
  private String portfolioName1 = "SamplePortfolioName1";
  private LocalDateTime janEight2018 = LocalDateTime.of(2018, 1, 8, 10, 30);
  private LocalDateTime febEight2018 = LocalDateTime.of(2018, 2, 8, 10, 30);
  private LocalDateTime novNine2018 = LocalDateTime.of(2018, 11, 9, 10, 30);


  @Test
  public void testCreatePortfolioNull() {
    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));
    try {
      managerHighPay.createPortfolio(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.\n", e.getMessage());
      assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    }
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testGetContentsBeforeInitialising() {
    try {
      managerHighPay.createPortfolio(portfolioName1);
      managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
      output1.append(generateString("AMZN", 1500d, janEight2018));
      managerHighPay.getPortfolioContent("Test");
    } catch (IllegalArgumentException e) {
      assertEquals("IPortfolio name does not exist.\n", e.getMessage());
      assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    }
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testInitialValuesBeforeAdding() {
    managerHighPay.createPortfolio("testPortfolio");
    assertTrue(managerHighPay.getPortfolioContent("testPortfolio").isEmpty());
    assertEquals(0.0d,
            managerHighPay.getPortfolioCostBasis("testPortfolio"), 0.001d);
    assertEquals(0.0d,
            managerHighPay.getPortfolioCurrentValue("testPortfolio",
                    LocalDateTime.now()), 0.001d);
  }

  /**
   * Test create portfolio.
   */
  @Test
  public void testNullCreatePortfolio() {
    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));
    try {
      managerHighPay.createPortfolio("TestPortfolio");
      assertTrue(managerHighPay.getPortfolioContent("TestPortfolio").isEmpty());
      assertEquals(0.0d, managerHighPay.getPortfolioCostBasis("testPortfolio"),
              0.0d);
      assertEquals(0.0d,
              managerHighPay.getPortfolioCurrentValue("testPortfolio",
                      LocalDateTime.now()), 0.0d);

      managerHighPay.createPortfolio(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("IPortfolio name does not exist.\n", e.getMessage());

      assertTrue(managerHighPay.getPortfolioContent("TestPortfolio").isEmpty());
      assertEquals(0.0d, managerHighPay.getPortfolioCostBasis("TestPortfolio"),
              0.0d);
      assertEquals(0.0d,
              managerHighPay.getPortfolioCurrentValue("TestPortfolio",
                      LocalDateTime.now()), 0.0d);
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testEmptyCreatePortfolio() {
    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));
    try {
      managerHighPay.createPortfolio("TestPortfolio");
      assertTrue(managerHighPay.getPortfolioContent("TestPortfolio").isEmpty());
      assertEquals(0.0d, managerHighPay.getPortfolioCostBasis("testPortfolio")
              , 0.0d);
      assertEquals(0.0d,
              managerHighPay.getPortfolioCurrentValue("testPortfolio",
                      LocalDateTime.now()), 0.0d);

      managerHighPay.createPortfolio("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("IPortfolio name does not exist.\n", e.getMessage());

      assertTrue(managerHighPay.getPortfolioContent("TestPortfolio").isEmpty());
      assertEquals(0.0d, managerHighPay.getPortfolioCostBasis("TestPortfolio")
              , 0.0d);
      assertEquals(0.0d,
              managerHighPay.getPortfolioCurrentValue("TestPortfolio",
                      LocalDateTime.now()), 0.0d);
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testAlreadyExistingPortfolio() {
    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));
    try {
      managerHighPay.createPortfolio("TestPortfolio");
      assertTrue(managerHighPay.getPortfolioContent("TestPortfolio").isEmpty());
      assertEquals(0.0d, managerHighPay.getPortfolioCostBasis("TestPortfolio")
              , 0.0d);
      assertEquals(0.0d,
              managerHighPay.getPortfolioCurrentValue("TestPortfolio",
                      LocalDateTime.now()), 0.0d);

      managerHighPay.createPortfolio("TestPortfolio");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("IPortfolio exists. Enter a unique name.\n", e.getMessage());

      assertTrue(managerHighPay.getPortfolioContent("TestPortfolio").isEmpty());
      assertEquals(0.0d, managerHighPay.getPortfolioCostBasis("TestPortfolio")
              , 0.0d);
      assertEquals(0.0d,
              managerHighPay.getPortfolioCurrentValue("TestPortfolio",
                      LocalDateTime.now()), 0.0d);
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  /**
   * Test addToPortfolio.
   */
  @Test
  public void testATPNullPortfolio() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio("TestPortfolio");
    managerHighPay.addToPortfolio("TestPortfolio",
            "AMZN", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("AMZN", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent("TestPortfolio"));
    try {
      managerHighPay.addToPortfolio(null, "AMZN", 2000d,
              LocalDateTime.of(2017, 11, 15, 9, 30));
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent("TestPortfolio"));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testATPEmptyPortfolio() {
    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio("TestPortfolio");
    managerHighPay.addToPortfolio("TestPortfolio",
            "AMZN", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("AMZN", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent("TestPortfolio"));
    try {
      managerHighPay.addToPortfolio("", "AMZN", 2000d,
              LocalDateTime.of(2017, 11, 15, 9, 30));
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent("TestPortfolio"));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testATPNullTicker() {
    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio("TestPortfolio");
    managerHighPay.addToPortfolio("TestPortfolio",
            "AMZN", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("AMZN", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent("TestPortfolio"));
    try {
      managerHighPay.addToPortfolio("TestPortfolio", null, 2000d,
              LocalDateTime.of(2017, 11, 15, 9, 30));
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent("TestPortfolio"));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testATPEmptyTicker() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio("TestPortfolio");
    managerHighPay.addToPortfolio("TestPortfolio",
            "AMZN", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("AMZN", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent("TestPortfolio"));
    try {
      managerHighPay.addToPortfolio("", null, 2000d,
              LocalDateTime.of(2017, 11, 15, 9, 30));
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent("TestPortfolio"));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testATPPortfolioNameDoesNotExist() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName,
            "MSFT", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("MSFT", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.addToPortfolio("AbsentPortfolioName", "AMZN", 1000d,
              LocalDateTime.of(2018, 11, 12, 10, 0));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("IPortfolio name does not exist.\n", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testATPTickerDoesNotExist() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName,
            "MSFT", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("MSFT", 10000d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.addToPortfolio(portfolioName, "AMZN1", 1000d,
              LocalDateTime.of(2018, 11, 12, 10, 0));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid ticker symbol.\n", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testATPAmountLessThanSingleStock() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName,
            "MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.addToPortfolio(portfolioName,
              "MSFT", 10d,
              LocalDateTime.of(2017, 11, 15, 9, 30));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Insufficient funds.", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testATPNullDate() {
    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName,
            "MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.addToPortfolio(portfolioName,
              "MSFT", 1000d, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testATPFutureDate() {
    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName,
            "MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.addToPortfolio(portfolioName,
              "MSFT", 1000d,
              LocalDateTime.of(2019, 8, 1, 10, 30));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter business times.", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testATPInvalidDate() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName,
            "MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.addToPortfolio(portfolioName, "MSFT", 1000d,
              LocalDateTime.of(2018, 2, 29, 10, 30));
      fail();
    } catch (DateTimeException e) {
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }



  /**
   * Test get portfolio content.
   */
  @Test
  public void testNullPortfolioContent() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));


    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName,
            "MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.getPortfolioContent(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.\n", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testEmptyPortfolioContent() {
    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName,
            "MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.getPortfolioContent("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.\n", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testNameDoesNotExistsPortfolioContent() {
    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName,
            "MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30));
    output.append(generateString("MSFT", 100d,
            LocalDateTime.of(2017, 11, 15, 9, 30)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.getPortfolioContent("SomeRandomGenericName");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("IPortfolio name does not exist.\n", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  /**
   * Test getCostBasis extreme.
   */
  @Test
  public void testNullCostBasis() {
    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName, "AMZN", 10000d, janEight2018);
    output.append(generateString("AMZN", 10000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.getPortfolioCostBasis(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.\n", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testEmptyCostBasis() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName, "AMZN", 10000d, janEight2018);
    output.append(generateString("AMZN", 10000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.getPortfolioCostBasis("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.\n", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  /**
   * Test get portfolio current value.
   */
  @Test
  public void testNullDate() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName, "AMZN", 10000d, janEight2018);
    output.append(generateString("AMZN", 10000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.getPortfolioCurrentValue(portfolioName, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid date.\n", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testEmptyValue() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName, "AMZN", 10000d, janEight2018);
    output.append(generateString("AMZN", 10000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.getPortfolioCurrentValue("", febEight2018);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.\n", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testNullValue() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));


    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName, "AMZN", 10000d, janEight2018);
    output.append(generateString("AMZN", 10000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    try {
      managerHighPay.getPortfolioCurrentValue(null, febEight2018);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.\n", e.getMessage());
      assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    }
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  /**
   * Check for the sequence of operations for cost-basis.
   */
  @Test
  public void testCostBasis1Stock() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));


    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName, "AMZN", 5000d, janEight2018);
    output.append(generateString("AMZN", 5000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(4987.48d, managerHighPay.getPortfolioCostBasis(portfolioName), 0.1d);
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testCostBasis2SameStockDifferentDates() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));


    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName, "AMZN", 5000d, janEight2018);
    output.append(generateString("AMZN", 5000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(4987.48d, managerHighPay.getPortfolioCostBasis(portfolioName), 0.1d);

    managerHighPay.addToPortfolio(portfolioName, "AMZN", 2000d, febEight2018);
    output.append(generateString("AMZN", 2000d, febEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(6337.98d, managerHighPay.getPortfolioCostBasis(portfolioName), 0.1d);

    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testCostBasis2DifferentStockDifferentDates() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.addToPortfolio(portfolioName, "AMZN", 5000d, janEight2018);
    output.append(generateString("AMZN", 5000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(4987.48d, managerHighPay.getPortfolioCostBasis(portfolioName), 0.1d);

    managerHighPay.addToPortfolio(portfolioName, "FB", 2000d, febEight2018);
    output.append(generateString("FB", 2000d, febEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(6874.86d, managerHighPay.getPortfolioCostBasis(portfolioName), 0.1d);

    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }


  /**
   * Test value by date.
   */
  @Test
  public void testGetValue2SameStockSimple() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));


    managerHighPay.createPortfolio(portfolioName);

    managerHighPay.addToPortfolio(portfolioName, "AMZN", 10000d, janEight2018);
    output.append(generateString("AMZN", 10000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(10804.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.plusMonths(1)), 0.0d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusMonths(1)), 0.0d);

    managerHighPay.addToPortfolio(portfolioName, "AMZN", 2000d, febEight2018);
    output.append(generateString("AMZN", 2000d, febEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));

    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(12154.5d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.plusMonths(1)), 0.0d);

    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testGetValue2DifferentStockSimple() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);

    managerHighPay.addToPortfolio(portfolioName, "AMZN", 10000d, janEight2018);
    output.append(generateString("AMZN", 10000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(10804.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.plusMonths(1)), 0.0d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusMonths(1)), 0.0d);

    managerHighPay.addToPortfolio(portfolioName, "MSFT", 2000d, febEight2018);
    output.append(generateString("MSFT", 2000d, febEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));

    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(12759.23d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.plusMonths(1)), 0.0d);

    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testGetValue3DifferentStockMiddleDate() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);

    managerHighPay.addToPortfolio(portfolioName, "AMZN", 10000d, janEight2018);
    output.append(generateString("AMZN", 10000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(10804.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.plusMonths(1)), 0.0d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusMonths(1)), 0.0d);

    managerHighPay.addToPortfolio(portfolioName, "MSFT", 2000d, febEight2018);
    output.append(generateString("MSFT", 2000d, febEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));

    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(12759.23d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            febEight2018), 0.0d);
    assertEquals(15508.98d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            LocalDateTime.of(2018, 11, 13, 10, 30)), 0.0d);

    managerHighPay.addToPortfolio(portfolioName, "FB", 5000d, novNine2018);
    output.append(generateString("FB", 5000d, novNine2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));

    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(20342.42d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            LocalDateTime.of(2018, 11, 13, 10, 30)), 0.0d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusDays(1)), 0.0d);

    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);

  }

  @Test
  public void testGetValue3SameStockMiddleDate() {

    managerHighPay.createPortfolio(portfolioName1);
    managerHighPay.addToPortfolio(portfolioName1, "AMZN", 1500d, janEight2018);
    output1.append(generateString("AMZN", 1500d, janEight2018));

    managerHighPay.createPortfolio(portfolioName);

    managerHighPay.addToPortfolio(portfolioName, "AMZN", 10000d, janEight2018);
    output.append(generateString("AMZN", 10000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(10804.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.plusMonths(1)), 0.0d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusMonths(1)), 0.0d);

    managerHighPay.addToPortfolio(portfolioName, "AMZN", 2000d, febEight2018);
    output.append(generateString("AMZN", 2000d, febEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));

    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(12154.5d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            febEight2018), 0.0d);
    assertEquals(14680.53d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            LocalDateTime.of(2018, 11, 13, 10, 30)), 0.0d);

    managerHighPay.addToPortfolio(portfolioName, "AMZN", 5000d, novNine2018);
    output.append(generateString("AMZN", 5000d, novNine2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));

    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(17942.87d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            LocalDateTime.of(2018, 11, 13, 10, 30)), 0.01d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusDays(1)), 0.0d);

    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(1246.87d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(1712.43d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  /**
   * Test for multiple portfolios.
   */
  @Test
  public void testMultiplePortfolioSimple() {
    //Create 2 portfolios - portfolioName and portfolioName1.
    managerHighPay.createPortfolio(portfolioName);
    managerHighPay.createPortfolio(portfolioName1);

    //Add AMZN to portfolioName
    managerHighPay.addToPortfolio(portfolioName, "AMZN", 10000d, janEight2018);
    output.append(generateString("AMZN", 10000d, janEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(10804.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.plusMonths(1)), 0.0d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusMonths(1)), 0.0d);

    //Adding AMZN to portfolioName does not affect the values for portfolioName1
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(0.0d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            LocalDateTime.now()), 0.1d);

    //Add the stocks of FB. All methods reflect change for portfolioName1
    managerHighPay.addToPortfolio(portfolioName1, "FB", 5000d, janEight2018);
    output1.append(generateString("FB", 5000d, janEight2018));
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(4895.28d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(3768.96d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);

    //Initial values for portfolioName have not changed.
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));
    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(10804.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.plusMonths(1)), 0.0d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusMonths(1)), 0.0d);

    //Adding MSFT to portfolioName will reflect change in portfolioName but not portfolioName1
    managerHighPay.addToPortfolio(portfolioName, "MSFT", 2000d, febEight2018);
    output.append(generateString("MSFT", 2000d, febEight2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));

    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(4895.28d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(3768.96d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);

    //Similarly, adding to portfolioName1 will not change the values in portfolioName
    managerHighPay.addToPortfolio(portfolioName1, "GOOG", 5000d, febEight2018);
    output1.append(generateString("GOOG", 5000d, febEight2018));
    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(8901.36d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(8033.56d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);

    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));

    //Previous methods are compatible for portfolioName
    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(12759.23d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            febEight2018), 0.0d);
    assertEquals(15508.98d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            LocalDateTime.of(2018, 11, 13, 10, 30)), 0.0d);

    //Adding FB to portfolioName does not affect portfolioName1
    managerHighPay.addToPortfolio(portfolioName, "FB", 5000d, novNine2018);
    output.append(generateString("FB", 5000d, novNine2018));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent(portfolioName));

    assertEquals(9974.96d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018), 0.0d);
    assertEquals(20342.42d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            LocalDateTime.of(2018, 11, 13, 10, 30)), 0.0d);
    assertEquals(0.0d, managerHighPay.getPortfolioCurrentValue(portfolioName,
            janEight2018.minusDays(1)), 0.0d);

    assertEquals(output1.toString(), managerHighPay.getPortfolioContent(portfolioName1));
    assertEquals(8901.36d, managerHighPay.getPortfolioCostBasis(portfolioName1), 0.1d);
    assertEquals(8033.56d, managerHighPay.getPortfolioCurrentValue(portfolioName1,
            novNine2018), 0.1d);
  }

  @Test
  public void testSimpleGetPortfolioContent() {
    managerHighPay.createPortfolio("College-Saving");

    managerHighPay.addToPortfolio("College-Saving", "AMZN", 2000d,
            LocalDateTime.of(2008, 11, 13, 9, 0));
    output.append(generateString("AMZN", 2000d,
            LocalDateTime.of(2008, 11, 13, 9, 0)));

    managerHighPay.addToPortfolio("College-Saving", "MSFT", 1000d,
            LocalDateTime.of(2018, 11, 13, 10, 20));
    output.append(generateString("MSFT", 1000d,
            LocalDateTime.of(2018, 11, 13, 10, 20)));
    assertEquals(output.toString(), managerHighPay.getPortfolioContent("College-Saving"));
  }

  @Test
  public void testSimpleGetPortfolioContent1() {
    managerHighPay.createPortfolio("College-Saving");
    try {
      managerHighPay.addToPortfolio("College-Saving", "AMZN", -20.5d,
              LocalDateTime.of(2008, 11, 13, 9, 0));
    } catch (IllegalArgumentException e) {
      assertEquals("Enter valid input.", e.getMessage());
    }
    try {
      managerHighPay.addToPortfolio("College-Saving", "MSFT", 0.5d,
              LocalDateTime.of(2018, 11, 13, 10, 20));
    } catch (IllegalArgumentException e) {
      assertEquals("Insufficient funds.", e.getMessage());
    }
    assertEquals(output.toString(), managerHighPay.getPortfolioContent("College-Saving"));
  }

  private String generateString(String tickerSymbol, Double amount, LocalDateTime date) {
    Double closingAmount = valueOfStockOnDate(tickerSymbol, date);
    Integer numOfStocks = numberOfStocksBought(closingAmount, amount);
    StringBuilder toReturn = new StringBuilder();

    return toReturn.append("Bought ").append(numOfStocks).append(" stocks of ")
            .append(tickerSymbol).append(" at $")
            .append(closingAmount).append(" each on ").append(date).append("\n").toString();
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