/**
 * This class represents a Newtonian particle. A particle has an initial x,y position and an initial
 * horizonatal and vertical velocity vx, vy respectively.
 */
public class SimpleProjectile implements Particle {
  private final double ACCELARATION = 9.81f;
  private double initial_x;
  private double initial_y;
  private double initial_vx;
  private double initial_vy;

  /**
   * Construct a particle that has the provided initial positions x,y and initial velocities vx,vy.
   *
   * @param initialX  Initial X co-ordinate position of the particle.
   * @param initialY  Initial Y co-ordinate position of the particle.
   * @param initialVx Initial horizontal velocity of the particle.
   * @param initialVy Initial vertical velocity of the particle.
   */

  public SimpleProjectile(double initialX, double initialY, double initialVx,
                          double initialVy) {
    this.initial_x = initialX;
    this.initial_y = initialY;
    this.initial_vx = initialVx;
    this.initial_vy = initialVy;
  }

  /**
   * Return the state of this particle as a formatted string. The format of the string is as
   * follows:
   * <code>At time [t]: position is ([x],[y])</code> where
   * <ul>
   * <li>[t] is the time passed to this method, rounded to three decimal
   * places</li>
   * <li>[x] is the x-coordinate of the position of this particle at this
   * time, rounded to three decimal places </li>
   * <li>[y] is the y-coordinate of the position of this particle at this
   * time, rounded to three decimal places
   * </li> </ul>
   *
   * @param time the time at which the state must be obtained
   * @return the state of the particle as a string formatted as above
   */
  @Override
  public String getState(float time) {
    double displacementX;
    double displacementY;

    double timeToGround = (2 * this.initial_vy) / this.ACCELARATION;

    displacementX = this.initial_vx * time;
    displacementY = this.initial_vy * time - (this.ACCELARATION * 0.5 * Math.pow(time, 2));

    if (time < 0.0 || this.initial_vy < 0) {
      return String.format("At time %.2f: position is (%.2f,%.2f)", time, this.initial_x,
              this.initial_y);
    }

    if (time >= timeToGround) {
      displacementY = 0.0;
      displacementX = this.initial_vx * timeToGround;
    }
    return String.format("At time %.2f: position is (%.2f,%.2f)", time,
            this.initial_x + displacementX, this.initial_y + displacementY);
  }
}
