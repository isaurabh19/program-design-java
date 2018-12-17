package stock.controller;

import java.io.IOException;

/**
 * The controller interface for the stock trading program. The function designed gives control to
 * the controller to accept inputs from the view and pass them to the model and take output from the
 * model and pass it to the view.
 */
public interface IStockController {

  /**
   * Starts the program with the given model and calls appropriate model functions based on user
   * inputs.
   *
   * @throws IOException if input/output sources throw errors.
   */
  void execute() throws IOException;
}
