package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

import stock.view.IStockView;

/**
 * An helper class to get correct date from the user. Prompts for date field by field as year, month
 * day and optionally for hour and minutes.
 */

public class DateHelper {
  //kept package private since its only helpful for methods within commands package to get date.

  private static String getDate(IStockView view) throws IOException {
    view.write("Enter the year in 4 digits. e.g 2018\n");
    String year = view.read();
    if (year.equalsIgnoreCase("current")) {
      return "current";
    }
    String month = getDateFieldBy(view, (String s) -> Integer.parseInt(s) >= 0 &&
            Integer.parseInt(s) <= 12, "Enter the month number from 1-12. e.g 11\n");
    String day = getDateFieldBy(view, (String s) -> Integer.parseInt(s) >= 0 &&
            Integer.parseInt(s) <= 31, "Enter day of the month starting from 1 till last" +
            "day the month can have. E.g 25\n");
    return year + "-" + month + "-" + day;
  }

  private static String getDateFieldBy(IStockView view, Predicate<String> condition,
                                       String message) throws IOException {
    while (true) {
      view.write(message);
      String param = view.read();
      if (condition.test(param)) {
        return param;
      }
    }
  }

  private static String getTime(IStockView view) throws IOException {
    String hours = getDateFieldBy(view, (String s) -> Integer.parseInt(s) >= 0 &&
            Integer.parseInt(s) <= 24, "Enter hours in 24 hr format\n");
    String minutes = getDateFieldBy(view, (String s) -> Integer.parseInt(s) >= 0 &&
            Integer.parseInt(s) <= 60, "Enter minutes between 00-60\n");
    return "," + hours + ":" + minutes;
  }

  /**
   * Helper method to get the date from view input.
   *
   * @param view the view from which input is requested.
   * @return a LocalDateTime object from the input string.
   * @throws IOException if the input source throws any error.
   */
  public static LocalDateTime getDateTime(IStockView view, boolean timeRequired)
          throws IOException {
    String dateTime = getDate(view);
    String time = ",16:00";
    if (timeRequired) {
      time = getTime(view);
    }
    if (dateTime.equalsIgnoreCase("current")) {
      return LocalDateTime.now();
    }
    dateTime += time;
    while (true) {
      try {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        if (!dateTime.replace(',', 'T').equals(localDateTime.toString())) {
          view.write("Invalid day of the month. Enter again.\n");
        }
        if (localDateTime.isBefore(LocalDateTime.now())) {
          return localDateTime;
        } else {
          view.write("Future date not allowed. Enter command again\n");
        }
      } catch (DateTimeParseException e) {
        view.write("Incorrect date time format. Please refer documentation for correct " +
                "format " + "and enter date again.\n");
      }
    }
  }
}
