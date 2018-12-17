import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDateTime;

import stock.controller.IStockController;
import stock.controller.StockController;
import stock.view.StockView;

import static org.junit.Assert.assertEquals;

public class MockModelTest {
  private StringBuilder expectedLogOutput = new StringBuilder();
  private StringBuilder expectedOutOutput = new StringBuilder();
  private LocalDateTime testDate = LocalDateTime.of(2010, 11, 15, 10, 0);

  @Test
  public void testCreatePortfolio() {
    StringBuffer out = new StringBuffer();
    StringBuilder log = new StringBuilder();
    Reader in = new StringReader("create_portfolio samplePort q");
    IStockController controller = new StockController(new MockModel(log, 7.05d),
            new StockView(in, out));
    expectedOutOutput.append(genereateInitialOutMessage())
            .append(generateRealCreatePortfolio("samplePort"));

    expectedLogOutput.append(generateMockCreatePortfolio("samplePort"));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(expectedLogOutput.toString(), log.toString());//inputs reached the model correctly
    assertEquals(expectedOutOutput.toString(), out.toString());//inputs reached the model correctly
    //assertEquals("1234321\n1234321\n", out.toString()); //output of model transmitted correctly
  }

  @Test
  public void testAddToPortfolio1() {
    StringBuffer out = new StringBuffer();
    StringBuilder log = new StringBuilder();
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock samplePort " +
            "MSFT 5340.75 " + "2010-11-15,10:00\nq");
    IStockController controller = new StockController(new MockModel(log, 7.05d),
            new StockView(reader, out));
    expectedOutOutput.append(genereateInitialOutMessage())
            .append(generateRealCreatePortfolio("samplePort"))
            .append(getRealContent());

