
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

  /**
   * sets up the model for controller.
   */
  @Before
  public void setController() {
    portfolioManagerModel = new PortfolioManagerModel();
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
    assertEquals(welcome + instructions + portfolioCreated
            + " samplePort.\n" + instructions
            + "Portfolio exists. Enter a unique name.\n" + instructions, stringBuffer.toString());
  }

  @Test
  public void testAddStocksToPortfolioHoliday() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock samplePort\n" +
            "regular\nMSFT\n" + "5000\n" + "0.3\n" + "\n2018\n11\n11\n10\n30\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(welcome + instructions + portfolioCreated
            + " samplePort.\n" + instructions
            + strategyChoice + regular + amount + commission + year
            + month + day + hour + minutes +
            "Enter business times.\n" + instructions, stringBuffer.toString());
  }

  private final String year = "Enter the year in 4 digits. e.g 2018\n";
  private final String month = "Enter the month number from 1-12. e.g 11\n";
  private final String day = "Enter day of the month starting from 1 " +
          "till lastday the month can" +
          " have. E.g 25\n";
  private final String amount = "Enter the amount.\n";
  private final String commission = "Enter commission fees as absolute value.\n";
  private final String hour = "Enter hours in 24 hr format\n";
  private final String minutes = "Enter minutes between 00-60\n";
  private final String regular = "To add via a regular strategy," +
          " enter the ticker symbol of the " +
          "stock.\n";

  @Test
  public void testAddStocksToPortfolioAfterHours() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock samplePort " +
            "regular\nMSFT\n" +
            "5000.00\n" + "2.5\n" + "2018\n11\n13\n18\n00\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(welcome + instructions + portfolioCreated
            + " samplePort.\n" + instructions
            + strategyChoice + regular + amount + commission + year
            + month + day + hour + minutes +
            "Enter business times.\n" + instructions, stringBuffer.toString());
  }

  @Test
  public void testAddStocksToPortfolioIncorrectSymbol() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock samplePort " +
            "regular\nMSFTE\n" + "5000.00\n4\n" + "\n2018\n11\n13\n12\n30\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(welcome + instructions + portfolioCreated
            + " samplePort.\n" + instructions
            + strategyChoice + regular + amount + commission + year
            + month + day + hour + minutes +
            "Enter valid ticker symbol.\n" + instructions, stringBuffer.toString());

  }

  @Test
  public void testAddStocksToPortfolioZeroAmount() {
    Reader reader = new StringReader("create_portfolio samplePort\nadd_stock samplePort " +
            "regular\nMSFT\n" + "00.00\n\n1000\n3\n " + "2018\n11\n13\n12\n30\nq");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found.");
    }
    assertEquals(welcome + instructions + portfolioCreated +
            " samplePort.\n" + instructions
            + strategyChoice + regular + amount + "Expecting non zero " +
            "amount as decimals. Enter again\n"
            + commission + year + month + day + hour + minutes +
            stockSuccess + instructions, stringBuffer.toString());
  }

  private final String stockSuccess = "Stocks added successfully." +
          " You can examine the portfolio to" +
          " see the stocks.\n";


  @Test
  public void testGetCostBasis() {
    String commandSequence = "create_portfolio samplePort\nadd_stock samplePort regular\n" +
            "MSFT\n5000.00\n1.25\n" + "2018\n11\n01\n10\n00\nadd_stock samplePort regular\n" +
            "GOOG\n3499.50\n1\n2018\n11\n01\n09\n45\nadd_stock samplePort regular\nFB\n" +
            "2000\n2\n2018\n11\n02\n12\n30\ncost_basis samplePort 2018\n11\n02\nq";
    Reader reader = new StringReader(commandSequence);
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found");
    }
    String output = welcome + instructions + portfolioCreated + " samplePort.\n" +
            instructions + strategyChoice + "To add via a regular strategy, enter the ticker " +
            "symbol of the" + " stock.\n" + amount + commission + year + month + day + hour +
            minutes + stockSuccess + instructions + strategyChoice + "To add via a regular " +
            "strategy, enter the ticker symbol of the " + "stock.\n" + amount + commission + year
            + month + day + hour + minutes + stockSuccess + instructions + strategyChoice + "To" +
            " add via a regular strategy, enter the ticker symbol of the " + "stock.\n" + amount +
            commission + year + month + day + hour + minutes + stockSuccess + instructions
            + year + month + day + "Cost basis for samplePort: 10142.789999999999\n" +
            instructions;
    assertEquals(output, stringBuffer.toString());
  }

  @Test
  public void testGetCurrentValue() {
    String commandSequence = "create_portfolio samplePort\nadd_stock samplePort regular\n" +
            "MSFT\n5000.00\n1.25\n" + "2018\n11\n01\n10\n00\nadd_stock samplePort regular\n" +
            "GOOG\n3499.50\n1\n2018\n11\n01\n09\n45\nadd_stock samplePort regular\nFB\n" +
            "2000\n2\n2018\n11\n02\n12\n30\ncurrent_value samplePort 2018\n11\n02\nq";
    Reader reader = new StringReader(commandSequence);
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("Should not have failed. IOException found");
    }
    String output = welcome + instructions + portfolioCreated + " samplePort.\n" +
            instructions + strategyChoice + "To add via a regular strategy, enter the ticker " +
            "symbol of the" + " stock.\n" + amount + commission + year + month + day + hour +
            minutes + stockSuccess + instructions + strategyChoice + "To add via a regular " +
            "strategy, enter the ticker symbol of the " + "stock.\n" + amount + commission + year
            + month + day + hour + minutes + stockSuccess + instructions + strategyChoice + "To" +
            " add via a regular strategy, enter the ticker symbol of the " + "stock.\n" + amount +
            commission + year + month + day + hour + minutes + stockSuccess + instructions
            + year + month + day + "Current Value for samplePorton 2018-11-02T16:00: " +
            "10117.439999999999\n" +
            instructions;
    assertEquals(output, stringBuffer.toString());
  }


  @Test
  public void testQuitInMiddle() {
    Reader reader = new StringReader("create_portfolio college\nq\nadd_stock " +
            "college regular\n" +
            "msft\n200\n0.2\n " + "2018\n11\n13\n13\n20");
    StringBuffer stringBuffer = new StringBuffer();
    controller = new StockController(portfolioManagerModel, new StockView(reader, stringBuffer));
    try {
      controller.execute();
    } catch (IOException e) {
      fail("should not fail");
    }
    assertEquals(welcome + instructions + portfolioCreated + " college.\n" + instructions
            , stringBuffer.toString());
  }

  private final String welcome = "Welcome to Virtual Trading!\n" +
          "Please refer the documentation for using commands.\n";
  private final String instructions =
          "1. To create a portfolio, type 'create_portfolio' followed by unique portfolio name" +
                  " of your choice.\n" +
                  " 2. To add stock use 'add_stock' followed by portfolio name. " +
                  "Enter further details as" +
                  " asked.\n" +
                  "3. To examine portfolio, enter 'examine_portfolio' followed by" +
                  " portfolio name. Enter" +
                  " further details as asked.\n" +
                  "4. To view cost basis or current value, enter 'cost_basis' or" +
                  " 'current_value' followed" +
                  " by portfolio name. Enter further details as asked.\n";
  private final String portfolioCreated = "Portfolio created:";
  private final String strategyChoice = "Enter strategy: 'regular' or 'dollar_cost'\n";


}

