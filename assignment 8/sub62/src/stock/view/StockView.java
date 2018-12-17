package stock.view;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A class that implements the IStockView interface. This is a text based interface which accepts
 * inputs and displays output onto the Readable and Appendable objects.
 */
public class StockView implements IStockView {

  //private final Readable in;
  private final Appendable out;
  private final Scanner scanner;

  /**
   * Constructs a view object given an input and output source.
   *
   * @param in  the input source to accept user inputs.
   * @param out the output source to display outputs.
   */
  public StockView(Readable in, Appendable out) {
    this.out = out;
    scanner = new Scanner(in);
  }

  @Override
  public String read() throws IOException {
    //Scanner scanner = new Scanner(in);
    try {
      return scanner.next();
    } catch (NoSuchElementException e) {
      throw new IOException("Incomplete parameters. Enter command again.\n");
    }
  }

  @Override
  public void write(String output) throws IOException {
    out.append(output);
  }

  @Override
  public void flush() {
    scanner.nextLine();
  }
}
