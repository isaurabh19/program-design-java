import org.junit.Test;

import java.time.LocalDateTime;

import stock.model.IPortfolio;
import stock.model.PortfolioImpl;

import static org.junit.Assert.assertEquals;

public class DAOcsvTest {

  @Test
  public void test() {
    IPortfolio testPortfolio = new PortfolioImpl("Nachiket1");
    testPortfolio.add("GOOG", 5000.24d,
            LocalDateTime.of(2017, 1, 10, 10, 0),
            5d);
    assertEquals("Bought 6 stocks of GOOG at $804.79 " +
            "each on 2017-01-10T10:00 with commission fee of $5.0\n", testPortfolio.toString());
    testPortfolio.add("MSFT", 5000.24d,
            LocalDateTime.of(2017, 1, 10, 14, 0),
            50d);
    assertEquals("Bought 6 stocks of GOOG at $804.79 each on 2017-01-10T10:00 " +
                    "with commission fee of $5.0" +
                    "\nBought 79 stocks of MSFT at $62.62 each on 2017-01-10T14:00 with" +
                    " commission fee of $50.0\n",
            testPortfolio.toString());
    testPortfolio.add("AMZN", 8800.24d,
            LocalDateTime.of(2017, 2, 3, 9, 0),
            20d);
    assertEquals("Bought 6 stocks of GOOG at $804.79 each on 2017-01-10T10:00 " +
                    "with commission fee of $5.0\n" +
                    "Bought 79 stocks of MSFT at $62.62 each on 2017-01-10T14:00 with" +
                    " commission fee of $50.0\n" +
                    "Bought 10 stocks of AMZN at $810.2 each on 2017-02-03T09:00 with" +
                    " commission fee of $20.0\n",
            testPortfolio.toString());

    assertEquals(30976.26, testPortfolio.getValueByDate(LocalDateTime
            .of(2018, 11, 13, 9, 0)), 0.01);

    assertEquals(17877.72, testPortfolio.getCostBasis(LocalDateTime.now()), 0.01d);
  }

}