    expectedLogOutput.append(generateMockCreatePortfolio("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "MSFT", 5340.75d,
                    testDate))
            .append(getMockContent("samplePort"));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(expectedLogOutput.toString(), log.toString());//inputs reached the model correctly
    assertEquals(expectedOutOutput.toString(), out.toString());//inputs reached the model correctly
    //assertEquals("1234321\n1234321\n", out.toString()); //output of model transmitted correctly
  }

  @Test
  public void testAddToPortfolio() {
    StringBuffer out = new StringBuffer();
    StringBuilder log = new StringBuilder();
    Reader reader = new StringReader("create_portfolio samplePort\nadd_Stock samplePort MSFT " +
            "5000.00 " + "2018-11-13,10:00\nadd_stock samplePort MSFT 800 2018-11-13,10:30\nq");
    IStockController controller = new StockController(new MockModel(log, 7.05d),
            new StockView(reader, out));
    expectedOutOutput.append(genereateInitialOutMessage())
            .append(generateRealCreatePortfolio("samplePort"))
            .append("Incorrect command. Please refer " +
                    "documentation for correct syntax.\n")
            .append(getRealContent());

    expectedLogOutput.append(generateMockCreatePortfolio("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "MSFT", 800.0d,
                    LocalDateTime.of(2018, 11, 13, 10, 30)))
            .append(getMockContent("samplePort"));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(expectedLogOutput.toString(), log.toString());
    assertEquals(expectedOutOutput.toString(), out.toString());
  }


  @Test
  public void testAddToPortfolio2() {
    String commandSequence = "create_portfolio samplePort\nadd_stock samplePort MSFT 5000.00 " +
            "2018-11-01,10:00\nadd_stock samplePort GOOG 3499.50 2018-11-01,09:45\nadd_stock " +
            "samplePort FB 2000 2018-11-02,12:30\ncurrent_value samplePort 2019-11-13,10:00\n" +
            "current_value samplePort 2018-11-13,10:00\nq";
    double uniqueCode = 7.05d;
    Reader reader = new StringReader(commandSequence);

    StringBuffer out = new StringBuffer();
    StringBuilder log = new StringBuilder();
    IStockController controller = new StockController(new MockModel(log, uniqueCode),
            new StockView(reader, out));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    expectedLogOutput.append(generateMockCreatePortfolio("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "MSFT", 5000.0d,
                    LocalDateTime.of(2018, 11, 1, 10, 0)))
            .append(getMockContent("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "GOOG", 3499.5d,
                    LocalDateTime.of(2018, 11, 1, 9, 45)))
            .append(getMockContent("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "FB", 2000.0d,
                    LocalDateTime.of(2018, 11, 2, 12, 30)))
            .append(getMockContent("samplePort"))
            .append(getMockCurrentValue(LocalDateTime.of(2018, 11, 13, 10, 0)));
    assertEquals(expectedLogOutput.toString(), log.toString());

    expectedOutOutput.append(genereateInitialOutMessage())
            .append(generateRealCreatePortfolio("samplePort"))
            .append(getRealContent())
            .append(getRealContent())
            .append(getRealContent())
            .append("Future Date not allowed. Enter command again\n")
            .append("Current Value for samplePorton 2018-11-13T10:00: ")
            .append(uniqueCode)
            .append("\n");
    assertEquals(expectedOutOutput.toString(), out.toString());
  }


  @Test
  public void testAddToPortfolio3() {
    String commandSequence = "create_portfolio samplePort\nadd_stock samplePort MSFT 5000.00" +
            " 2018-11-01,10:00\nadd_stock samplePort GOOG 3499.50 2018-11-01,09:45\nadd_stock" +
            " samplePort FB 2000 2018-11-02,12:30\ncurrent_value samplePort 2018-11-13,10:00\nq";
    Reader reader = new StringReader(commandSequence);
    double uniqueCode = 7.05d;

    StringBuffer out = new StringBuffer();
    StringBuilder log = new StringBuilder();
    IStockController controller = new StockController(new MockModel(log, uniqueCode),
            new StockView(reader, out));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    expectedLogOutput.append(generateMockCreatePortfolio("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "MSFT", 5000.0d,
                    LocalDateTime.of(2018, 11, 1, 10, 0)))
            .append(getMockContent("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "GOOG", 3499.5d,
                    LocalDateTime.of(2018, 11, 1, 9, 45)))
            .append(getMockContent("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "FB", 2000.0d,
                    LocalDateTime.of(2018, 11, 2, 12, 30)))
            .append(getMockContent("samplePort"))
            .append(getMockCurrentValue(LocalDateTime.of(2018, 11, 13, 10, 0)));
    assertEquals(expectedLogOutput.toString(), log.toString());


    expectedOutOutput.append(genereateInitialOutMessage())
            .append(generateRealCreatePortfolio("samplePort"))
            .append(getRealContent())
            .append(getRealContent())
            .append(getRealContent())
            .append("Current Value for samplePorton 2018-11-13T10:00: ")
            .append(uniqueCode)
            .append("\n");
    assertEquals(expectedOutOutput.toString(), out.toString());
  }

  @Test
  public void testAddToPortfolio4() {
    String commandSequence = "create_portfolio samplePort\nadd_stock samplePort MSFT 5000.00 " +
            "2018-11-01,10:00\nadd_stock samplePort GOOG 3499.50 2018-11-01,09:45\nadd_stock " +
            "samplePort FB " +
            "2000 2018-11-02,12:30\ncost_basis samplePort\nq";
    Reader reader = new StringReader(commandSequence);
    double uniqueCode = 7.05d;

    StringBuffer out = new StringBuffer();
    StringBuilder log = new StringBuilder();
    IStockController controller = new StockController(new MockModel(log, uniqueCode),
            new StockView(reader, out));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    expectedLogOutput.append(generateMockCreatePortfolio("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "MSFT", 5000.0d,
                    LocalDateTime.of(2018, 11, 1, 10, 0)))
            .append(getMockContent("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "GOOG", 3499.5d,
                    LocalDateTime.of(2018, 11, 1, 9, 45)))
            .append(getMockContent("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "FB", 2000.0d,
                    LocalDateTime.of(2018, 11, 2, 12, 30)))
            .append(getMockContent("samplePort"))
            .append("Cost basis of samplePort\n");
    assertEquals(expectedLogOutput.toString(), log.toString());


    expectedOutOutput.append(genereateInitialOutMessage())
            .append(generateRealCreatePortfolio("samplePort"))
            .append(getRealContent())
            .append(getRealContent())
            .append(getRealContent())
            .append("Cost basis for samplePort: ")
            .append(uniqueCode)
            .append("\n");
    assertEquals(expectedOutOutput.toString(), out.toString());
  }

  @Test
  public void testAddToPortfolio5() {
    String commandSequence = "create_portfolio samplePort\nadd_stock samplePort MSFT 5000.00 " +
            "2018-11-14,10:00\ncreate_portfolio techPort\nadd_stock techPort GOOG 10000.00 " +
            "2018-11-13,09:30\nadd_stock samplePort FB 2000.0 2018-11-02,12:30\nexamine_portfolio" +
            " -all\nq";
    Reader reader = new StringReader(commandSequence);
    double uniqueCode = 7.05d;

    StringBuffer out = new StringBuffer();
    StringBuilder log = new StringBuilder();
    IStockController controller = new StockController(new MockModel(log, uniqueCode)
            , new StockView(reader, out));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    expectedLogOutput.append(generateMockCreatePortfolio("samplePort"))
            .append(generateMockAddToPortfolio("samplePort", "MSFT", 5000.0d
                    , LocalDateTime.of(2018, 11, 14, 10, 0)))
            .append(getMockContent("samplePort"))
            .append(generateMockCreatePortfolio("techPort"))
            .append(generateMockAddToPortfolio("techPort", "GOOG", 10000.0d
                    , LocalDateTime.of(2018, 11, 13, 9, 30)))
            .append(getMockContent("techPort"))
            .append(generateMockAddToPortfolio("samplePort", "FB", 2000.0d
                    , LocalDateTime.of(2018, 11, 2, 12, 30)))
            .append(getMockContent("samplePort")).append("Called toString \n");
    assertEquals(expectedLogOutput.toString(), log.toString());


    expectedOutOutput.append(genereateInitialOutMessage())
            .append(generateRealCreatePortfolio("samplePort"))
            .append(getRealContent())
            .append(generateRealCreatePortfolio("techPort"))
            .append(getRealContent())
            .append(getRealContent())
            .append("Called toString");

    assertEquals(expectedOutOutput.toString(), out.toString());
  }

  private String getMockCurrentValue(LocalDateTime date) {
    return "Current value of samplePort on " + date.toString() + "\n";
  }

  private String getRealContent() {
    return "Return portfolio content.\n";
  }

  private String getMockContent(String name) {
    return "Content of " + name + "\n";
  }

  private String genereateInitialOutMessage() {
    return "Welcome to Virtual Trading!\nPlease refer the documentation for using commands.\n";
  }

  private String generateRealCreatePortfolio(String portfolioName) {
    return "IPortfolio created: " + portfolioName + ".\n";
  }

  private String generateMockAddToPortfolio(String portfolio, String ticker, double amount
          , LocalDateTime date) {
    return "Add to portfolio: " + portfolio + ", Ticker: " + ticker +
            ", with $" + amount + " on " + date + "\n";
  }

  private String generateMockCreatePortfolio(String portfolioName) {
    return "Create portfolio: " + portfolioName + "\n";
  }
}
