package stock.view;

import java.io.IOException;

/**
 * The view interface for virtual trading application. It provides methods to send user input to the
 * controller and accept input from the controller to output onto the view.
 */
public interface IStockView {

  /**
   * Reads the input from the input source and returns it as string to the controller.
   * @return the input from the source.
   * @throws IOException if input source throws any error.
   */
  String read() throws IOException;

  /**
   * Writes the output from the controller onto the corresponding output source.
   * @param output the string to be output on the view.
   * @throws IOException if the output source throws any error.
   */
  void write(String output) throws IOException;

  /**
   * Flushes the input source from the current point. Mostly used if corrupt input encountered
   * results in ignoring the next inputs.
   */
  void flush();
}
