package swinglib;

public class Pair<F, S> {

  private F first;
  private S second;

  public Pair(F first, S second) {
    this.first = first;
    this.second = second;
  }

  public F getFirst() {
    return first;
  }

  public S getSecond() {
    return second;
  }

  public Object[] getBoth() {
    return new Object[] {first, second};
  }

  public void setFirst(F first) {
    this.first = first;
  }

  public void setSecond(S second) {
    this.second = second;
  }

  @Override
  public String toString() {
    return String.format("Pair{first: %s, second: %s}", getBoth());
  }

}
