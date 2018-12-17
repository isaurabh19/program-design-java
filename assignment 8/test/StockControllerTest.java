import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import stock.controller.IStockController;
import stock.controller.StockController;
import stock.model.IPortfolioManagerModel;
import stock.model.PortfolioManagerModel;
import stock.view.IStockView;
import stock.view.StockView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StockControllerTest {

  private IStockController controller;
  private IPortfolioManagerModel portfolioManagerModel;
  private IStockView view;

  private String message = "Welcome to Virtual Trading!\nPlease refer the documentation for " +
          "using commands.\n";

  @Before
  public void setController() {
    portfolioManagerModel = new PortfolioManagerModel();
  }

  @Test
  public void testValidPortfolioCreation() {
    Reader reader = new StringReader("create_portfolio samplePort\nquit");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(message + "IPortfolio created: samplePort.\n", stringBuffer.toString());
  }

  @Test
  public void testNameAlreadyExists() {
    Reader reader = new StringReader("create_portfolio samplePort\ncreate_portfolio " +
            "samplePort\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(message + "IPortfolio created: samplePort.\nIPortfolio exists. Enter a" +
            " unique name.\n", stringBuffer.toString());
  }

  @Test
  public void testTypoInCommandCreatePortfolio() {
    Reader reader = new StringReader("create_Portfolio sample\ncreate_portfolio samplePort\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(message + "Incorrect command. Please refer documentation for correct" +
                    " syntax.\nIPortfolio created: samplePort.\n",
            stringBuffer.toString());
  }

  @Test
  public void testAddStocksToPortfolio() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock samplePort " +
            "MSFT 5340.75 " + "2010-11-15,10:00\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    //find out the number of stocks.
    assertEquals(message + "IPortfolio created: samplePort.\nBought 203 stocks of MSFT at" +
            " $26.2 " +
            "each on 2010-11-15T10:00\n", stringBuffer.toString());
  }

  @Test
  public void testTypoInCommandAddToPortfolio() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_Stock samplePort MSFT " +
            "5000.00 " + "2018-11-13,10:00\nadd_stock samplePort MSFT 800 2018-11-13,10:30\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(message + "IPortfolio created: samplePort.\nIncorrect command. Please " +
            "refer documentation for correct syntax.\n" +
            "Bought 7 stocks of MSFT at $106.94 each on 2018-11-13T10:30\n", stringBuffer
            .toString());
  }

  @Test
  public void testIncompleteParameters() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock MSFT " +
            "5000.00 " + "2018-11-13,10:00\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(message + "IPortfolio created: samplePort.\n" + "Ticker symbol has to be " +
            "alphabets only. Enter the command again.\n" + "Incorrect command. Please refer " +
            "documentation for correct syntax.\n", stringBuffer.toString());
  }

  @Test
  public void testAddStocksToPortfolioHoliday() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock samplePort MSFT" +
            " 5 " + "2018-11-11,10:00\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(message + "IPortfolio created: samplePort.\n" +
            "Enter business times.\n", stringBuffer.toString());
  }

  @Test
  public void testAddStocksToPortfolioAfterHours() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock samplePort MSFT " +
            "5000.00 " + "2018-11-13,18:30\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(message + "IPortfolio created: samplePort.\n" +
            "Enter business times.\n", stringBuffer.toString());
  }

  @Test
  public void testAddStocksToPortfolioIncorrectSymbol() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock samplePort MSFTE " +
            "5000.00 " + "2018-11-13,12:30\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(message + "IPortfolio created: samplePort.\n" +
            "Enter valid ticker symbol.\n", stringBuffer.toString());
  }

  @Test
  public void testAddStocksToPortfolioZeroAmount() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock samplePort MSFT " +
            "00.00 " + "2018-11-13,12:30\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(message + "IPortfolio created: samplePort.\n" +
            "Expecting non zero amount as decimals.\n", stringBuffer.toString());
  }

  @Test
  public void testAddStocksToPortfolioWrongPortfolioName() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock techPort MSFT " +
            "5000.00 " + "2018-11-13,12:30\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(message + "IPortfolio created: samplePort.\n" +
            "IPortfolio name does not exist.\n", stringBuffer.toString());
  }

  @Test
  public void testAddStocksToPortfolioFutureDate() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock samplePort MSFT " +
            "5000.00 " + "2019-11-13,12:30\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(message + "IPortfolio created: samplePort.\nFuture Date not allowed. " +
            "Enter" + " command again\n", stringBuffer.toString());
  }

  @Test
  public void testExaminePortfolioAll() {
    String commandSequence = "create_portfolio samplePort\nadd_stock samplePort MSFT 5000.00 " +
            "2018-11-14,10:00\ncreate_portfolio techPort\nadd_stock techPort GOOG 10000.00 " +
            "2018-11-13,09:30\nexamine_portfolio -all\nq";
    Reader reader = new StringReader(commandSequence);
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found");
    }
    String output = message + "IPortfolio created: samplePort.\nBought 47 stocks of MSFT at $104" +
            ".95 " + "each on 2018-11-14T10:00\n"
            + "IPortfolio created: techPort.\nBought 9 stocks of GOOG at $1036.05 each on " +
            "2018-11-13T09:30\n"
            + "samplePort\ntechPort\n";
    assertEquals(output, stringBuffer.toString());
  }

  @Test
  public void testExaminePortfolioName() {
    String commandSequence = "create_portfolio samplePort\nadd_stock samplePort MSFT 5000.00 " +
            "2018-11-01,10:00\nadd_stock samplePort GOOG 3499.50 2018-11-01,09:45\nadd_stock " +
            "samplePort FB " +
            "2000 2018-11-02,14:30\nexamine_portfolio samplePort\nq";
    Reader reader = new StringReader(commandSequence);
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found");
    }
    String output = message + "IPortfolio created: samplePort.\n" + "Bought 47 stocks of MSFT at " +
            "$105.92" + " each on" + " 2018-11-01T10:00\n" + "Bought 3 stocks of GOOG at $1070.0" +
            " each on" + " 2018-11-01T09:45\n" + "Bought 13 stocks of FB at $150.35 each on" +
            " 2018-11-02T14:30\n" +
            "Bought 47 stocks of MSFT at $105.92 each on" +
            " 2018-11-01T10:00\n" + "Bought 3 stocks of GOOG at $1070.0 each on" +
            " 2018-11-01T09:45\n" + "Bought 13 stocks of FB at $150.35 each on" +
            " 2018-11-02T14:30\n";
    assertEquals(output, stringBuffer.toString());
  }

  @Test
  public void testGetCostBasis() {
    String commandSequence = "create_portfolio samplePort\nadd_stock samplePort MSFT 5000.00 " +
            "2018-11-01,10:00\nadd_stock samplePort GOOG 3499.50 2018-11-01,09:45\nadd_stock " +
            "samplePort FB " +
            "2000 2018-11-02,12:30\ncost_basis samplePort\nq";
    Reader reader = new StringReader(commandSequence);
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found");
    }
    String output = message + "IPortfolio created: samplePort.\nBought 47 stocks of MSFT at " +
            "$105.92 " + "each on 2018-11-01T10:00\n" +
            "Bought 3 stocks of GOOG at $1070.0 each on 2018-11-01T09:45\n" +
            "Bought 13 stocks of FB at $150.35 each on 2018-11-02T12:30\n" +
            "Cost basis for samplePort: 10142.789999999999\n";
    assertEquals(output, stringBuffer.toString());
  }

  @Test
  public void testGetCurrentValue() {
    String commandSequence = "create_portfolio samplePort\nadd_stock samplePort MSFT 5000.00" +
            " 2018-11-01,10:00\nadd_stock samplePort GOOG 3499.50 2018-11-01,09:45\nadd_stock" +
            " samplePort FB 2000 2018-11-02,12:30\ncurrent_value samplePort 2018-11-13,10:00\nq";
    Reader reader = new StringReader(commandSequence);
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found");
    }
    String output = message + "IPortfolio created: samplePort.\n" +
            "Bought 47 stocks of MSFT at $105.92 each on 2018-11-01T10:00\n" +
            "Bought 3 stocks of GOOG at $1070.0 each on 2018-11-01T09:45\n" +
            "Bought 13 stocks of FB at $150.35 each on 2018-11-02T12:30\n" +
            "Current Value for samplePorton 2018-11-13T10:00: 9982.41\n";
    assertEquals(output, stringBuffer.toString());
  }

  @Test
  public void testGetCurrentValueFutureDate() {
    String commandSequence = "create_portfolio samplePort\nadd_stock samplePort MSFT 5000.00 " +
            "2018-11-01,10:00\nadd_stock samplePort GOOG 3499.50 2018-11-01,09:45\nadd_stock " +
            "samplePort FB 2000 2018-11-02,12:30\ncurrent_value samplePort 2019-11-13,10:00\n" +
            "current_value samplePort 2018-11-13,10:00\nq";
    Reader reader = new StringReader(commandSequence);
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found");
    }
    String output = message + "IPortfolio created: samplePort.\nBought 47 stocks of MSFT at $105." +
            "92 " + "each on 2018-11-01T10:00\n" +
            "Bought 3 stocks of GOOG at $1070.0 each on 2018-11-01T09:45\n" +
            "Bought 13 stocks of FB at $150.35 each on 2018-11-02T12:30\n" +
            "Future Date not allowed. Enter command again\n" +
            "Current Value for samplePorton 2018-11-13T10:00: 9982.41\n";
    assertEquals(output, stringBuffer.toString());
  }

  @Test
  public void testQuitInMiddle() {
    Reader reader = new StringReader("create_portfolio college\nq\nadd_stock college msft 200 " +
            "2018-11-13,13:20");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("should not fail");
    }
    assertEquals(message + "IPortfolio created: college.\n", stringBuffer.toString());
  }
}
