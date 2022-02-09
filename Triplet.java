package swinglib;

/**
 * @author milind
 * Class to hold three objects
 * @param <F> Class of first object
 * @param <S> Class of second object
 * @param <T> Class of third object
 */
public class Triplet<F, S, T> extends Pair<F, S> {

  /**
   * Third object
   */
  private T third;

  /**
   * Creates a pair of the given objects
   * @param first First object
   * @param second Second object
   * @param third Third object
   */
  public Triplet(F first, S second, T third) {
    super(first, second);
    this.third = third;
  }

  /**
   * Returns the third object
   * @return Third object
   */
  public T getThird() {
    return third;
  }
  

  /**
   * Creates an array containing all objects
   * @return All objects
   */
  public Object[] getAll() {
    return new Object[] {getFirst(), getSecond(), third};
  }


  /**
   * Sets the third object
   * @param third New third object
   */
  public void setThird(T third) {
    this.third = third;
  }

  @Override
  public boolean equals(Object obj) {
    return ((this == obj) || super.equals(obj) && (((Triplet<?, ?, ?>) obj).third.equals(third)));
  }

}
