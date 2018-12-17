package cs5010.register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implements a Cash register. This register does not add an entry if the passed count of
 * denomination is 0 and throws IllegalArgumentException if passed negative count. If a withdrawal
 * of an amount results in all counts of a denomination, then that denomination is also removed
 * from the register. If a denomination already exists, then it's count is updated with the new
 * passed count otherwise a new entry of the denomination and count is made in the register. The
 * class can provide a copy of the register contents.
 */
public class SimpleRegister implements CashRegister {

  private Map<Integer, Integer> register;
  private List<String> auditLogs = new ArrayList<>();

  public SimpleRegister() {
    register = new TreeMap<>(Collections.reverseOrder());
  }

  private boolean isNumValid(int num) {
    if (num < 0) {
      throw new IllegalArgumentException("Negative count not allowed");
    }
    return num > 0;
  }

  private void addToAuditLogs(int num, String transactionType) {
    int dollars = num / 100;
    int cents = num % 100;
    auditLogs.add(String.format("%s: %d.%02d", transactionType, dollars, cents));
  }

  @Override
  public void addPennies(int num) throws IllegalArgumentException {
    if (isNumValid(num)) {
      int initialCount = 0;
      if (register.containsKey(1)) {
        initialCount = register.get(1);
      }
      register.put(1, num + initialCount);
      addToAuditLogs(num, "Deposit");
    }
  }

  @Override
  public void addNickels(int num) throws IllegalArgumentException {
    if (isNumValid(num)) {
      int initialCount = 0;
      if (register.containsKey(5)) {
        initialCount = register.get(5);
      }
      register.put(5, num + initialCount);
      addToAuditLogs(5 * num, "Deposit");
    }
  }

  @Override
  public void addDimes(int num) throws IllegalArgumentException {
    if (isNumValid(num)) {
      int initialCount = 0;
      if (register.containsKey(10)) {
        initialCount = register.get(10);
      }
      register.put(10, num + initialCount);
      addToAuditLogs(10 * num, "Deposit");
    }
  }

  @Override
  public void addQuarters(int num) throws IllegalArgumentException {
    if (isNumValid(num)) {
      int initialCount = 0;
      if (register.containsKey(25)) {
        initialCount = register.get(25);
      }
      register.put(25, num + initialCount);
      addToAuditLogs(25 * num, "Deposit");
    }
  }

  @Override
  public void addOnes(int num) throws IllegalArgumentException {
    if (isNumValid(num)) {
      int initialCount = 0;
      if (register.containsKey(100)) {
        initialCount = register.get(100);
      }
      register.put(100, num + initialCount);
      addToAuditLogs(100 * num, "Deposit");
    }
  }

  @Override
  public void addFives(int num) throws IllegalArgumentException {
    if (isNumValid(num)) {
      int initialCount = 0;
      if (register.containsKey(500)) {
        initialCount = register.get(500);
      }
      register.put(500, num + initialCount);
      addToAuditLogs(500 * num, "Deposit");
    }
  }

  @Override
  public void addTens(int num) throws IllegalArgumentException {
    if (isNumValid(num)) {
      int initialCount = 0;
      if (register.containsKey(1000)) {
        initialCount = register.get(1000);
      }
      register.put(1000, num + initialCount);
      addToAuditLogs(1000 * num, "Deposit");
    }
  }

  private void filterRegister(List<Integer> removeKeys) {
    for (Integer key : removeKeys) {
      register.remove(key);
    }
  }

  @Override
  public Map<Integer, Integer> withdraw(int dollars, int cents) throws InsufficientCashException {
    Map<Integer, Integer> cashReturn = new TreeMap<>(Collections.reverseOrder());
    Map<Integer, Integer> newRegister = new TreeMap<>(Collections.reverseOrder());
    int amount = 100 * dollars + cents;
    List<Integer> removeKeys = new ArrayList<>();
    for (Map.Entry<Integer, Integer> entry : register.entrySet()) {
      int denomination = entry.getKey();
      int numberOfDenomination = amount / denomination;
      if (numberOfDenomination > 0) {
        if (numberOfDenomination < entry.getValue()) {
          amount -= numberOfDenomination * denomination;
          cashReturn.put(denomination, numberOfDenomination);
          newRegister.put(denomination, entry.getValue() - numberOfDenomination);
        } else {
          amount -= denomination * entry.getValue();
          cashReturn.put(denomination, entry.getValue());
          removeKeys.add(entry.getKey());
        }
      } else {
        newRegister.put(entry.getKey(), entry.getValue());
      }
      if (amount == 0) {
        break;
      }
    }
    if (amount > 0) {
      throw new InsufficientCashException("Insufficient cash");
    }
    filterRegister(removeKeys);
    addToAuditLogs(dollars * 100 + cents, "Withdraw");
    this.register = newRegister;
    return cashReturn;
  }

  @Override
  public Map<Integer, Integer> getContents() {
    return new TreeMap<>(this.register);
  }

  @Override
  public String getAuditLog() {
    return String.join("\n", auditLogs);
  }
}
