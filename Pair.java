package swinglib;

/**
 * @author milind
 * Class to hold two objects
 * @param <F> Class of first object
 * @param <S> Class of second object
 */
public class Pair<F, S> extends Stringer {

  /**
   * First object
   */
  private F first;
  /**
   * Second object
   */
  private S second;

  /**
   * Creates a pair of the given objects
   * @param first First object
   * @param second Second object
   */
  public Pair(F first, S second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Returns the first object
   * @return First object
   */
  public F getFirst() {
    return first;
  }

  /**
   * Returns the second object
   * @return Second object
   */
  public S getSecond() {
    return second;
  }

  /**
   * Creates an array containing both objects
   * @return Both objects
   */
  public Object[] getBoth() {
    return new Object[] {first, second};
  }

  /**
   * Sets the first object
   * @param first New first object
   */
  public void setFirst(F first) {
    this.first = first;
  }

  /**
   * Sets the second object
   * @param second New second object
   */
  public void setSecond(S second) {
    this.second = second;
  }

  @Override
  public boolean equals(Object obj) {
    boolean eq = (this == obj);
    if (!eq && (obj.getClass() == getClass())) {
      final var p = (Pair<?, ?>) obj;
      eq = (p.first.equals(first) && (p.second.equals(second)));
    }
    return eq;
  }

}
