package stock.model.dao;

/**
 * This is a factory class that creates a suitable object and returns to the caller. Since our
 * current implementation supports from CSV file and API, we have 2 options. If an invalid source is
 * entered, an IllegalArgumentException is thrown, if not, a suitable object is created and
 * returned.
 */
public class DAOFactory {

  /**
   * Returns the dao source as per the parameter passed.
   *
   * @param source the string representing source of data.
   * @return the DAOInterface object.
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
