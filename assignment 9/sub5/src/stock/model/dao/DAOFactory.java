package stock.model.dao;

/**
 * This is a factory class that creates a suitable object and returns to the caller. Since our
 * current implementation supports from CSV file and API, we have 2 options. If an invalid source is
 * entered, an IllegalArgumentException is thrown, if not, a suitable object is created and
 * returned.
 */
public class DAOFactory {
  /**
   * This is a static method which takes in the DAO source and returns the required object. It is a
   * factory design pattern.
   *
   * @param source the source of the data.
   * @return an object with suitable source.
   */
  public static DAOInterface getDAOSource(String source) {
    switch (source) {
      case "csv":
        return new DAOcsv();
      case "api":
        return new DAOWebAPI();
      default:
        throw new IllegalArgumentException("Invalid source string.\n");
    }
  }
}
