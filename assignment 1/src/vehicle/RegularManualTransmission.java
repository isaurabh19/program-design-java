package vehicle;


import java.util.HashMap;

/**
 * Implements the ManualTransmission interface. Provides methods to correctly change speeds and
 * gears if it's possible. At any point of time, the status of the transmission can be obtained.
 * Currently Maximum allowed gears and magnitude of speed change are final fields set to 5 and 1
 * respectively. By default, the car always starts in gear 1 with speed 0.
 */
public class RegularManualTransmission implements ManualTransmission {
  private static final String ALL_OK = "OK: everything is OK.";
  private static final String MAX_SPEED = "Cannot increase speed. Reached maximum speed.";
  private static final String MAX_GEAR = "Cannot increase gear. Reached maximum gear.";
  private static final String MIN_SPEED = "Cannot decrease speed. Reached minimum speed.";
  private static final String MIN_GEAR = "Cannot decrease gear. Reached minimum gear.";
  private static final String INC_GEAR = "OK: you may increase the gear.";
  private static final String DEC_GEAR = "OK: you may decrease the gear.";
  private static final String NO_INC_SPEED = "Cannot increase speed, increase gear first.";
  private static final String NO_DEC_SPEED = "Cannot decrease speed, decrease gear first.";
  private static final String NO_INC_GEAR = "Cannot increase gear, increase speed first.";
  private static final String NO_DEC_GEAR = "Cannot decrease gear, decrease speed first.";

  private int currentSpeed;
  private int currentGear;
  private int maxSpeed;
  private String currentStatusMessage;
  private final int speedChange = 1;
  private final int maxGears = 5;
  private HashMap<Integer, Integer[]> gearRange = new HashMap<Integer, Integer[]>();


  /**
   * Creates a new RegularManualTransmission object with default gear as 1, speed as 0 and maximum
   * speed as the highest range value of the last gear.
   *
   * @param l1 starting speed of 1st gear.
   * @param h1 max speed limit of 1st gear.
   * @param l2 starting speed of 2nd gear.
   * @param h2 max speed limit of 2nd gear.
   * @param l3 starting speed of 3rd gear.
   * @param h3 max speed limit of 3rd gear.
   * @param l4 starting speed of 4th gear.
   * @param h4 max speed limit of 4th gear.
   * @param l5 starting speed of 5th gear.
   * @param h5 max speed limit of 5th gear.
   * @throws IllegalArgumentException if even one of the following is true: 1) lower limit of any
   *                                  gear is greater than higher limit of the same gear. 2) lower
   *                                  limit of higher gear is lesser than lower limit of it's
   *                                  previous gears. 3) the ranges don't overlap
   */
  public RegularManualTransmission(int l1, int h1, int l2, int h2, int l3, int h3, int l4, int h4,
                                   int l5, int h5) throws IllegalArgumentException {
    if (!((l1 < l2) && (l2 < l3) && (l3 < l4) && (l4 < l5))) {
      throw new IllegalArgumentException();
    }
    if ((l1 > h1) || (l2 > h2) || (l3 > h3) || (l4 > h4) || (l5 > h5)) {
      throw new IllegalArgumentException();
    }
    // if adjacent ranges don't overlap, throw exception
    if ((h1 < l2) || (h2 < l3) || (h3 < l4) || (h4 < l5)) {
      throw new IllegalArgumentException();
    }

    this.gearRange.put(1, new Integer[]{0, h1});
    this.gearRange.put(2, new Integer[]{l2, h2});
    this.gearRange.put(3, new Integer[]{l3, h3});
    this.gearRange.put(4, new Integer[]{l4, h4});
    this.gearRange.put(5, new Integer[]{l5, h5});
    this.maxSpeed = h5;
    this.currentStatusMessage = ALL_OK;
    this.currentGear = 1;
    this.currentSpeed = 0;
  }

