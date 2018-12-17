package fib;


public class FibCounter implements FibonacciCounter {
  private long count;
  private final int changeBy = 1;

  public FibCounter() {
    this.count = 1;
  }

  private FibCounter(long count) {
    this.count = count;
  }

  @Override
  public long getCount() {
    return this.count;
  }

  @Override
  public long getFibNumber() throws ArithmeticException {
    long elem1 = 0;
    long elem2 = 1;
    long temp;
    if (this.count == 1) {
      return elem1;
    }
    if (this.count == 2) {
      return elem2;
    }
    for (long i = 3; i <= this.count; i++) {
      if (elem1 + elem2 > Long.MAX_VALUE) {
        throw new ArithmeticException("Overflow");
      }
      temp = elem1 + elem2;
      elem1 = elem2;
      elem2 = temp;
    }
    return elem2;
  }

  @Override
  public FibonacciCounter incrementCount() throws ArithmeticException {
    if (this.count == Long.MAX_VALUE) {
      throw new ArithmeticException("Overflow");
    }
    this.count = this.count + this.changeBy;
    return new FibCounter(this.count);
  }

  @Override
  public FibonacciCounter decrementCount() throws ArithmeticException {
    this.count = this.count - this.changeBy;
    if (this.count < 1) {
      throw new ArithmeticException("Count cannot be less than 1");
    }
    return new FibCounter(this.count);
  }
}
