package stock.model.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

  protected Integer calculateNumberOfStocksBought(Double closeD, Double amount) {
    double numberOfStocks = Math.floor(amount / closeD);
    if (numberOfStocks <= 0) {
      throw new IllegalStateException("Insufficient funds.\n");
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

  protected static double round(double value, int places) {
    if (places < 0) {
      throw new IllegalArgumentException();
    }
    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}
