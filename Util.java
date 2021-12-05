package swinglib;

public class Util {

  private Util() {} // Don't let anyone instantiate

  public static int wrap(int i, int max) {
    Log.checkGt(0, max, "Max must be positive");
    if (i < 0) {
      i = max - i;
    }
    i %= max;
    return i;
  }

  public static int clamp(int i, int min, int max) {
    return Math.max(Math.min(i, max - 1), min);
  }

}
