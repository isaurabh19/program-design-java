package stock.controller;

import java.io.IOException;
import java.io.InputStreamReader;

import stock.model.IPortfolioManagerModel;
import stock.model.PortfolioManagerModel;
import stock.view.IStockView;
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

    IStockView view = new StockView(new InputStreamReader(System.in), System.out);
    IPortfolioManagerModel model = new PortfolioManagerModel();
    IStockController controller = new StockController(model, view);
    controller.execute();
  }
}