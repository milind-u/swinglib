package swinglib;

public class Triplet<F, S, T> extends Pair<F, S> {

  private T third;

  public Triplet(F first, S second, T third) {
    super(first, second);
    this.third = third;
  }

  public T getThird() {
    return third;
  }

  public void setThird(T third) {
    this.third = third;
  }

  public Object[] getAll() {
    return new Object[] {getFirst(), getSecond(), third};
  }

  @Override
  public String toString() {
    return String.format("Triplet{first: %s, second: %s, third: %s}", getAll());
  }

}
