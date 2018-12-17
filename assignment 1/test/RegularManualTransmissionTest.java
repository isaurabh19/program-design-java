import org.junit.Before;
import org.junit.Test;

import vehicle.ManualTransmission;
import vehicle.RegularManualTransmission;

import static org.junit.Assert.assertEquals;

public class RegularManualTransmissionTest {

  private ManualTransmission r;
  private ManualTransmission r2;
  private ManualTransmission r3;

  /**
   * Instantiates ManualTransmission objects for use in unit test cases.
   */
  @Before
  public void setup() {
    r = new RegularManualTransmission(0, 10, 9, 20, 15, 30, 28, 45, 40,
            60);
    r2 = new RegularManualTransmission(0, 1, 1, 2, 2, 3, 3, 4, 4, 5);
    r3 = new RegularManualTransmission(0, 1, 1, 2, 2, 3, 3, 4, 4, 5);
  }

  @Test
  public void testRegularManualTransmission() {
    assertEquals(new RegularManualTransmission(5, 10, 9, 20, 15, 22, 20, 30,
            28, 40).getGear(), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapRanges() {
    new RegularManualTransmission(0, 10, 11, 20, 21, 25, 29,
            33, 32, 36);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLowerRanges() {
    new RegularManualTransmission(0, 10, 2, 12, 1, 13, 10, 20, 18, 22);
    new RegularManualTransmission(20, 25, 18, 26, 19, 33, 23, 32, 22,
            40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLowerMore() {
    new RegularManualTransmission(22, 20, 23, 45, 34, 38, 37, 36,
            40, 40);
  }

  @Test
  public void getStatus() {
    // just status
    assertEquals(new RegularManualTransmission(0, 1, 1, 2, 2, 3, 3, 4, 4,
            5).getStatus(), "OK: everything is OK.");
    // increase speed status

    //increase gear
    //assertEquals(r.increaseGear().getStatus(),"OK: everything is OK.");
    //increase gear
    //increase speed increase gear
    //increase speed increase gear decrease speed
    //increase speed increase gear decrease speed decrease gear
    //inc sp dec sp
    //inc sp inc g dec sp dec g
  }

  @Test
  public void getSpeed() {
    assertEquals(new RegularManualTransmission(5, 10, 9, 20, 15, 30, 28, 45,
            40, 60).getSpeed(), 0);
    assertEquals(new RegularManualTransmission(0, 10, 9, 20, 15, 30, 28, 45,
            40, 60).getSpeed(), 0);
  }

  @Test
  public void getGear() {
    assertEquals(new RegularManualTransmission(0, 10, 9, 20, 15, 30, 28, 45,
            40, 60).getGear(), 1);
  }

  @Test
  public void increaseSpeed() {
    assertEquals(r.increaseSpeed().getStatus(), "OK: everything is OK.");
    assertEquals(r.getSpeed(), 1);
    //increase speed * n
    for (int i = 0; i < 9; i++) {
      r = r.increaseSpeed();
    }
    assertEquals(r.getStatus(), "OK: you may increase the gear.");
    assertEquals(r.getSpeed(), 10);
    assertEquals(r.increaseSpeed().getStatus(), "Cannot increase speed, increase gear "
            + "first.");
    assertEquals(r.getSpeed(), 10);
    assertEquals(r.decreaseSpeed().increaseSpeed().getStatus(), "OK: you may increase the "
            + "gear.");
    assertEquals(r.increaseGear().increaseSpeed().getStatus(), "OK: everything is OK.");
    assertEquals(r.increaseSpeed().getSpeed(), 10);
    assertEquals(r.decreaseSpeed().decreaseGear().decreaseSpeed().increaseSpeed().getSpeed(),
            9);
    assertEquals(r.decreaseGear().decreaseSpeed().decreaseSpeed().increaseSpeed().getStatus(),
            "OK: everything is OK.");
    assertEquals(r.decreaseSpeed().getSpeed(), 8);
    for (int i = 0; i < 4; i++) {
      r2 = r2.increaseSpeed().increaseGear();
    }
    assertEquals(r2.increaseSpeed().increaseSpeed().getStatus(), "Cannot increase speed. "
            + "Reached maximum speed.");
    assertEquals(r2.increaseSpeed().getSpeed(), 5);
  }

  @Test
  public void decreaseSpeed() {
    for (int i = 0; i < 10; i++) {
      r = r.increaseSpeed();
    }
    assertEquals(r.decreaseSpeed().getSpeed(), 9);
    assertEquals(r.increaseSpeed().getSpeed(), 10);
    assertEquals(r.increaseGear().decreaseSpeed().getStatus(), "OK: you may decrease the "
            + "gear.");
    assertEquals(r.getSpeed(), 10);
    assertEquals(r.decreaseSpeed().getSpeed(), 9);
    assertEquals(r.decreaseSpeed().getStatus(), "Cannot decrease speed, decrease gear "
            + "first.");
    assertEquals(r.decreaseSpeed().getSpeed(), 9);
    assertEquals(r.decreaseGear().decreaseSpeed().getStatus(), "OK: everything is OK.");
    assertEquals(r.decreaseSpeed().getSpeed(), 8);
    assertEquals(r.increaseSpeed().decreaseSpeed().getSpeed(), 8);
    assertEquals(r.getSpeed(), 9);
    for (int i = 0; i < 9; i++) {
      r = r.decreaseSpeed();
    }
    assertEquals(r.getSpeed(), 0);
    assertEquals(r.decreaseSpeed().getStatus(), "Cannot decrease speed. Reached minimum "
            + "speed.");
    assertEquals(r.decreaseSpeed().getSpeed(), 0);
  }

  @Test
  public void increaseGear() {
    assertEquals(r3.increaseGear().getGear(), 1);
    assertEquals(r3.increaseSpeed().increaseGear().increaseGear().getStatus(), "Cannot "
            + "increase gear, increase speed first.");

    for (int i = 0; i < 4; i++) {
      r2 = r2.increaseSpeed().increaseGear();
    }
    assertEquals(r2.decreaseSpeed().getSpeed(), 4);
    assertEquals(r2.decreaseGear().getGear(), 4);
    assertEquals(r2.decreaseSpeed().getSpeed(), 3);
    assertEquals(r2.increaseSpeed().getStatus(), "OK: you may increase the gear.");
    assertEquals(r2.getGear(), 4);
    assertEquals(r2.increaseGear().getStatus(), "OK: everything is OK.");
    assertEquals(r2.getGear(), 5);
    assertEquals(r2.increaseGear().getStatus(), "Cannot increase gear. Reached maximum "
            + "gear.");
    assertEquals(r2.increaseGear().getGear(), 5);
    assertEquals(r2.decreaseSpeed().decreaseGear().decreaseSpeed().increaseGear().getStatus(),
            "Cannot increase gear, increase speed first.");
  }

  @Test
  public void decreaseGear() {
    for (int i = 0; i < 4; i++) {
      r2 = r2.increaseSpeed().increaseGear();
    }
    assertEquals(r2.increaseSpeed().decreaseSpeed().getStatus(), "OK: you may decrease "
            + "the gear.");
    assertEquals(r2.getGear(), 5);
    assertEquals(r2.decreaseSpeed().decreaseGear().getStatus(), "OK: everything is OK.");
    assertEquals(r2.decreaseGear().getGear(), 4);
    assertEquals(r2.decreaseGear().getStatus(), "Cannot decrease gear, decrease speed "
            + "first.");
    assertEquals(r2.decreaseGear().getGear(), 4);
    for (int i = 0; i < 5; i++) {
      r2 = r2.decreaseSpeed().decreaseGear();
    }
    assertEquals(r2.decreaseGear().getStatus(), "Cannot decrease gear. Reached minimum "
            + "gear.");
    assertEquals(r2.decreaseGear().getGear(), 1);

  }
}