  /**
   * Private constructor to create RegularManualTransmission object from given gear range, current
   * status message, gear, speed and maximum speed.
   *
   * @param gearRange            A HashMap with gear number as key and an array of 2 integers
   *                             denoting low and high speeds for the gear as values.
   * @param currentStatusMessage current status of the tranmission as a string.
   * @param currentSpeed         current speed the car is in as a whole number.
   * @param currentGear          current gear the car is in as a whole number.
   * @param maxSpeed             maximum speed that car can attain.
   */
  private RegularManualTransmission(HashMap<Integer, Integer[]> gearRange,
                                    String currentStatusMessage, int currentSpeed,
                                    int currentGear, int maxSpeed) {
    this.gearRange = gearRange;
    this.currentStatusMessage = currentStatusMessage;
    this.currentSpeed = currentSpeed;
    this.currentGear = currentGear;
    this.maxSpeed = maxSpeed;

  }

  @Override
  public String getStatus() {
    return this.currentStatusMessage;
  }

  @Override
  public int getSpeed() {
    return this.currentSpeed;
  }

  @Override
  public int getGear() {
    return this.currentGear;
  }

  @Override
  public ManualTransmission increaseSpeed() {

    int nextSpeed = this.currentSpeed + this.speedChange;
    if (nextSpeed > this.maxSpeed) {
      this.currentStatusMessage = MAX_SPEED;
    } else if (this.currentGear == this.maxGears) {
      this.currentSpeed = nextSpeed;
      this.currentStatusMessage = ALL_OK;
    } else if ((nextSpeed >= this.gearRange.get(this.currentGear + 1)[0])
            && ((nextSpeed) <= this.gearRange.get(this.currentGear)[1])) {
      this.currentSpeed = nextSpeed;
      this.currentStatusMessage = INC_GEAR;
    } else if (nextSpeed > this.gearRange.get(this.currentGear)[1]) {
      this.currentStatusMessage = NO_INC_SPEED;
    } else {
      this.currentSpeed = nextSpeed;
      this.currentStatusMessage = ALL_OK;
    }
    return new RegularManualTransmission(gearRange, this.currentStatusMessage, this.currentSpeed,
            this.currentGear, this.maxSpeed);
  }

  @Override
  public ManualTransmission decreaseSpeed() {
    int nextSpeed = this.currentSpeed - this.speedChange;
    if (nextSpeed < 0) {
      this.currentStatusMessage = MIN_SPEED;
    } else if (this.currentGear == 1) {
      this.currentSpeed = nextSpeed;
      this.currentStatusMessage = ALL_OK;
    } else if ((nextSpeed <= this.gearRange.get(this.currentGear - 1)[1])
            && (nextSpeed >= this.gearRange.get(this.currentGear)[0])) {
      this.currentSpeed = nextSpeed;
      this.currentStatusMessage = DEC_GEAR;
    } else if (nextSpeed < this.gearRange.get(this.currentGear)[0]) {
      this.currentStatusMessage = NO_DEC_SPEED;
    } else {
      this.currentSpeed = nextSpeed;
      this.currentStatusMessage = ALL_OK;
    }
    return new RegularManualTransmission(this.gearRange, this.currentStatusMessage,
            this.currentSpeed, this.currentGear, this.maxSpeed);
  }

  @Override
  public ManualTransmission increaseGear() {
    if (this.currentGear == this.maxGears) {
      this.currentStatusMessage = MAX_GEAR;
    } else if (this.currentSpeed >= this.gearRange.get(this.currentGear + 1)[0]) {
      this.currentGear = this.currentGear + 1;
      this.currentStatusMessage = ALL_OK;
    } else if (this.currentSpeed < this.gearRange.get(this.currentGear + 1)[0]) {
      this.currentStatusMessage = NO_INC_GEAR;
    }
    return new RegularManualTransmission(this.gearRange, this.currentStatusMessage,
            this.currentSpeed, this.currentGear, this.maxSpeed);
  }

  @Override
  public ManualTransmission decreaseGear() {
    if (this.currentGear == 1) {
      this.currentStatusMessage = MIN_GEAR;
    } else if (this.currentSpeed <= this.gearRange.get(this.currentGear - 1)[1]) {
      this.currentGear = this.currentGear - 1;
      this.currentStatusMessage = ALL_OK;
    } else if (this.currentSpeed > this.gearRange.get(this.currentGear - 1)[1]) {
      this.currentStatusMessage = NO_DEC_GEAR;
    }
    return new RegularManualTransmission(this.gearRange, this.currentStatusMessage,
            this.currentSpeed, this.currentGear, this.maxSpeed);
  }
}
