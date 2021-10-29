package swinglib;

public class Pair<F, S> extends Stringer {

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
  public boolean equals(Object obj) {
    boolean eq = (obj.getClass() == getClass());
    if (eq) {
      final var p = (Pair<?, ?>) obj;
      eq &= (p.first.equals(first) && (p.second.equals(second)));
    }
    return eq;
  }

}
