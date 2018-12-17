import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import stock.controller.IStockController;
import stock.controller.StockController;
import stock.model.DollarCostStrategy;
import stock.model.IPortfolio;
import stock.model.IStock;
import stock.model.PersistPortfolioJSON;
import stock.model.PersistPortfolioJSONImpl;
import stock.model.PersistStrategyJSON;
import stock.model.PersistStrategyJSONImpl;
import stock.model.PersistentPortfolioManagerModel;
import stock.model.PortfolioImpl;
import stock.model.PortfolioManagerModel;
import stock.model.RegularStrategy;
import stock.model.StockAdapter;
import stock.model.StockImpl;
import stock.model.Strategy;
import stock.view.StockView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class PersistanceTests {

  private PersistPortfolioJSON persistPortfolioJSON;

  @Before
  public void setup() {
    persistPortfolioJSON = new PersistPortfolioJSONImpl(Paths.get("res",
            "persisted", "portfolio").toString());
    PersistStrategyJSON strategyJSON = new PersistStrategyJSONImpl(Paths.get("res",
            "persisted", "portfolio").toString());
  }

  @Test
  public void testSavePortfolio() {
    IPortfolio portfolio = new PortfolioImpl("FANG");
    portfolio.add("MSFT", 5000, LocalDateTime
            .of(2018, 11, 14, 10, 30), 1.5);
    portfolio.add("GOOG", 5000, LocalDateTime
            .of(2018, 11, 14, 10, 30), 1.5);
    portfolio.add("FB", 5000, LocalDateTime
            .of(2018, 11, 14, 10, 30), 1.5);
    IStock stock = new StockImpl("MSFT", 140.00,
            LocalDateTime.of(2018, 11, 14, 10, 30));
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            //.setDateFormat("yyyy-MM-ddTHH:mm")
            .registerTypeAdapter(IStock.class, new StockAdapter())
            //.registerTypeAdapter(IStock.class, new StockInstanceCreator())
            .enableComplexMapKeySerialization().create();
    String stockJSon = gson.toJson(stock, StockImpl.class);
    //System.out.println(stockJSon);
    String portfolioJSON = gson.toJson(portfolio, PortfolioImpl.class);
    System.out.println(portfolioJSON);
    IPortfolio portfolio2 = gson.fromJson(portfolioJSON, new TypeToken<PortfolioImpl>() {{
      }}.getType());
    assertEquals(portfolioJSON, gson.toJson(portfolio2));
    persistPortfolioJSON = new PersistPortfolioJSONImpl(Paths.get("res",
            "persisted", "portfolio").toString());
    try {
      persistPortfolioJSON.savePortfolio(portfolio2);
    } catch (IOException e) {
      fail("IOException");
    }
  }

  @Test
  public void testLoadStrategy() {
    Map<String, Double> map = new HashMap<>();
    map.put("msft", 0.50);
    map.put("fb", 0.25);
    map.put("goog", 0.25);
    Strategy dollarCost = new DollarCostStrategy(map, 2000, LocalDateTime
            .of(2017, 11, 14, 10, 30), 30, 1.2, "strat1");
    Gson gson = new GsonBuilder().create();
    String stratSerial = gson.toJson(dollarCost, DollarCostStrategy.class);
    System.out.println(stratSerial);
    Strategy deserialStrat = gson.fromJson(stratSerial, new TypeToken<DollarCostStrategy>() {{
      }}.getType());
    assertEquals(stratSerial, gson.toJson(deserialStrat, DollarCostStrategy.class));
    JsonParser parser = new JsonParser();
    JsonObject jsonObject = parser.parse(stratSerial).getAsJsonObject();
    String type = jsonObject.get("TYPE").getAsString();
    if (type.equalsIgnoreCase("DollarCost")) {
      deserialStrat = gson.fromJson(stratSerial, new TypeToken<DollarCostStrategy>() {{
        }}.getType());
    }
    assertEquals(stratSerial, gson.toJson(deserialStrat, DollarCostStrategy.class));
  }

  @Test
  public void testReadJSON() {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(IStock.class, new StockAdapter())
            .enableComplexMapKeySerialization()
            .create();
    String portfolioJSON = "";
    try {
      portfolioJSON = new String(Files.readAllBytes(Paths.get("C:\\Users\\saurabh\\" +
              "IdeaProjects\\assignment-8-2\\test\\sample.json")));
      IPortfolio portfolio = gson.fromJson(portfolioJSON, new TypeToken<PortfolioImpl>() {{
        }}.getType());
      assertEquals(portfolioJSON, gson.toJson(portfolio));
    } catch (IOException e) {
      throw new IllegalArgumentException("File path doesn't exist.\n");
    }
  }

  @Test
  public void testControllerLoadPortBuy() {
    String stratPath = "C:\\Users\\saurabh\\IdeaProjects\\assignment-8-2\\res\\persisted" +
            "\\strat2.json";
    Reader reader = new StringReader("add_stock\ny\nC:\\Users\\saurabh\\IdeaProjects" +
            "\\assignment-8-2\\res\\persisted\\portfolio\\FANG.json\n" + stratPath + "\nq");
    StringBuffer out = new StringBuffer();
    IStockController controller = new StockController(new PortfolioManagerModel(),
            new StockView(reader, out));
    try {
      controller.execute();
    } catch (IOException e) {
      e.printStackTrace();
      fail("Test failed IOException");
    }
  }

  @Test
  public void testSaveStrategy() {
    PersistStrategyJSON strategyJSON = new PersistStrategyJSONImpl(Paths.get("res",
            "persisted", "strategy").toString());
    Map<String, Double> map = new HashMap<>();
    map.put("msft", 0.50);
    map.put("fb", 0.25);
    map.put("goog", 0.25);
    Strategy dollarCost = new DollarCostStrategy(map, 2000, LocalDateTime
            .of(2017, 11, 14, 10, 30), 30, 1.2, "sdc1");
    Strategy regular = new RegularStrategy("msft", 2500, 1.5,
            LocalDateTime.of(2017, 11, 14, 10, 30), "sr1");
    try {
      strategyJSON.saveStrategy(dollarCost);
      strategyJSON.saveStrategy(regular);
    } catch (IOException e) {
      fail("io error");
    }
    Strategy dollarCostRetrieved = strategyJSON.loadStrategy("C:\\Users\\saurabh\\" +
            "IdeaProjects\\assignment-8-2\\res\\persisted\\strategy\\sdc1.json");
    Strategy regularRetrieved = strategyJSON.loadStrategy("C:\\Users\\saurabh\\" +
            "IdeaProjects\\assignment-8-2\\res\\persisted\\strategy\\sr1.json");
    assertEquals(dollarCost.toString(), dollarCostRetrieved.toString());
    assertEquals(regular.toString(), regularRetrieved.toString());
  }

  @Test
  public void testSavePortfolioAndLoad() {
    PersistPortfolioJSON portfolioJSON = new PersistPortfolioJSONImpl(Paths.get("res",
            "persisted", "portfolio").toString());
    Map<String, Double> map = new HashMap<>();
    map.put("msft", 0.50);
    map.put("fb", 0.50);
    Strategy dollarCost = new DollarCostStrategy(map, 50000, LocalDateTime
            .of(2017, 11, 14, 10, 30), 30, 0.5, "sdc1");
    Strategy regular = new RegularStrategy("msft", 2500, 1.5,
            LocalDateTime.of(2017, 11, 14, 10, 30), "sr1");
    PersistentPortfolioManagerModel model = new PortfolioManagerModel();
    model.createPortfolio("sam1");
    model.addToPortfolio("sam1", dollarCost);
    model.createPortfolio("sam2");
    model.addToPortfolio("sam2", regular);

    try {
      model.savePortfolio("sam1", portfolioJSON);
      model.savePortfolio("sam2", portfolioJSON);
    } catch (IOException e) {
      fail("io error");
    }
    IPortfolio portfolio1 = portfolioJSON.loadPortfolio("C:\\Users\\saurabh\\" +
            "IdeaProjects\\assignment-8-2\\res\\persisted\\portfolio\\sam1.json");
    IPortfolio portfolio2 = portfolioJSON.loadPortfolio("C:\\Users\\saurabh\\" +
            "IdeaProjects\\assignment-8-2\\res\\persisted\\portfolio\\sam2.json");
    assertEquals(4826.5, portfolio1.getCostBasis(LocalDateTime
            .of(2018, 11, 14, 12, 30)), 0.01);
    //assertEquals(regular.toString(),regularRetrieved.toString());
  }

  @Test
  public void testLoadPortfolioSaved() {
    PersistPortfolioJSON portfolioJSON = new PersistPortfolioJSONImpl(Paths.get("res",
            "persisted", "portfolio").toString());
    Strategy regular = new RegularStrategy("msft", 2500, 1.5,
            LocalDateTime.of(2017, 11, 14, 10, 30), "sr1");
    PersistentPortfolioManagerModel model = new PortfolioManagerModel();
    String name = model.loadPortfolio("C:\\Users\\saurabh\\IdeaProjects" +
            "\\assignment-8-2" +
            "\\res\\persisted\\portfolio\\FANG.json", portfolioJSON);
    model.addToPortfolio(name, regular);
    double firstVal = model.getPortfolioCostBasis(name, LocalDateTime
            .of(2018, 11, 14, 12, 30));
    try {
      model.savePortfolio(name, portfolioJSON);
      String nameRetrieve = model.loadPortfolio("C:\\Users\\saurabh\\IdeaProjects" +
              "\\assignment-8-2" +
              "\\res\\persisted\\portfolio\\FANG.json", portfolioJSON);
      assertEquals(firstVal,
              model.getPortfolioCostBasis(nameRetrieve, LocalDateTime
                      .of(2018, 11, 14, 12, 30)), 0.001);
    } catch (IOException e) {
      fail("io error");
    }
  }

  @Test
  public void testLoadStrategyAndApply() {
    PersistStrategyJSON persistStrategyJSON = new PersistStrategyJSONImpl(Paths.get("res",
            "persisted", "strategy").toString());
    Strategy strategy = persistStrategyJSON.loadStrategy("C:\\Users\\saurabh\\" +
            "IdeaProjects\\assignment-8-2\\res\\persisted\\strategy\\sr1.json");
    PersistentPortfolioManagerModel model = new PortfolioManagerModel();
    model.createPortfolio("college");
    model.addToPortfolio("college", strategy);
    assertEquals(0.0, model.getPortfolioCostBasis("college", LocalDateTime
            .of(2018, 11, 14, 12, 30)), 0.01);
  }

  @Test(expected = JsonSyntaxException.class)
  public void testInvalidJSON() {
    persistPortfolioJSON.loadPortfolio("C:\\Users\\saurabh\\IdeaProjects\\" +
            "assignment-8-2\\res\\persisted\\portfolio\\invalid.json");
  }

  @Test
  public void testEditNotSavePortfolioLater() {
    PersistentPortfolioManagerModel model = new PortfolioManagerModel();
    model.createPortfolio("happy");
    assertEquals(0.0, model.getPortfolioCostBasis("happy",
            LocalDateTime.now())
            , 0.001);
    Strategy regular = new RegularStrategy("msft", 2500, 1.5,
            LocalDateTime.of(2017, 11, 14, 10, 30), "sr1");
    Strategy regular2 = new RegularStrategy("fb", 2500, 1.5,
            LocalDateTime.of(2017, 11, 14, 10, 30), "sr2");
    model.addToPortfolio("happy", regular);
    assertEquals(3132.0, model.getPortfolioCurrentValue("happy", LocalDateTime
            .now()), 0.001);
    try {
      model.savePortfolio("happy", persistPortfolioJSON);
      double costBasis1 = model.getPortfolioCostBasis("happy",
              LocalDateTime.of(2017, 11, 14, 10, 30));
      model.addToPortfolio("happy", regular2);
      double costBasis2 = model.getPortfolioCostBasis("happy",
              LocalDateTime.of(2017, 11, 14, 10, 30));
      String name = model.loadPortfolio("C:\\Users\\saurabh\\IdeaProjects\\" +
              "assignment-8-2\\res" +
              "\\persisted\\portfolio\\happy.json", persistPortfolioJSON);
      assertEquals(costBasis1, model.getPortfolioCostBasis(name,
              LocalDateTime.of(2017, 11, 14, 10, 30)), 0.001);
      assertNotEquals(costBasis2, model.getPortfolioCostBasis(name,
              LocalDateTime.of(2017, 11, 14, 10, 30)), 0.001);
    } catch (IOException e) {
      fail("io error");
      e.printStackTrace();
    }
  }
}
