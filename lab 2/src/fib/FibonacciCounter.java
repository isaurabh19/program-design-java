package fib;

public interface FibonacciCounter {
  /**
   * It returns the current count of the Fibonacci counter.
   * @return the current fibonacci count as a long.
   */
  long getCount();

  /**
   * It computes and returns the fibonacci number of the sequence corresponding to the current
   * fibonacci count.
   * @return a fibonacci number as a long.
   */
  long getFibNumber();

  /**
   * It increments the current count of the fibonacci counter by a 1.
   * @return a new FibonacciCounter object with updated count.
   */
  FibonacciCounter incrementCount();

  /**
   * It decrements the current count of the fibonacci counter by 1.
   * @return a new FibonacciCounter object with updated count.
   */
  FibonacciCounter decrementCount();
}
