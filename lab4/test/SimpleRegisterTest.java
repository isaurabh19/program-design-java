import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import cs5010.register.CashRegister;
import cs5010.register.InsufficientCashException;
import cs5010.register.SimpleRegister;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SimpleRegisterTest {

  private CashRegister register;

  /**
   * Instantiates the register object.
   */
  @Before
  public void setRegister() {
    register = new SimpleRegister();
  }

  @Test
  public void testAddPenniesZeroOnEmptyRegister() {
    register.addPennies(0);
    Map<Integer, Integer> registerMap = register.getContents();
    assertFalse(registerMap.containsKey(1));
    assertEquals("", register.getAuditLog());
  }

  @Test
  public void testAddPenniesZeroOnNonEmptyRegister() {
    register.addPennies(5);
    register.addPennies(0);
    assertEquals("Deposit: 0.05", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    assertTrue(registerMap.containsKey(1));
    if (registerMap.containsKey(1)) {
      int actual = registerMap.get(1);
      assertEquals(5, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPenniesNegative() {
    register.addPennies(-1);
  }

  @Test
  public void testAddPenniesOnEmptyRegister() {
    register.addPennies(5);
    assertEquals("Deposit: 0.05", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(1)) {
      int actual = registerMap.get(1);
      assertEquals(5, actual);
    } else {
      fail("Key doesn't exist, test failed");
    }
  }

  @Test
  public void testAddPenniesOnNonEmptyRegister() {
    register.addPennies(100);
    register.addPennies(4);
    assertEquals("Deposit: 1.00\nDeposit: 0.04", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(1)) {
      int actual = registerMap.get(1);
      assertEquals(104, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test
  public void testAddNickelsZeroOnEmptyRegister() {
    register.addNickels(0);
    Map<Integer, Integer> registerMap = register.getContents();
    assertFalse(registerMap.containsKey(5));
    assertEquals("", register.getAuditLog());
  }

  @Test
  public void testAddNickelZeroOnNonEmptyRegister() {
    register.addNickels(5);
    register.addNickels(0);
    assertEquals("Deposit: 0.25", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    assertTrue(registerMap.containsKey(5));
    if (registerMap.containsKey(5)) {
      int actual = registerMap.get(5);
      assertEquals(5, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNicklesNegative() {
    register.addNickels(-1);
  }

  @Test
  public void testAddNickelsOnEmptyRegister() {
    register.addNickels(5);
    assertEquals("Deposit: 0.25", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(5)) {
      int actual = registerMap.get(5);
      assertEquals(5, actual);
    } else {
      fail("Key doesn't exist, test failed");
    }
  }

  @Test
  public void testAddNicklesOnNonEmptyRegister() {
    register.addNickels(100);
    register.addNickels(4);
    assertEquals("Deposit: 5.00\nDeposit: 0.20", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(5)) {
      int actual = registerMap.get(5);
      assertEquals(104, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test
  public void testAddTensZeroOnEmptyRegister() {
    register.addTens(0);
    Map<Integer, Integer> registerMap = register.getContents();
    assertFalse(registerMap.containsKey(1000));
    assertEquals("", register.getAuditLog());
  }

  @Test
  public void testAddTensZeroOnNonEmptyRegister() {
    register.addTens(5);
    register.addTens(0);
    assertEquals("Deposit: 50.00", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    assertTrue(registerMap.containsKey(1000));
    if (registerMap.containsKey(1000)) {
      int actual = registerMap.get(1000);
      assertEquals(5, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTensNegative() {
    register.addTens(-1);
  }

  @Test
  public void testAddTensOnEmptyRegister() {
    register.addTens(12);
    assertEquals("Deposit: 120.00", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(1000)) {
      int actual = registerMap.get(1000);
      assertEquals(12, actual);
    } else {
      fail("Key doesn't exist, test failed");
    }
  }

  @Test
  public void testAddTensOnNonEmptyRegister() {
    register.addTens(100);
    register.addTens(4);
    assertEquals("Deposit: 1000.00\nDeposit: 40.00", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(1000)) {
      int actual = registerMap.get(1000);
      assertEquals(104, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test
  public void testAddDimesZeroOnEmptyRegister() {
    register.addDimes(0);
    Map<Integer, Integer> registerMap = register.getContents();
    assertFalse(registerMap.containsKey(10));
    assertEquals("", register.getAuditLog());
  }

  @Test
  public void testAddDimesZeroOnNonEmptyRegister() {
    register.addDimes(5);
    register.addDimes(0);
    assertEquals("Deposit: 0.50", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    assertTrue(registerMap.containsKey(10));
    if (registerMap.containsKey(10)) {
      int actual = registerMap.get(10);
      assertEquals(5, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddDimesNegative() {
    register.addDimes(-1);
  }

  @Test
  public void testAddDimesOnEmptyRegister() {
    register.addDimes(5);
    assertEquals("Deposit: 0.50", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(10)) {
      int actual = registerMap.get(10);
      assertEquals(5, actual);
    } else {
      fail("Key doesn't exist, test failed");
    }
  }

  @Test
  public void testAddDimesOnNonEmptyRegister() {
    register.addDimes(100);
    register.addDimes(4);
    assertEquals("Deposit: 10.00\nDeposit: 0.40", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(10)) {
      int actual = registerMap.get(10);
      assertEquals(104, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test
  public void testAddQuartersZeroOnEmptyRegister() {
    register.addQuarters(0);
    Map<Integer, Integer> registerMap = register.getContents();
    assertFalse(registerMap.containsKey(25));
    assertEquals("", register.getAuditLog());
  }

  @Test
  public void testAddQuartersZeroOnNonEmptyRegister() {
    register.addQuarters(5);
    register.addQuarters(0);
    assertEquals("Deposit: 1.25", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    assertTrue(registerMap.containsKey(25));
    if (registerMap.containsKey(25)) {
      int actual = registerMap.get(25);
      assertEquals(5, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddQuartersNegative() {
    register.addQuarters(-1);
  }

  @Test
  public void testAddQuartersOnEmptyRegister() {
    register.addQuarters(5);
    assertEquals("Deposit: 1.25", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(25)) {
      int actual = registerMap.get(25);
      assertEquals(5, actual);
    } else {
      fail("Key doesn't exist, test failed");
    }
  }

  @Test
  public void testAddQuartersOnNonEmptyRegister() {
    register.addQuarters(100);
    register.addQuarters(4);
    assertEquals("Deposit: 25.00\nDeposit: 1.00", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(25)) {
      int actual = registerMap.get(25);
      assertEquals(104, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test
  public void testAddOnesZeroOnEmptyRegister() {
    register.addOnes(0);
    Map<Integer, Integer> registerMap = register.getContents();
    assertFalse(registerMap.containsKey(100));
    assertEquals("", register.getAuditLog());
  }

  @Test
  public void testAddOnesZeroOnNonEmptyRegister() {
    register.addOnes(5);
    register.addOnes(0);
    assertEquals("Deposit: 5.00", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    assertTrue(registerMap.containsKey(100));
    if (registerMap.containsKey(100)) {
      int actual = registerMap.get(100);
      assertEquals(5, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddOnesNegative() {
    register.addOnes(-1);
  }

  @Test
  public void testAddOnesOnEmptyRegister() {
    register.addOnes(5);
    assertEquals("Deposit: 5.00", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(100)) {
      int actual = registerMap.get(100);
      assertEquals(5, actual);
    } else {
      fail("Key doesn't exist, test failed");
    }
  }

  @Test
  public void testAddOnesOnNonEmptyRegister() {
    register.addOnes(100);
    register.addOnes(4);
    assertEquals("Deposit: 100.00\nDeposit: 4.00", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(100)) {
      int actual = registerMap.get(100);
      assertEquals(104, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test
  public void testAddFivesZeroOnEmptyRegister() {
    register.addFives(0);
    Map<Integer, Integer> registerMap = register.getContents();
    assertFalse(registerMap.containsKey(500));
    assertEquals("", register.getAuditLog());
  }

  @Test
  public void testAddFivesZeroOnNonEmptyRegister() {
    register.addFives(5);
    register.addFives(0);
    assertEquals("Deposit: 25.00", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    assertTrue(registerMap.containsKey(500));
    if (registerMap.containsKey(500)) {
      int actual = registerMap.get(500);
      assertEquals(5, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddFivesNegative() {
    register.addFives(-1);
  }

  @Test
  public void testAddFivesOnEmptyRegister() {
    register.addFives(5);
    assertEquals("Deposit: 25.00", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(500)) {
      int actual = registerMap.get(500);
      assertEquals(5, actual);
    } else {
      fail("Key doesn't exist, test failed");
    }
  }

  @Test
  public void testAddFivesOnNonEmptyRegister() {
    register.addFives(100);
    register.addFives(4);
    assertEquals("Deposit: 500.00\nDeposit: 20.00", register.getAuditLog());
    Map<Integer, Integer> registerMap = register.getContents();
    if (registerMap.containsKey(500)) {
      int actual = registerMap.get(500);
      assertEquals(104, actual);
    } else {
      fail("Key Doesnt exist, test failed");
    }
  }

  @Test
  public void testGetContentsOnEmptyRegister() {
    Map<Integer, Integer> registerContents = register.getContents();
    assertTrue(registerContents.isEmpty());
  }

  @Test
  public void testGetContentsOnNonEmptyRegister() {
    register.addPennies(3);
    register.addDimes(4);
    register.addOnes(10);
    register.addOnes(0);
    register.addTens(0);
    int[] keys = new int[]{100, 10, 1};
    int[] values = new int[]{10, 4, 3};
    Map<Integer, Integer> registerContents = register.getContents();
    for (int i = 0; i < keys.length; i++) {
      assertTrue(registerContents.containsKey(keys[i]));
      int actual = registerContents.get(keys[i]);
      assertEquals(values[i], actual);
    }
  }

  @Test
  public void testGetContentsOnFullRegister() {
    register.addTens(1);
    register.addFives(1);
    register.addOnes(1);
    register.addQuarters(1);
    register.addDimes(1);
    register.addNickels(1);
    register.addPennies(1);
    int[] keys = new int[]{1000, 500, 100, 25, 10, 5, 1};
    int[] values = new int[]{1, 1, 1, 1, 1, 1, 1};
    Map<Integer, Integer> registerContents = register.getContents();
    for (int i = 0; i < keys.length; i++) {
      assertTrue(registerContents.containsKey(keys[i]));
      int actual = registerContents.get(keys[i]);
      assertEquals(values[i], actual);
    }
  }

  @Test
  public void testWithdrawAmountLessThanHighest() {
    register.addTens(10);
    register.addFives(5);
    register.addOnes(3);
    register.addQuarters(4);
    register.addDimes(2);
    register.addNickels(3);
    int[] addedCashCount = new int[]{10, 5, 3, 4, 2, 3};
    int[] addedDenomination = new int[]{1000, 500, 100, 25, 10, 5};
    StringBuilder auditLog = new StringBuilder();
    for (int i = 0; i < addedCashCount.length; i++) {
      int value = addedDenomination[i] * addedCashCount[i];
      auditLog.append(String.format("Deposit: %d.%02d\n", value / 100, value % 100));
    }
    int[] returnDenomination = new int[]{100, 25, 10, 5};
    int[] returnNumbers = new int[]{3, 4, 2, 2};
    int counter = 0;
    try {
      Map<Integer, Integer> returnSlip = register.withdraw(4, 30);
      for (Map.Entry<Integer, Integer> entry : returnSlip.entrySet()) {
        assertEquals(returnDenomination[counter] + ":" + returnNumbers[counter],
                entry.getKey() + ":" + entry.getValue());
        counter++;
      }
    } catch (InsufficientCashException e) {
      fail("Test should have passed");
    }
    int[] remainingDenomination = new int[]{5, 500, 1000};
    int[] remainingCount = new int[]{1, 5, 10};
    counter = 0;
    for (Map.Entry<Integer, Integer> entry : register.getContents().entrySet()) {
      assertEquals(remainingDenomination[counter] + ":" + remainingCount[counter],
              entry.getKey() + ":" + entry.getValue());
      counter++;
    }
    assertEquals(auditLog.append("Withdraw: 4.30").toString(), register.getAuditLog());
  }

  @Test
  public void testWithdrawAmountMoreThanHighest() {
    register.addTens(10);
    register.addFives(5);
    register.addOnes(3);
    register.addQuarters(4);
    register.addDimes(2);
    register.addNickels(3);
    register.addPennies(9);
    int[] addedCashCount = new int[]{10, 5, 3, 4, 2, 3, 9};
    int[] addedDenomination = new int[]{1000, 500, 100, 25, 10, 5, 1};
    StringBuilder auditLog = new StringBuilder();
    for (int i = 0; i < addedCashCount.length; i++) {
      int value = addedDenomination[i] * addedCashCount[i];
      auditLog.append(String.format("Deposit: %d.%02d\n", value / 100, value % 100));
    }

    int[] returnDenomination = new int[]{1000, 100, 25, 10, 1};
    int[] returnNumbers = new int[]{1, 1, 1, 1, 4};
    int counter = 0;
    try {
      Map<Integer, Integer> returnSlip = register.withdraw(11, 39);
      for (Map.Entry<Integer, Integer> entry : returnSlip.entrySet()) {
        assertEquals(returnDenomination[counter] + ":" + returnNumbers[counter],
                entry.getKey() + ":" + entry.getValue());
        counter++;
      }
    } catch (InsufficientCashException e) {
      fail("Test should have passed");
    }
    int[] remainingDenomination = new int[]{1, 5, 10, 25, 100, 500, 1000};
    int[] remainingCount = new int[]{5, 3, 1, 3, 2, 5, 9};
    counter = 0;
    for (Map.Entry<Integer, Integer> entry : register.getContents().entrySet()) {
      assertEquals(remainingDenomination[counter] + ":" + remainingCount[counter],
              entry.getKey() + ":" + entry.getValue());
      counter++;
    }
    assertEquals(auditLog.append("Withdraw: 11.39").toString(), register.getAuditLog());

  }

  @Test
  public void testWithdrawInsufficientCash() {
    register.addTens(5);
    try {
      register.withdraw(5, 40);
      fail("Test should have failed");
    } catch (InsufficientCashException e) {
      //test passes
    }
    Map<Integer, Integer> contents = register.getContents();
    int actual = contents.get(1000);
    assertEquals(5, actual);
  }

  @Test
  public void testWithdrawSuccessively() {
    register.addTens(1);
    register.addFives(4);
    try {
      Map<Integer, Integer> cashReturn = register.withdraw(5, 0);
      assertTrue(cashReturn.containsKey(500));
      int returnedCashCount = cashReturn.get(500);
      assertEquals(1, returnedCashCount);
      cashReturn = register.withdraw(10, 0);
      assertTrue(cashReturn.containsKey(1000));
      returnedCashCount = cashReturn.get(1000);
      assertEquals(1, returnedCashCount);
    } catch (InsufficientCashException e) {
      fail("test fails, insufficient cash");
    }
  }

  @Test
  public void testWithdrawInsufficientThenSufficient() {
    register.addTens(1);
    register.addFives(4);
    try {
      Map<Integer, Integer> cashReturn = register.withdraw(100, 0);
      fail("Should have failed");
    } catch (InsufficientCashException e) {
      //test pass
    }
    try {
      Map<Integer, Integer> cashReturn = register.withdraw(10, 0);
      assertTrue(cashReturn.containsKey(1000));
      int returnedCashCount = cashReturn.get(1000);
      assertEquals(1, returnedCashCount);
    } catch (InsufficientCashException e) {
      fail("test should have failed");
    }
  }

  @Test
  public void testGetAuditLogsEmpty() {
    assertEquals("", register.getAuditLog());
  }

}