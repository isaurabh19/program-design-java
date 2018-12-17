package vehicle;

/**
 * An interface that represents a set of methods used for manual transmission of a vehicle. A manual
 * transmission is the mode of vehicle operation where it's the driver's responsibility to change
 * gear based on the instantaneous speed of car.
 */
public interface ManualTransmission {
  /**
   * A method that returns the current transmission status of the car.
   *
   * @return a status message of the current status as string with either of the following values:
   */
  String getStatus();

  /**
   * A method that returns the current speed of the car.
   *
   * @return the speed of the car as a whole number.
   */
  int getSpeed();

  /**
   * A method that returns the current gear that car is in.
   *
   * @return gear of the car as a whole number.
   */
  int getGear();

  /**
   * A method to increase the speed of a car, if possible, by a pre determined fix amount. Sets
   * corresponding status messages on successful or unsuccessful speed increase. In any case, speed
   * cannot increase beyond maximum speed limit.
   *
   * @return  a ManualTransmission object with new speed if changed successfully. Otherwise, returns
   *          a new ManualTransmission object with same speed.
   */
  ManualTransmission increaseSpeed();

  /**
   * A method to decrease the speed of a car, if possible, by a pre determined fix amount. Sets
   * corresponding status messages on successful or unsuccessful speed decrease. In any case, speed
   * cannot decrease below 0.
   *
   * @return  a ManualTransmission object with new speed if changed successfully. Otherwise, returns
   *          a new ManualTransmission object with same speed.
   */
  ManualTransmission decreaseSpeed();

  /**
   * A method to increase the gear of the car, if possible, by 1. Sets corresponding status messages
   * on successful or unsuccessful gear increase. In any case, gear won't increase above maximum
   * gear number.
   *
   * @return  a ManualTransmission object with next gear value if changed successfully. Otherwise,
   *          returns a new object with same gear.
   */
  ManualTransmission increaseGear();

  /**
   * A method to decrease the gear of the car, if possible, by 1. Sets corresponding status messages
   * on successful or unsuccessful gear decrease. In any case, gear cannot decrease below 1.
   *
   * @return  a ManualTransmission object with next gear value if changed successfully. Otherwise,
   *          returns a new object with same gear.
   */
  ManualTransmission decreaseGear();
}
