import org.junit.Before;
import org.junit.Test;

import polynomial.Polynomial;
import polynomial.PolynomialImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PolynomialImplTest {

  private Polynomial polynomial;
  private Polynomial polynomial1;
  private Polynomial polynomial2;
  private Polynomial emptyPolynomial;

  @Before
  public void setup() {
    polynomial = new PolynomialImpl("2x^2 -3x^1 +4");
    emptyPolynomial = new PolynomialImpl();
  }

  @Test
  public void testEmptyStringPolynomialCreation() {
    assertEquals("0", new PolynomialImpl("").toString());
    assertEquals(0, new PolynomialImpl("").getDegree());
    assertEquals(0, new PolynomialImpl("").getCoefficient(1));
  }

  @Test
  public void testPowerZero() {
    assertEquals("1", new PolynomialImpl("1x^0").toString());
    assertEquals(0, new PolynomialImpl("1x^0").getDegree());
    assertEquals(1, new PolynomialImpl("1x^0").getCoefficient(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCharacter() {
    polynomial = new PolynomialImpl("2x^1 +p ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativePowerPolynomial() {
    polynomial = new PolynomialImpl("-3x^-2 +4x^2 +0");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecimalPowersAndCoefficients() {
    polynomial = new PolynomialImpl("2.5x^3.0 +4x^2.3 +5.5");
  }

  @Test
  public void testEmptyPolynomial() {
    assertEquals("0", new PolynomialImpl().toString());
    assertEquals(0, new PolynomialImpl().getDegree());
    assertEquals(0, new PolynomialImpl().getCoefficient(0));
  }

  @Test
  public void testZeroCoefficientPolynomial() {
    assertEquals("2x^3+1x^1+2", new PolynomialImpl("2x^3 -0x^2 +x^1 +2").toString());
    assertEquals(3, new PolynomialImpl("2x^3 -0x^2 +x^1 +2").getDegree());
    assertEquals(0, new PolynomialImpl("2x^3 -0x^2 +x^1 +2").getCoefficient(2));
  }

  @Test
  public void testNoCoefficientPolynomial() {
    assertEquals("2x^3-1x^2+2", new PolynomialImpl("-x^2 +2x^3 +2").toString());
    assertEquals(3, new PolynomialImpl("2x^3 -x^2 +2").getDegree());
    assertEquals(-1, new PolynomialImpl("2x^3 -x^2 +2").getCoefficient(2));
  }

  @Test
  public void testNormalPolynomial() {
    assertEquals("3x^2+2x^1-3", new PolynomialImpl("3x^2 +2x^1 -3").toString());
  }

  @Test
  public void testValidAddTermInMiddle() {
    polynomial = new PolynomialImpl("2x^4 -3x^1 +3");
    polynomial.addTerm(1, 2);
    assertEquals("2x^4+1x^2-3x^1+3", polynomial.toString());
    assertEquals(4, polynomial.getDegree());
    assertEquals(-3, polynomial.getCoefficient(1));
    assertEquals(3, polynomial.getCoefficient(0));
  }

  @Test
  public void testConstantTerm() {
    assertEquals("102", new PolynomialImpl("102").toString());
    assertEquals(0, new PolynomialImpl("102").getDegree());
    assertEquals(0, new PolynomialImpl("102").getCoefficient(1));
    assertEquals(102, new PolynomialImpl("102").getCoefficient(0));
  }

  @Test
  public void testGetDegreeOne() {
    assertEquals(1, new PolynomialImpl("2x^1").getDegree());
  }

  @Test
  public void testValidAddTermAtFront() {
    polynomial = new PolynomialImpl("2x^3 -3x^1 +3");
    polynomial.addTerm(2, 4);
    assertEquals("2x^4+2x^3-3x^1+3", polynomial.toString());
    assertEquals(4, polynomial.getDegree());
    assertEquals(2, polynomial.getCoefficient(4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTermIllegalCharacter() {
    polynomial.addTerm(7, -1);
  }

  @Test
  public void testValidAddTermAtBack() {
    polynomial = new PolynomialImpl("2x^4 +x^2 -3x^1");
    polynomial.addTerm(3, 0);
    assertEquals("2x^4+1x^2-3x^1+3", polynomial.toString());
    assertEquals(4, polynomial.getDegree());
    assertEquals(3, polynomial.getCoefficient(0));
  }

  @Test
  public void testAddTermToEmpty() {
    Polynomial p = new PolynomialImpl();
    p.addTerm(2, 3);
    assertEquals("2x^3", p.toString());
    assertEquals(2, p.getCoefficient(3));
    assertEquals(3, p.getDegree());
    emptyPolynomial.addTerm(1, 1);
    assertEquals("1x^1", emptyPolynomial.toString());
  }

  @Test
  public void testAddTermCoefficientAsOne() {
    polynomial.addTerm(1, 3);
    assertEquals("1x^3+2x^2-3x^1+4", polynomial.toString());
  }

  @Test
  public void testAddTermPowerAsOne() {
    polynomial.addTerm(3, 1);
    assertEquals("2x^2+4", polynomial.toString());
  }

  @Test
  public void testAddTermNegativeCoefficient() {
    polynomial.addTerm(-2, 2);
    assertEquals("-3x^1+4", polynomial.toString());
  }

  @Test
  public void testAddTermCoefficientAsZero() {
    polynomial.addTerm(0, 4);
    assertEquals("2x^2-3x^1+4", polynomial.toString());
  }

  @Test
  public void testAddTermPowerAsZero() {
    polynomial.addTerm(2, 0);
    assertEquals("2x^2-3x^1+6", polynomial.toString());
  }

  @Test
  public void testGetDegree() {
    assertEquals(2, polynomial.getDegree());
    assertEquals(4, new PolynomialImpl("3x^3 +6x^4").getDegree());
    assertEquals(1, new PolynomialImpl("3x^1").getDegree());
    assertEquals(5, new PolynomialImpl("3x^1 +2x^3 -1x^5").getDegree());
  }

  @Test
  public void testGetDegreeOfConstant() {
    assertEquals(0, new PolynomialImpl("3").getDegree());
  }

  @Test
  public void testDegreeOfEmptyPolynomial() {
    assertEquals(0, new PolynomialImpl().getDegree());
  }

  @Test
  public void testAddPolynomialSameOrder() {
    polynomial1 = new PolynomialImpl("2x^2 +3x^1 -4");
    polynomial2 = new PolynomialImpl("2x^2 -3X^1 +4");
    assertEquals("4x^2", polynomial1.add(polynomial2).toString());
    assertEquals(2, polynomial1.add(polynomial2).getDegree());
    assertEquals(4, polynomial1.add(polynomial2).getCoefficient(2));
    assertEquals(0, polynomial1.add(polynomial2).getCoefficient(1));
    assertEquals(2, polynomial1.getDegree());
    assertEquals(2, polynomial2.getDegree());
    assertEquals(2, polynomial1.getCoefficient(2));
    assertEquals(-3, polynomial2.getCoefficient(1));
  }

  @Test
  public void testAddPolynomialDifferentOrder() {
    polynomial1 = new PolynomialImpl("2x^3 +3x^1 -4");
    polynomial2 = new PolynomialImpl("-2x^2 -3X^1 +4");
    assertEquals("2x^3-2x^2", polynomial1.add(polynomial2).toString());
    assertEquals("2x^3-2x^2", polynomial2.add(polynomial1).toString());
    assertEquals(3, polynomial1.add(polynomial2).getDegree());
    assertEquals(-2, polynomial1.add(polynomial2).getCoefficient(2));
    assertEquals(0, polynomial1.add(polynomial2).getCoefficient(1));
    assertEquals(3, polynomial1.getDegree());
    assertEquals(2, polynomial2.getDegree());
    assertEquals(0, polynomial1.getCoefficient(2));
    assertEquals(-3, polynomial2.getCoefficient(1));
  }

  @Test
  public void testAddPolynomialOneTerm() {
    polynomial1 = new PolynomialImpl("2x^2 +3x^1 -4");
    polynomial2 = new PolynomialImpl("-2x^2");
    //assertEquals("3x^1-4",polynomial1.add(polynomial2).toString());
    assertEquals(1, polynomial1.add(polynomial2).getDegree());
    assertEquals(0, polynomial1.add(polynomial2).getCoefficient(2));
    assertEquals(3, polynomial1.add(polynomial2).getCoefficient(1));
    assertEquals(2, polynomial1.getDegree());
    assertEquals(2, polynomial2.getDegree());
    assertEquals(2, polynomial1.getCoefficient(2));
    assertEquals(0, polynomial2.getCoefficient(1));
  }

  @Test
  public void testAddPolynomialEmpty() {
    polynomial1 = new PolynomialImpl("2x^2 +3x^1 -4");
    polynomial2 = new PolynomialImpl();
    assertEquals("2x^2+3x^1-4", polynomial1.add(polynomial2).toString());
    assertEquals("2x^2+3x^1-4", polynomial2.add(polynomial1).toString());
    assertEquals(2, polynomial2.add(polynomial1).getDegree());
    assertEquals(3, polynomial2.add(polynomial1).getCoefficient(1));
    assertEquals(2, polynomial1.getDegree());
    assertEquals(0, polynomial2.getDegree());
    assertEquals(2, polynomial1.getCoefficient(2));
    assertEquals(0, polynomial2.getCoefficient(1));
  }

  @Test
  public void testAddPolynomialResultZero() {
    polynomial1 = new PolynomialImpl("2x^2 +3x^1 -4");
    polynomial2 = new PolynomialImpl("-2x^2 -3X^1 +4");
    assertEquals("0", polynomial1.add(polynomial2).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddIllegalPolynomial() {
    polynomial1 = new PolynomialImpl("2x^2 +3x^1 -4");
    polynomial2 = new PolynomialImpl("-2y^2 -3y^1 +4");
    assertEquals("0", polynomial1.add(new PolynomialImpl("3xy")).toString());
    assertEquals("0", polynomial1.add(polynomial2).toString());
  }

  @Test
  public void testAddPolynomialResultConstant() {
    polynomial1 = new PolynomialImpl("2x^2 +3x^1 -4");
    polynomial2 = new PolynomialImpl("-2x^2 -3X^1 -4");
    assertEquals("-8", polynomial1.add(polynomial2).toString());
  }

  @Test
  public void testGetCoefficient() {
    assertEquals(-4, new PolynomialImpl("2x^2 +3x^1 -4").getCoefficient(0));
    assertEquals(3, new PolynomialImpl("2x^2 +3x^1 -4").getCoefficient(1));
  }

  @Test
  public void testGetCoefficientPowerNotExist() {
    assertEquals(0, new PolynomialImpl("2x^2 +3x^1 -4").getCoefficient(4));
  }

  @Test
  public void testGetCoefficientEmptyPolynomial() {
    assertEquals(0, new PolynomialImpl().getCoefficient(1));
  }

  @Test
  public void testEvaluateOnEmptyPolynomial() {
    assertEquals(0.0, new PolynomialImpl().evaluate(0), 0.001);
    assertEquals(0.0, new PolynomialImpl().evaluate(-2), 0.001);
    assertEquals(0.0, new PolynomialImpl().evaluate(2), 0.001);
  }

  @Test
  public void testEvaluateOnConstantPolynomial() {
    assertEquals(102.0, new PolynomialImpl("102").evaluate(2), 0.001);
    assertEquals(0.0, new PolynomialImpl("0").evaluate(-2), 0.001);
    assertEquals(-5.0, new PolynomialImpl("-5").evaluate(0), 0.001);
    assertEquals(102, new PolynomialImpl("102").getCoefficient(0));
  }

  @Test
  public void testEvaluatePolynomial() {
    assertEquals(48.0, new PolynomialImpl("-5x^3 +2x^2").evaluate(-2), 0.001);
    assertEquals(-3.0, new PolynomialImpl("x^2 +2x^1 -3").evaluate(0), 0.001);
    assertEquals(2.09207241207E11, new PolynomialImpl("60x^10 +3x^5").
            evaluate(9), 0.001);
    assertEquals(1.440885747901424E8, new PolynomialImpl("60x^10 +3x^5")
            .evaluate(4.34556678), 0.001);
    assertEquals(-5, new PolynomialImpl("-5x^3 +2x^2").getCoefficient(3));
    assertEquals(3, new PolynomialImpl("-5x^3 +2x^2").getDegree());
  }

  @Test
  public void testDerivative() {
    Polynomial polynomial = new PolynomialImpl("-2x^4 +6x^2 +3x^1 -9");
    assertEquals("-8x^3+12x^1+3", polynomial.derivative().toString());
    assertEquals(4, polynomial.getDegree());
    assertEquals(-9, polynomial.getCoefficient(0));
    assertEquals(3, new PolynomialImpl("-2x^4 +6x^2 +3x^1 -9")
            .derivative().getDegree());
    assertEquals(3, new PolynomialImpl("-2x^4 +6x^2 +3x^1 -9")
            .derivative().getCoefficient(0));
  }

  @Test
  public void testDerivativeJustConstant() {
    assertEquals("0", new PolynomialImpl("-9").derivative().toString());
    assertEquals("0", new PolynomialImpl("-9x^0").derivative().toString());
  }

  @Test
  public void testDerivativeEmptyPolynomial() {
    assertEquals("0", new PolynomialImpl("").derivative().toString());
    assertEquals("0", new PolynomialImpl().derivative().toString());
    assertEquals("0", new PolynomialImpl("0x^2").derivative().toString());
  }

  @Test
  public void testEqualPolynomialsOnAdding() {
    polynomial1 = new PolynomialImpl("2x^2 +3x^1 -4");
    polynomial2 = new PolynomialImpl("2x^2 -3X^1 +4");
    Polynomial polynomial3 = polynomial1.add(polynomial2);
    assertEquals(polynomial3, new PolynomialImpl("4x^2"));
  }

  @Test
  public void testEqualsAfterAddTerm() {
    polynomial = new PolynomialImpl("2x^4 +x^2 -3x^1");
    polynomial.addTerm(3, 0);
    assertEquals(polynomial, new PolynomialImpl("2x^4 +x^2 -3x^1 +3"));
  }

  @Test
  public void testEquals() {
    Polynomial polynomial1 = new PolynomialImpl("2x^2 +x^1 -3");
    Polynomial polynomial2 = new PolynomialImpl("2x^2 +x^1 -3");
    Polynomial polynomial3 = new PolynomialImpl("2x^2 +x^1");
    assertEquals(polynomial1, polynomial2);
    assertEquals(polynomial2, polynomial1);
    assertNotEquals(polynomial3, polynomial1);
  }
}