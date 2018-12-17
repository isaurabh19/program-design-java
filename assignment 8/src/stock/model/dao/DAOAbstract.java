package stock.model.dao;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

abstract class DAOAbstract implements DAOInterface {
  //Made abstract since it is implementing interface and has methods which have to be defined
  //by classes which extend this abstract class.
  private static final Integer opensAt = 8;
  private static final Integer closesAt = 16;
  private static final String dateFormat = "yyyy-MM-dd";

  protected Integer numberOfStocksBought(Double closeD, Double amount) {
    double numberOfStocks = Math.floor(amount / closeD);
    if (numberOfStocks <= 0) {
      throw new IllegalArgumentException("Insufficient funds.\n");
    }
    return (int) numberOfStocks;
  }

  protected String getDateInFormat(LocalDateTime date) {
    if (date.getDayOfWeek() == DayOfWeek.SATURDAY
            || date.getDayOfWeek() == DayOfWeek.SUNDAY
            || date.toLocalTime().compareTo(LocalTime.of(opensAt, 0)) < 0
            || date.toLocalTime().compareTo(LocalTime.of(closesAt, 0)) > 0
            || date.toLocalDate().compareTo(LocalDate.now()) > 0

    ) {
      throw new IllegalArgumentException("Enter business times.\n");
    }
    DateTimeFormatter dateFormatForAPI = DateTimeFormatter.ofPattern(dateFormat);
    return dateFormatForAPI.format(date);
  }
}
