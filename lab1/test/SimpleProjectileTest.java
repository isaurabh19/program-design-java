
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleProjectileTest {

  private SimpleProjectile particle;

  @Before
  public void setUp() {
    particle = new SimpleProjectile(0.0, 10.0, 0.0, 10.0);
  }

  @Test
  public void testState() {
    assertEquals(particle.getState(2.0f), "At time [2.000]: position is ([50.000]," +
            "[30.380])");
    assertEquals(particle.getState(5.0f), "At time [5.000]: position is ([10.000]," +
            "[10.000])");
  }
}