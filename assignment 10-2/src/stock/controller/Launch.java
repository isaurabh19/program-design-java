package stock.controller;

import java.io.IOException;
import java.io.InputStreamReader;

import stock.model.PersistentPortfolioManagerModel;
import stock.model.PortfolioManagerModel;
import stock.view.GUIStockView;
import stock.view.IStockView;
import stock.view.MainView;
import stock.view.StockView;

/**
 * The main program that is launched when the application is started. It represents the virtual
 * trading application. It currently supports NYC stock market and Eastern time zone.
 */
public class Launch {

  /**
   * The main method which initialises the data access objects, view, model and controller and
   * passes the control to the controller.
   *
   * @param args arguments to the program.
   * @throws IOException if the input/output sources throw any errors.
   */
  public static void main(String[] args) throws IOException {

    if (args[0].equalsIgnoreCase("-view")) {
      PersistentPortfolioManagerModel model = new PortfolioManagerModel();
      IStockController controller;
      switch (args[1]) {
        case "console":
          IStockView view = new StockView(new InputStreamReader(System.in), System.out);
          controller = new StockController(model, view);
          controller.execute();
          break;
        case "gui":
          GUIStockView viewGUI = new MainView("Virtual Trader.");
          controller = new StockControllerGUI(model, viewGUI);
          controller.execute();
          break;
        default:
          System.out.println("Invalid view parameter. System closing.\n");
      }
    }
  }
}