package stock.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a bean class that represents the mapping of cost basis and current value with respect to
 * the dates.
 */
public class CostValues {
  private final Map<LocalDateTime, Double> costBasis;
  private final Map<LocalDateTime, Double> currentValue;

  /**
   * Constructs a new object with two maps, one each for cost basis and current value.
   *
   * @param costBasis    the mapping of date with cost basis.
   * @param currentValue the mapping of date with current value.
   */
  public CostValues(Map<LocalDateTime, Double> costBasis, Map<LocalDateTime, Double> currentValue) {
    this.costBasis = costBasis;
    this.currentValue = currentValue;
  }

  /**
   * Return the cost basis map.
   *
   * @return cost basis mapping.
   */
  public Map<LocalDateTime, Double> getCostBasis() {
    return new HashMap<>(costBasis);
  }

  /**
   * Return the current value map.
   *
   * @return the current value map.
   */
  public Map<LocalDateTime, Double> getCurrentValue() {
    return new HashMap<>(currentValue);
  }
}
