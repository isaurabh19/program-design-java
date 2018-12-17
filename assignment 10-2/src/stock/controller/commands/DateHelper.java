package stock.controller.commands;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatConversionException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * An helper class to get correct date from the user. Prompts for date field by field as year, month
 * day and optionally for hour and minutes.
 */

public class DateHelper {
  //kept package private since its only helpful for methods within commands package to get date.

  private static String getDate(Supplier<String> reader, Consumer<String> writer,
                                Consumer<String> notification) throws IOException {
    writer.accept("Enter the year in 4 digits. e.g 2018\n");
    String year = reader.get();
    if (year.equalsIgnoreCase("current")) {
      return "current";
    }
    String month = getDateFieldBy(reader, writer, notification, (String s) -> Integer.parseInt(s)
            >= 0 &&
            Integer.parseInt(s) <= 12, "Enter the month number from 1-12. e.g 05\n");
    String day = getDateFieldBy(reader, writer, notification, (String s) -> Integer.parseInt(s) >=
            0 &&
            Integer.parseInt(s) <= 31, "Enter day of the month starting from 1 till last" +
            "day the month can have. E.g 09\n");
    return year + "-" + String.format("%02d", Integer.parseInt(month)) + "-" +
            String.format("%02d", Integer.parseInt(day));
  }

  private static String getDateFieldBy(Supplier<String> reader, Consumer<String> writer,
                                       Consumer<String> notification, Predicate<String> condition,
                                       String message) throws IOException {
    while (true) {
      writer.accept(message);
      String param = reader.get();
      try {
        if (condition.test(param)) {
          return String.format("%02d", Integer.parseInt(param));
        }
      } catch (IllegalFormatConversionException | NumberFormatException e) {
        notification.accept("Invalid format. Numbers only\n");
      }
    }
  }

  private static String getTime(Supplier<String> reader, Consumer<String> writer,
                                Consumer<String> notification) throws IOException {
    String hours = getDateFieldBy(reader, writer, notification, (String s) -> Integer.parseInt(s)
            >= 0 &&
            Integer.parseInt(s) <= 24, "Enter hours in 24 hr format\n");
    String minutes = getDateFieldBy(reader, writer, notification, (String s) -> Integer.parseInt(s)
            >= 0 &&
            Integer.parseInt(s) <= 60, "Enter minutes between 00-60\n");
    return "," + hours + ":" + minutes;
  }

  /**
   * Helper method to get the date from input source.
   *
   * @param reader       the Function object that provides data required.
   * @param writer       the function object to prompt messages on the view for asking user input.
   * @param notify       notifies the view about feedback of their action or unusual events.
   * @param timeRequired if time is to be taken as input along with date.
   * @return a LocalDateTime object representing date and time.
   * @throws IOException if input source throws any error.
   */
  public static LocalDateTime getDateTime(Supplier<String> reader, Consumer<String> writer,
                                          Consumer<String> notify,
                                          boolean timeRequired) throws IOException {
    while (true) {
      try {
        String dateTime = getDate(reader, writer, notify);
        String time = ",16:00";
        if (timeRequired) {
          time = getTime(reader, writer, notify);
        }
        if (dateTime.equalsIgnoreCase("current")) {
          return null;
        }
        dateTime += time;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        if (!dateTime.replace(',', 'T').equals(localDateTime.toString())) {
          notify.accept("Invalid day of the month. Enter again.\n");
        }
        if (localDateTime.isBefore(LocalDateTime.now())) {
          return localDateTime;
        } else {
          notify.accept("Future date not allowed. Enter command again\n");
        }
      } catch (DateTimeParseException e) {
        notify.accept("Incorrect date time format. Please refer documentation for" +
                " correct " + "format " + "and enter date again.\n");
      }
    }
  }
}
