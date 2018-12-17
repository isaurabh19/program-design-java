package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import stock.view.IStockView;

/**
 * An helper class to get correct date from the user. Expects the date in yyyy-MM-dd,HH:mm format.
 * Asks to re-enter date if its not valid for the month.
 */

public class AbstractGetDateHelper {
  //kept package private since its only helpful for methods within commands package to get date.

  /**
   * Helper method to get the date from view input.
   * @param view the view from which input is requested.
   * @return a LocalDateTime object from the input string.
   * @throws IOException if the input source throws any error.
   */
  public static LocalDateTime getDateTime(IStockView view) throws IOException {
    String dateTime;
    dateTime = view.read();
    try {
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm");
      LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
      if (!dateTime.replace(',', 'T')
              .equals(localDateTime.toString())) {
        throw new IllegalArgumentException("Invalid day of the month. Enter command again.\n");
      }
      if (localDateTime.isBefore(LocalDateTime.now())) {
        return localDateTime;
      }
    } catch (DateTimeParseException e) {
      view.write("Incorrect date time format. Please refer documentation for correct " +
              "format " + "and enter just date again.");
    }
    throw new IllegalArgumentException("Future Date not allowed. Enter command again\n");
  }
}
