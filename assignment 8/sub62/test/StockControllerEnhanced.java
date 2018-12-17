import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import stock.controller.IStockController;
import stock.controller.StockController;
import stock.model.PortfolioManagerModel;
import stock.view.StockView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StockControllerEnhanced {
  private IStockController controller;

  @Test
  public void testCreatePortfolio() {
    Reader reader = new StringReader("create_portfolio FANG\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    assertEquals(welcome + instructions + portfolioCreated + " FANG.\n" + instructions,
            out.toString());
  }

  @Test
  public void testAddStockDCEqualWeight() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\ndollar_cost" +
            "\nMSFT\n" +
            "GOOG\nFB\nd\ne\n2016\n11\n12\n2018\n11\n12\n50000\n5\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
            strategyChoice + enterTickers + weightScheme + dateEnter + year + month + day
            + endDate +
            year + month + day + amount + commission + frequency + stockSuccess + instructions,
            out.toString());
  }

  @Test
  public void testAddStockDCCustomWeight() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\ndollar_cost" +
            "\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n30\n20\n2016\n11\n12\n2018\n11\n12\n50000\n5\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n"
            + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue + dateEnter
            + year + month + day + endDate +
            year + month + day + amount + commission + frequency + stockSuccess
            + instructions, out.toString());
  }

  @Test
  public void testAddStockIncorrectWeight() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\ndollar_cost" +
            "\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n25\n20\n2016\n11\n12\n2018\n11\n12\n50000\n5\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue + dateEnter
            + year + month + day + endDate +
            year + month + day + amount + commission + frequency + stockSuccess
            + instructions, out.toString());
  }

  @Test
  public void testAddStockIncorrectWeightOverflow() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\ndollar_cost" +
            "\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n25\n35\n2016\n11\n12\n2018\n11\n12\n50000\n5\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n"
            + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue
            + "Only allowed value for this" +
            " weight is 25.0 hence taken thatas the value.\n" + dateEnter + year
            + month + day + endDate +
            year + month + day + amount + commission + frequency + stockSuccess
            + instructions, out.toString());
  }

  @Test
  public void testAddStockRegular() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\nregular\nMSFT\n" +
            "2000\n1.25\n2018\n11\n14\n10\n30\nadd_stock MAFG\nregular\nGOOG\n10238.23\n3.24\n" +
            "2018\n11\n15\n13\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String hour = "Enter hours in 24 hr format\n";
    String minutes = "Enter minutes between 00-60\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
                    strategyChoice + "To add via a regular strategy, enter the " +
                    "ticker symbol of the stock.\n"
                    + amount + commission + year + month + day + hour + minutes
                    + stockSuccess + instructions +
                    strategyChoice + "To add via a regular strategy, enter the " +
                    "ticker symbol of the stock.\n"
                    + amount + commission + year + month + day + hour + minutes
                    + stockSuccess + instructions
            , out.toString());
  }

  @Test
  public void testAddStockNoEndDate() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\ndollar_cost" +
            "\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n30\n20\n2016\n11\n12\ncurrent\n50000\n5\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue + dateEnter
            + year + month + day + endDate +
            year + amount + commission + frequency + stockSuccess + instructions, out.toString());
  }

  @Test
  public void testTypoInCommand() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\ndollar_Cost\n" +
            "dollar_cost\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n30\n20\n2016\n11\n12\ncurrent\n50000\n5\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
            strategyChoice + "Incorrect strategy command.\n" +
            " Enter strategy command again.\n" + enterTickers + weightScheme + weightsValue
            + dateEnter +
            year + month + day + endDate +
            year + amount + commission + frequency + stockSuccess + instructions, out.toString());
  }

  @Test
  public void testWeightMoreThanHundredLessThanZero() {
    Reader reader = new StringReader("create_portfolio MAFG\n" +
            "add_stock MAFG\ndollar_cost\nMSFT\n" +
            "GOOG\nFB\nd\nc\n150\n50\n30\n-10\n0\n20\n2016\n11\n12\ncurrent\n50000\n5\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\nValue must be between 1-100.\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\nValue must be between 1-100.\nValue must be between 1-100.\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue + dateEnter +
            year + month + day + endDate +
            year + amount + commission + frequency + stockSuccess + instructions, out.toString());
  }

  @Test
  public void testAddDCEndDateBeforeStartDate() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\ndollar_cost" +
            "\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n30\n20\n2016\n11\n12\n2014\n11\n12\n2018\n11\n12\n50000" +
            "\n5\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue + dateEnter
            + year + month + day + endDate +
            year + month + day + "End date cannot be before start date.\n" + endDate +
            year + month + day + amount + commission + frequency + stockSuccess
            + instructions, out.toString());

  }

  @Test
  public void testAddDCAmountSmallerThan1Stock() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\ndollar_cost" +
            "\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n30\n20\n2016\n11\n12\ncurrent\n50\n1\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n"
            + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue + dateEnter
            + year + month + day + endDate +
            year + amount + commission + frequency + "Insufficient funds.\n" +
            instructions, out.toString());
  }

  @Test
  public void testSingleStockDC() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\n" +
            "dollar_cost\nMSFT" +
            "\nd\nc\n100\n2016\n11\n12\n2018\n11\n12\n50000\n5\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n"
                    + instructions +
                    strategyChoice + enterTickers + weightScheme + weightsValue
                    + dateEnter + year + month
                    + day + endDate +
                    year + month + day + amount + commission + frequency
                    + stockSuccess + instructions
            , out.toString());
  }

  @Test
  public void testAddStockDCZeroCommission() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock " +
            "MAFG\ndollar_cost\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n30\n20\n2016\n11\n12\n2018\n11\n12\n50000\n0\n1\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";

    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue + dateEnter + year + month
            + day + endDate + year + month + day + amount + commission
            + "Expecting non zero amount " +
            "as" +
            " decimals. Enter again\n" + frequency + stockSuccess +
            instructions, out.toString());
  }

  @Test
  public void testAddStockDCInvalidFrequency() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG" +
            "\ndollar_cost\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n30\n20\n2016\n11\n12\n2018\n11\n12\n50000\n5\n-1\n30\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue
            + dateEnter + year + month + day + endDate +
            year + month + day + amount + commission + frequency
            + "Expecting non zero amount as " +
            "decimals. Enter again\n" + stockSuccess + instructions, out.toString());
  }

  @Test
  public void testCostBasisDate() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG" +
            "\ndollar_cost\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n30\n20\n2016\n11\n12\n2018\n11\n12\n50000\n5\n30\ncost_basis " +
            "MAFG\n2018\n11\n12\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue
            + dateEnter + year + month + day + endDate +
            year + month + day + amount + commission + frequency + stockSuccess + instructions
            + year + month + day + "Cost basis for MAFG: 1237377.51\n"
            + instructions, out.toString());
  }

  @Test
  public void testCostValueByDate() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\n" +
            "dollar_cost\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n30\n20\n2016\n11\n12\n2018\n11\n12\n50000\n5\n" +
            "30\ncurrent_value " +
            "MAFG\n2018\n11\n12\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue
            + dateEnter + year + month + day + endDate +
            year + month + day + amount + commission + frequency
            + stockSuccess + instructions
            + year + month + day + "Current Value for MAFGon 2018-11-12T16:00: " +
            "1450052.9800000002\n" + instructions, out.toString());
  }

  @Test
  public void examinePortfolioNameByDate() {
    Reader reader = new StringReader("create_portfolio MAFG\nadd_stock MAFG\n" +
            "dollar_cost\nMSFT\n" +
            "GOOG\nFB\nd\nc\n50\n30\n20\n2016\n11\n12\n2018\n11\n12\n50000\n5\n" +
            "30\nexamine_portfolio " +
            "MAFG\n2018\n11\n12\nq");
    StringBuffer out = new StringBuffer();
    controller = new StockController(new PortfolioManagerModel(), new StockView(reader, out));
    try {
      controller.execute();

    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
    String weightsValue = "Enter weight as percent value between 0-100 for each symbol.Enter " +
            "weights for:MSFT\n" +
            "Enter weights for:GOOG\n" +
            "Enter weights for:FB\n";
    assertEquals(welcome + instructions + portfolioCreated + " MAFG.\n" + instructions +
            strategyChoice + enterTickers + weightScheme + weightsValue + dateEnter + year
            + month + day + endDate +
            year + month + day + amount + commission + frequency + stockSuccess + instructions
            + year + month + day + "Stock\t\t\tQuantity\n" +
            "====================================================\n" +
            "MSFT\t\t\t7840\n" +
            "GOOG\t\t\t376\n" +
            "FB\t\t\t1566\n" + instructions, out.toString());
  }

  private final String welcome = "Welcome to Virtual Trading!\n" +
          "Please refer the documentation for using commands.\n";
  private final String instructions =
          "1. To create a portfolio, type 'create_portfolio' followed by unique portfolio name" +
                  " of your choice.\n" +
                  " 2. To add stock use 'add_stock' followed by portfolio name. Enter further" +
                  " details as" +
                  " asked.\n" +
                  "3. To examine portfolio, enter 'examine_portfolio' followed by " +
                  "portfolio name." +
                  " Enter" +
                  " further details as asked.\n" +
                  "4. To view cost basis or current value, enter 'cost_basis' or " +
                  "'current_value' followed" +
                  " by portfolio name. Enter further details as asked.\n";
  private final String portfolioCreated = "Portfolio created:";
  private final String strategyChoice = "Enter strategy: 'regular' or 'dollar_cost'\n";
  private final String enterTickers = "To add via dollar cost stratgey,Start by" +
          " entering ticker " +
          "symbols on new line. Enter d or D when done.\n";
  private final String weightScheme =
          "Choose weighting scheme: Enter e for equal or c for custom.\n";
  private final String dateEnter = "Enter start date as asked.\n";
  private final String year = "Enter the year in 4 digits. e.g 2018\n";
  private final String month = "Enter the month number from 1-12. e.g 11\n";
  private final String day = "Enter day of the month starting from 1 till lastday" +
          " the month can" +
          " have. E.g 25\n";
  private final String endDate = "Enter end date as asked, if no end date then " +
          "enter 'current'.\n";
  private final String amount = "Enter the amount.\n";
  private final String commission = "Enter commission fees as absolute value.\n";
  private final String frequency = "Enter the frequency of execution as number of days.\n";
  private final String stockSuccess = "Stocks added successfully. You can examine" +
          " the portfolio to" +
          " see the stocks.\n";
}
